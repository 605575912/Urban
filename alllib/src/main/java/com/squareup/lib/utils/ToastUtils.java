package com.squareup.lib.utils;

import android.os.Handler;
import android.widget.Toast;

import com.squareup.lib.BaseApplication;

/**
 * Created by lzx on 2017/05/27 0027.
 */

public class ToastUtils {
    static Toast toast;
    static Handler handler;

    public static void showToast(final CharSequence text) {
        if (text == null) {
            return;
        }
        Runnable toastRunnable = new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(BaseApplication.getApplication(), text, Toast.LENGTH_LONG);
                }
                toast.setText(text);
                toast.show();
            }
        };
        if (handler == null) {
            handler = new Handler(BaseApplication.getApplication().getMainLooper());
        }
        handler.post(toastRunnable);
    }

    public static void showToast(final int resId) {
        try {
            CharSequence s = BaseApplication.getApplication().getResources().getText(resId);
            showToast(s);
        } catch (Exception e) {
            showToast(String.valueOf(resId));
        }
    }
}
