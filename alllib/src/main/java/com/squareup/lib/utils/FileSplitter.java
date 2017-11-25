package com.squareup.lib.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Keven split a src-filt into sevral small pieces. The src-file's size
 *         if required to be smaller than miPartSize * Integer.MAX_VALUE
 */
public class FileSplitter {

    private final String msSrcFile, msDstDir, msOutFileBaseName;
    private int miPartSize = 1024 * 1024;
    private int miMaxThrPoolSize = 10;
    private ThreadPoolExecutor moThrPool;

    private FileInputStream moInput;
    private FileChannel moInChannel;

    /**
     * Constructor
     *
     * @param psSrcFile
     * @param psDstDir
     * @param psOutFileBaseName the output files will be named by
     */
    public FileSplitter(String psSrcFile, String psDstDir, String psOutFileBaseName) {
        msSrcFile = psSrcFile;
        msDstDir = psDstDir;
        msOutFileBaseName = psOutFileBaseName;
    }

    /**
     * Constructor
     *
     * @param psSrcFile
     * @param psDstDir
     * @param psOutFileBaseName
     * @param piPartSize        the split-out file's max-size
     */
    public FileSplitter(String psSrcFile, String psDstDir, String psOutFileBaseName, int piPartSize) {
        msSrcFile = psSrcFile;
        msDstDir = psDstDir;
        msOutFileBaseName = psOutFileBaseName;
        miPartSize = piPartSize;
    }

    /**
     * Constructor
     *
     * @param psSrcFile
     * @param psDstDir
     * @param psOutFileBaseName
     * @param piPartSize
     * @param piMaxThrPool      the max-size of thread pool
     */
    public FileSplitter(String psSrcFile, String psDstDir, String psOutFileBaseName, int piPartSize, int piMaxThrPool) {
        msSrcFile = psSrcFile;
        msDstDir = psDstDir;
        msOutFileBaseName = psOutFileBaseName;
        miPartSize = piPartSize;
        miMaxThrPoolSize = piMaxThrPool;
    }

    /**
     * Judge if the splitter is working
     *
     * @return
     */
    public boolean isBusy() {
        return moThrPool != null && !moThrPool.isTerminated();
    }

    /**
     * Start splitting
     *
     * @return the array of the names of split-out files
     * @throws IOException
     */
    public String[] start() throws IOException {
        File loFile = new File(msSrcFile);
        if (!loFile.exists()) {
            throw new IOException("Src-File not found:" + msSrcFile);
        }

        long liFileLen = loFile.length();
        long liPartCnt = liFileLen / miPartSize + (liFileLen % miPartSize == 0 ? 0 : 1);
        if (liPartCnt > Integer.MAX_VALUE) {
            throw new IOException("Src-File too large");
        }

        moInput = new FileInputStream(msSrcFile);
        moInChannel = moInput.getChannel();
        moThrPool = new ThreadPoolExecutor(miMaxThrPoolSize, miMaxThrPoolSize, 100, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        String[] laOutFiles = new String[(int) liPartCnt];
        for (int i = 0; i < (int) liPartCnt; i++) {
            moThrPool.execute(new SplitThread(i, i == liPartCnt - 1 ? liFileLen % miPartSize : miPartSize));
            laOutFiles[i] = msDstDir + File.separator + msOutFileBaseName + "." + i + ".part";
        }
        moThrPool.shutdown();
        // Monitor
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (moThrPool.isTerminated()) {
                        if (moInChannel != null) {
                            try {
                                moInChannel.close();
                            } catch (IOException ex) {
                                Logger.getLogger(FileSplitter.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if (moInput != null) {
                            try {
                                moInput.close();
                            } catch (IOException ex) {
                                Logger.getLogger(FileSplitter.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        break;
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FileSplitter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }.start();
        return laOutFiles;
    }

    /**
     * Splitting Thread
     */
    private class SplitThread implements Runnable {

        private final int miPartIndex;
        private final long miSize;

        public SplitThread(int piPartIndex, long piSize) {
            miPartIndex = piPartIndex;
            miSize = piSize;
        }

        @Override
        public void run() {
            String lsDstPartFile = msDstDir + File.separator + msOutFileBaseName + "." + miPartIndex + ".part";
            System.out.println("FileSplitter: splitting " + lsDstPartFile);
            long liStartTime = System.currentTimeMillis();

            // output vars
            File loDstFile = new File(lsDstPartFile);
            RandomAccessFile loOutput = null;
            FileChannel loOutChannel = null;

            try {
                // Input Map
                MappedByteBuffer loInBuf = moInChannel.map(FileChannel.MapMode.READ_ONLY, miPartSize * miPartIndex, miSize);

                // Output Map
                if (!loDstFile.exists()) {
                    loDstFile.createNewFile();
                }
                loOutput = new RandomAccessFile(loDstFile, "rw");
                loOutChannel = loOutput.getChannel();
                MappedByteBuffer loOutBuf = loOutChannel.map(FileChannel.MapMode.READ_WRITE, 0, miSize);

                byte liByte;
                while (loInBuf.hasRemaining()) {
                    liByte = loInBuf.get();
                    loOutBuf.put(liByte);
                }
            } catch (IOException ex) {
                System.out.print("part_index:" + miPartIndex + " boom");
                Logger.getLogger(FileSplitter.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                // close output
                if (loOutChannel != null) {
                    try {
                        loOutChannel.close();
                    } catch (IOException ex) {
                        Logger.getLogger(FileSplitter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (loOutput != null) {
                    try {
                        loOutput.close();
                    } catch (IOException ex) {
                        Logger.getLogger(FileSplitter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            System.out.println("Splitting Task " + lsDstPartFile + " Terminated. Spend " + (System.currentTimeMillis() - liStartTime) + " milsecs");
        }
    }
}

