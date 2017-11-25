package com.squareup.lib.utils;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.graphics.Palette;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.squareup.lib.BaseApplication;
import com.squareup.lib.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;

//import android.support.v7.graphics.Palette;

/**
 * Created by Administrator on 2017/05/26 0026.
 */

public class AppLibUtils implements IProguard.ProtectClassAndMembers {

    private static String appVersionName;
    private static int currentVersionCode;
    private static String token;


    public static String RECORDFILE = "record";
    public static String IMAGEFILE = "image";
    public static String VIDEOFILE = "video";


    public static String getTodaty(long SuperDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(SuperDate);
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_YEAR);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(year);
        stringBuilder.append(day);
        String today = stringBuilder.toString();
        return today;
    }

    public static String getToken() {
        if (TextUtils.isEmpty(token)) {
            SharedPreferences sharedPreferences = CryptSharedPreferences.getDefaultSharedPreferences(BaseApplication.getApplication());
            token = sharedPreferences.getString("token", "");
        }
        return token;
    }

    public static void setToken(String token) {
        AppLibUtils.token = token;
        if (!TextUtils.isEmpty(token)) {
            SharedPreferences sharedPreferences = CryptSharedPreferences.getDefaultSharedPreferences(BaseApplication.getApplication());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("token", token);
            editor.apply();
        }
    }

    public static int getversionCode(Context ctx) {
        if (currentVersionCode > 0) {
            return currentVersionCode;
        }
        getPackageInfo(ctx);
        return currentVersionCode;
    }

    private static void getPackageInfo(Context ctx) {
        PackageManager manager = ctx.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            appVersionName = info.versionName; // 版本名
            currentVersionCode = info.versionCode; // 版本号
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getAppVersionName(Context ctx) {
        if (!TextUtils.isEmpty(appVersionName)) {
            return appVersionName;
        }
        getPackageInfo(ctx);
        return appVersionName;
    }

    public static String getMd5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;

            StringBuilder buf = new StringBuilder();
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

    }

    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = BaseApplication.getApplication().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = BaseApplication.getApplication().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private static double density;

