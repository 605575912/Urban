package com.squareup.lib.utils;

import android.util.Log;


/**
 * Created by Administrator on 2017/05/25 0025.
 */

public class LogUtil implements IProguard.ProtectClassAndMembers{
    private static String TAG = "LogUtilLIB";
    private final static boolean islog = true;

    public static void i(String log) {
        if (islog) {
            Log.i(TAG, log);
        }
    }
    public static void i(CharSequence log) {
        if (islog) {
            Log.i(TAG, log.toString());
        }
    }
    public static void i(int log) {
        if (islog) {
            Log.i(TAG, String.valueOf(log));
        }
    }

    public static void e(String log) {
        if (islog) {
            Log.e(TAG, log);
        }
    }

    public static void w(String log) {
        if (islog) {
            Log.w(TAG, log);
        }
    }
}
