package com.lzx.h.module.image;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;

import com.squareup.lib.BaseApplication;
import com.squareup.lib.viewfactory.BaseViewItem;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

;

/**
 * Created by liangzhenxiong on 15/12/20.
 */
public class ImageMap {
    static ArrayList<String> listdata = new ArrayList<String>();
    static ArrayList<BaseViewItem> mImageFloders = new ArrayList<BaseViewItem>();
    private static ImageMap imageMap;

    private ImageMap() {

    }


    public static ImageMap getInstance() {
        if (imageMap == null) {
            imageMap = new ImageMap();
        }
        return imageMap;
    }

    public ArrayList<String> getListdata() {
        return listdata;
    }

    public ArrayList<String> getImageCursorList() {
        Cursor cursor = null;
//        int mCurrentPicSize = 0;
        int lastsize = 0;
        ArrayList<File> listfile = new ArrayList<File>();
        /**
         * 临时的辅助类，用于防止同一个文件夹的多次扫描
         */
        HashSet<String> mDirPaths = new HashSet<String>();
        try {

            Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            ContentResolver mContentResolver =
                    BaseApplication.getApplication().getContentResolver();

            // 只查询jpeg和png的图片
            cursor = mContentResolver.query(mImageUri, null,
                    MediaStore.Images.Media.MIME_TYPE + "=?",
                    new String[]{"image/jpeg"},
                    MediaStore.Images.Media.DATE_MODIFIED);

            while (cursor.moveToNext()) {
                // 获取图片的路径
                String path = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
                // 获取该图片的父路径名
                File parentFile = new File(path).getParentFile();
                if (parentFile == null)
                    continue;
                String dirPath = parentFile.getAbsolutePath();
                ImageFloder imageFloder = null;
                // 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
                if (mDirPaths.contains(dirPath)) {
                    continue;
                } else {
                    mDirPaths.add(dirPath);
                }

                File[] paths = parentFile.listFiles(new ImageFilenameFilter());


                if (paths != null && paths.length > 0) {
                    for (File imgFileName : paths) {
//                        if (!BitmapLoader.isValidImageFile(imgFileName.getPath())) {
//                            continue;
//                        }
                        listfile.add(imgFileName);
                    }
                }
                if (listfile.size() > lastsize) {
//                    imageFloder = new ImageFloder();
//                    imageFloder.setDir(dirPath);
//                    imageFloder.setFirstImagePath(path);
//                    imageFloder.setCount(listfile.size() - lastsize);
//                    mImageFloders.add(imageFloder);
                    lastsize = listfile.size();
                }
//                File[] paths = parentFile.listFiles(new ImageFilenameFilter());
//                int picSize = paths.length;
//                if (paths != null && paths.length > 0) {
//                    for (File imgFileName : paths) {
//                        listfile.add(imgFileName);
//                    }
//                }
//                imageFloder.setCount(picSize);
//                mImageFloders.add(imageFloder);
//                if (picSize > mCurrentPicSize) {
//                    mCurrentPicSize = picSize;
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        Collections.sort(listfile, new FileComparator());
        synchronized (listfile) {
            listdata.clear();
            for (File file : listfile) {
                listdata.add(file.getPath());
            }
        }

        // 扫描完成，辅助的HashSet也就可以释放内存了
        mDirPaths.clear();
        mDirPaths = null;
        return listdata;
    }

    private class ImageFilenameFilter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String filename) {
            if (filename.endsWith(".jpg")
                    || filename.endsWith(".png")
                    || filename.endsWith(".jpeg"))
                return true;
            return false;
        }
    }

    private class FileComparator implements Comparator<File> {

        @Override
        public int compare(File lhs, File rhs) {
            if (lhs.lastModified() < rhs.lastModified()) {
                return 1;//最后修改的照片在前
            } else {
                return -1;
            }
        }
    }


    ContentObserver mDatabaseListener;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            new Thread() {
                @Override
                public void run() {
                    getImageCursorList();
                }
            }.start();
            super.handleMessage(msg);
        }
    };

    void onCreateContentObserver() {
        mDatabaseListener = new ContentObserver(handler) {
            @Override
            public boolean deliverSelfNotifications() {
                return super.deliverSelfNotifications();
            }

            @Override
            public void onChange(boolean selfChange) {
                super.onChange(selfChange);
                handler.removeCallbacksAndMessages(null);

                handler.sendEmptyMessageDelayed(0, 1400);
            }


        };
        BaseApplication.getApplication().getContentResolver().registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, true, mDatabaseListener);

    }

    protected void onDestroyContentObserver() {
        listdata.clear();
        BaseApplication.getApplication().getContentResolver().unregisterContentObserver(mDatabaseListener);
    }
}