//    public static float getdensity(Activity activity) {
//        if (density > 0) {
//            return density;
//        }
//        DisplayMetrics metric = new DisplayMetrics();
//        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
//        int width = metric.widthPixels;  // 屏幕宽度（像素）
//        int height = metric.heightPixels;  // 屏幕高度（像素）
//        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
//        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）
//        return density;
//    }

    public static double getdensity(Context context) {
        if (density > 0) {
            return density;
        }
        if (context == null) {
            return 1;
        }
        Point point;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        density = dm.density;
        if (Build.VERSION.SDK_INT > 13) {
            dm = context.getResources().getDisplayMetrics();
            point = new Point();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if (Build.VERSION.SDK_INT > 16) {
                wm.getDefaultDisplay().getRealSize(point);
            } else {
                wm.getDefaultDisplay().getSize(point);
            }
            double x = Math.pow(point.x / dm.xdpi, 2);
            double y = Math.pow(point.y / dm.ydpi, 2);
            double screenInches = Math.sqrt(x + y);
            density = Math.sqrt(Math.pow(point.x, 2) + Math.pow(point.y, 2)) / screenInches / DisplayMetrics.DENSITY_MEDIUM;
        }
        return density;
    }

    private static int[] defaultDisplay = null;

    /**
     * 获取当前屏幕分辨率   宽度×高度
     *
     * @param activity
     * @return
     */
    public static int[] getDefaultDisplay(Activity activity) {
        synchronized (defaultDisplay) {
            if (defaultDisplay == null) {
                DisplayMetrics metric = new DisplayMetrics();
                activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
                int width = metric.widthPixels; // 屏幕宽度（像素）
                int height = metric.heightPixels; // 屏幕高度（像素）
                defaultDisplay = new int[]{width, height};
            }
        }
        return defaultDisplay;
    }

    public static void loadso(Context context, File dir, String soname) {
        //        File dir = this.getDir("lib", Activity.MODE_PRIVATE);
//        Log.i("TAG", "===dir" + dir.getAbsolutePath());
//        setload(dir, "libEasyAR.so");
//        setload(dir, "libEasyAR.so");
        File distFile = new File(dir.getAbsolutePath() + File.separator + soname);
        if (distFile.exists()) {
            //使用load方法加载内部储存的SO库
            System.load(distFile.getAbsolutePath());
            return;
        }
        if (copyFileFromAssets(context, soname, distFile.getAbsolutePath())) {
            //使用load方法加载内部储存的SO库
            System.load(distFile.getAbsolutePath());
        }
    }

    public static boolean copyFileFromAssets(Context context, String fileName, String path) {

        boolean copyIsFinish = false;

        try {

            InputStream is = context.getAssets().open(fileName);

            File file = new File(path);

            file.createNewFile();

            FileOutputStream fos = new FileOutputStream(file);

            byte[] temp = new byte[1024];

            int i = 0;

            while ((i = is.read(temp)) > 0) {

                fos.write(temp, 0, i);

            }

            fos.close();

            is.close();

            copyIsFinish = true;

        } catch (IOException e) {

            e.printStackTrace();


        }

        return copyIsFinish;

    }

    public static String getApplicationName(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName =
                (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }

    public static int getMODE_MULTI_PROCESS() {
        int MODE_MULTI_PROCESS = Context.MODE_PRIVATE;
        int mVersion = Integer.parseInt(Build.VERSION.SDK);// Integer.parseInt(Build.VERSION.SDK);
        if (mVersion >= 9) {
            Object obj = ReflectHelper.getStaticFieldValue(Context.class, "MODE_MULTI_PROCESS");
            if (obj != null && obj instanceof Integer) {
                MODE_MULTI_PROCESS = (Integer) obj;
            }
        }
        return MODE_MULTI_PROCESS;
    }


    /**
     * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换
     *
     * @param imageUri
     */
    private String getImageAbsolutePath(Activity context, Uri imageUri) {
        if (context == null || imageUri == null)
            return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    public static void PaletteColor(Bitmap bitmap) {
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                // palette为生成的调色板
                Palette.Swatch s1 = palette.getVibrantSwatch(); //充满活力的色板
                Palette.Swatch s2 = palette.getDarkVibrantSwatch(); //充满活力的暗色类型色板
                Palette.Swatch s3 = palette.getLightVibrantSwatch(); //充满活力的亮色类型色板
                Palette.Swatch s4 = palette.getMutedSwatch(); //黯淡的色板
                Palette.Swatch s5 = palette.getDarkMutedSwatch(); //黯淡的暗色类型色板（翻译过来没有原汁原味的赶脚啊！）
                Palette.Swatch s6 = palette.getLightMutedSwatch(); //黯淡的亮色类型色板
//                if (s1 != null) {
//                    v_0.setTag(s1.getRgb());
//
//                    v_0.setBackgroundColor(s1.getRgb());
//
//                    tv_0.setText(toHexFromColor(s1.getRgb()) + "\nVibrant");
//                } else {
//                    v_0.setTag(null);
//                    v_0.setBackgroundColor(Color.parseColor("#ffeeee"));
//                    tv_0.setText("无法获取改颜色" + "\nVibrant");
//                }
//                if (s2 != null) {
//                    v_1.setTag(s2.getRgb());
//                    v_1.setBackgroundColor(s2.getRgb());
//                    tv_1.setText(toHexFromColor(s2.getRgb()) + "\nDarkVibrant");
//                } else {
//                    v_1.setTag(null);
//                    v_1.setBackgroundColor(Color.parseColor("#ffeeee"));
//                    tv_1.setText("无法获取改颜色" + "\nDarkVibrant");
//                }
//                if (s3 != null) {
//                    v_2.setTag(s3.getRgb());
//                    v_2.setBackgroundColor(s3.getRgb());
//                    tv_2.setText(toHexFromColor(s3.getRgb()) + "\nLightVibrant");
//                } else {
//                    v_2.setTag(null);
//                    v_2.setBackgroundColor(Color.parseColor("#ffeeee"));
//                    tv_2.setText("无法获取改颜色" + "\nLightVibrant");
//                }
//                if (s4 != null) {
//                    v_3.setTag(s4.getRgb());
//                    v_3.setBackgroundColor(s4.getRgb());
//                    tv_3.setText(toHexFromColor(s4.getRgb()) + "\nMuted");
//                } else {
//                    v_3.setBackgroundColor(Color.parseColor("#ffeeee"));
//                    v_3.setTag(null);
//                    tv_3.setText("无法获取改颜色" + "\nMuted");
//                }
//                if (s5 != null) {
//                    v_4.setTag(s5.getRgb());
//                    v_4.setBackgroundColor(s5.getRgb());
//                    tv_4.setText(toHexFromColor(s5.getRgb()) + "\nDarkMuted");
//                } else {
//                    v_4.setBackgroundColor(Color.parseColor("#ffeeee"));
//                    v_4.setTag(null);
//                    tv_4.setText("无法获取改颜色" + "\nDarkMuted");
//                }
//                if (s6 != null) {
//                    v_5.setTag(s6.getRgb());
//                    v_5.setBackgroundColor(s6.getRgb());
//                    tv_5.setText(toHexFromColor(s6.getRgb()) + "\nLightMuted");
//                } else {
//                    v_5.setBackgroundColor(Color.parseColor("#ffeeee"));
//                    v_5.setTag(null);
//                    tv_5.setText("无法获取改颜色" + "\nLightMuted");
//                }


            }

        });
    }

    private static String toHexFromColor(int color) {
        String a, r, g, b;

        StringBuilder su = new StringBuilder();
        a = Integer.toHexString(Color.alpha(color));
        r = Integer.toHexString(Color.red(color));
        g = Integer.toHexString(Color.green(color));
        b = Integer.toHexString(Color.blue(color));
        a = a.length() == 1 ? "0" + a : a;
        r = r.length() == 1 ? "0" + r : r;
        g = g.length() == 1 ? "0" + g : g;
        b = b.length() == 1 ? "0" + b : b;
        a = a.toUpperCase();
        r = r.toUpperCase();
        g = g.toUpperCase();
        b = b.toUpperCase();
        su.append("#");
        su.append(a);
        su.append(r);
        su.append(g);
        su.append(b);
        //0xFF0000FF
        return su.toString();
    }


    /**
     * 从本地获取广告图片
     *
     * @param path
     * @param width
     * @param height
     * @return
     */
    Bitmap getimage(String path, int width, int height) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        try {
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            newOpts.inJustDecodeBounds = false;
            newOpts.inSampleSize = 1;
            Bitmap tempbitmap;
            try {
                tempbitmap = BitmapFactory.decodeFile(path, newOpts);
                if (tempbitmap == null) {
                    //如果图片为null, 图片不完整则删除掉图片
                    byte[] bytes = new byte[(int) file.length() + 1];
                    FileInputStream inputStream = new FileInputStream(path);
                    inputStream.read(bytes);
                    tempbitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    if (tempbitmap == null) {
                        file.delete();
                    }
                }
                return tempbitmap;
            } catch (OutOfMemoryError e) {
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            newOpts.inJustDecodeBounds = true;
            tempbitmap = BitmapFactory.decodeFile(path, newOpts);
            int sreen = width * height;
            int image = tempbitmap.getHeight() * tempbitmap.getWidth();
            if (sreen <= 720) {// 防止过小图
                sreen = 720 * 1080;
            }
            int samplesize = image / sreen;
            if (samplesize < 2) {
                samplesize = 2;
            }
            newOpts.inSampleSize = samplesize;
            newOpts.inJustDecodeBounds = false;
            try {
                tempbitmap = BitmapFactory.decodeFile(path, newOpts);
                return tempbitmap;
            } catch (OutOfMemoryError e) {

            } catch (Exception e) {
                e.printStackTrace();


            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static boolean isTablet(Context context) {
        return context.getResources().getBoolean(R.bool.isTablet);
    }

    public static long getDayTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
//        long max = calendar.getTimeInMillis() + 24 * 60 * 60 * 1000;
    }
}
