package com.squareup.lib.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
 * @author Keven
 */
public class FileUnifier {

    private final String[] maSrcFiles;
    private final String msDstFile;

    private int miMaxThrPoolSize = 10;
    private ThreadPoolExecutor moThrPool;
    private RandomAccessFile moOutput;
    private FileChannel moOutputChannel;

    /**
     * Constructor
     *
     * @param paSrcFiles
     * @param psDstFile
     */
    public FileUnifier(String[] paSrcFiles, String psDstFile) {
        maSrcFiles = paSrcFiles;
        msDstFile = psDstFile;
    }

    /**
     * Constructor
     *
     * @param paSrcFiles
     * @param psDstFile
     * @param piMaxThrPool
     */
    public FileUnifier(String[] paSrcFiles, String psDstFile, int piMaxThrPool) {
        maSrcFiles = paSrcFiles;
        msDstFile = psDstFile;
        miMaxThrPoolSize = piMaxThrPool;
    }

    /**
     * Judge if the unifier is working
     *
     * @return
     */
    public boolean isBusy() {
        return moThrPool != null && !moThrPool.isTerminated();
    }

    /**
     * Start unifying
     *
     * @throws java.io.IOException
     */
    public void start() throws IOException {
        File loOutFile = new File(msDstFile);
        if (!loOutFile.exists()) {
            loOutFile.createNewFile();
        }
        moOutput = new RandomAccessFile(loOutFile, "rw");
        moThrPool = new ThreadPoolExecutor(miMaxThrPoolSize, miMaxThrPoolSize, 100, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

        // get total size
        long[][] laOffsetSizes = new long[maSrcFiles.length][2];
        long liOffset = 0;
        for (int i = 0; i < maSrcFiles.length; i++) {
            String lsSrcFile = maSrcFiles[i];
            File loFile = new File(lsSrcFile);
            if (!loFile.exists()) {
                throw new IOException("Src-File not exist:" + lsSrcFile);
            }
            laOffsetSizes[i][0] = liOffset;
            laOffsetSizes[i][1] = loFile.length();
            liOffset += loFile.length();
        }
        moOutput.setLength(liOffset);
        moOutputChannel = moOutput.getChannel();
        for (int i = 0; i < maSrcFiles.length; i++) {
            moThrPool.execute(new UnifyThread(maSrcFiles[i], laOffsetSizes[i][0], laOffsetSizes[i][1]));
        }
        moThrPool.shutdown();
        // Start monitor Thread
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (moThrPool.isTerminated()) {
                        if (moOutputChannel != null) {
                            try {
                                moOutputChannel.close();
                            } catch (IOException ex) {
                                Logger.getLogger(FileUnifier.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        if (moOutput != null) {
                            try {
                                moOutput.close();
                            } catch (IOException ex) {
                                Logger.getLogger(FileUnifier.class.getName()).log(Level.SEVERE, null, ex);
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
    }

    /**
     * Unifying thread
     */
    private class UnifyThread implements Runnable {

        private final String msSrcFile;
        private final long miOffset, miSize;

        public UnifyThread(String psSrcFile, long piOffset, long piSize) {
            msSrcFile = psSrcFile;
            miOffset = piOffset;
            miSize = piSize;
        }

        @Override
        public void run() {
            File loSrcFile = new File(msSrcFile);
            FileInputStream loInput = null;
            FileChannel loInChannel = null;
            try {
                System.out.println("FileUnifier: start unifying " + msSrcFile);
                long liStartTime = System.currentTimeMillis();
                loInput = new FileInputStream(loSrcFile);
                loInChannel = loInput.getChannel();
                MappedByteBuffer loInBuf = loInChannel.map(FileChannel.MapMode.READ_ONLY, 0, miSize);
                MappedByteBuffer loOutBuf = moOutputChannel.map(FileChannel.MapMode.READ_WRITE, miOffset, miSize);
                while (loInBuf.hasRemaining()) {
                    loOutBuf.put(loInBuf.get());
                }
                System.out.println("FileUnifier: unify " + msSrcFile + " complete. Spend " + (System.currentTimeMillis() - liStartTime) + "milsecs");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FileUnifier.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FileUnifier.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                // Close input
                if (loInChannel != null) {
                    try {
                        loInChannel.close();
                    } catch (IOException ex) {
                        Logger.getLogger(FileUnifier.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (loInput != null) {
                    try {
                        loInput.close();
                    } catch (IOException ex) {
                        Logger.getLogger(FileUnifier.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

    }
}

