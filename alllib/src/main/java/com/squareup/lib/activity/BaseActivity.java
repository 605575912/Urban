package com.squareup.lib.activity;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.squareup.lib.BaseApplication;
import com.squareup.lib.BuildConfig;
import com.squareup.lib.EventMainObject;
import com.squareup.lib.EventThreadObject;
import com.squareup.lib.LayoutInterFace;
import com.squareup.lib.R;
import com.squareup.lib.frament.BaseFrament;
import com.squareup.lib.utils.AppLibUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Administrator on 2017/05/25 0025.
 */

public class BaseActivity extends FragmentActivity implements LayoutInterFace {
    public List<BaseFrament> fragments;
    protected ViewDataBinding viewDataBinding;

    public boolean isAddLifecycle() {
        return true;
    }

    public
    @LayoutRes
    int setFromLayoutID() {
        return R.layout.layout;
    }

    protected Activity getActivity() {
        return this;
    }

    public void setStatus(boolean transtatus) {
        if (!isTranslucentStatus()) {
            return;
        }
        int h = transtatus ? 0 : AppLibUtils.getStatusBarHeight();
        FrameLayout frameLayout = ((FrameLayout) getWindow().getDecorView().findViewById(android.R.id.content));
        if (frameLayout.getChildCount() > 0) {
            View view = frameLayout.findViewById(R.id.topView);
            if (view == null) {
                frameLayout.getChildAt(0).setPadding(0, h, 0, getVirtualBarHeigh());
            } else {
                view.setPadding(0, h, 0, 0);
            }
        } else {
            frameLayout.setPadding(0, h, 0, 0);
        }
    }

    /**
     * 获取虚拟功能键高度
     */
    public int getVirtualBarHeigh() {
        int vh = 0;
        WindowManager windowManager = (WindowManager) BaseApplication.getApplication().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        try {
            @SuppressWarnings("rawtypes")
            Class c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            vh = dm.heightPixels - windowManager.getDefaultDisplay().getHeight();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vh;
    }
    public boolean NeedEventBus() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
//            ViewServer.get(this).addWindow(this);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        viewDataBinding = DataBindingUtil.setContentView(this, setFromLayoutID());
        if (isTranslucentStatus()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                        | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                window.setStatusBarColor(Color.TRANSPARENT);
                window.setNavigationBarColor(Color.BLACK);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
                    localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
                }
            }

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setStatus(isAllTranslucentStatus());
        }
        if (NeedEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    //是否透明状态栏
    protected boolean isTranslucentStatus() {
        return true;
    }

    //是否透明状态栏
    protected boolean isAllTranslucentStatus() {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMain(EventMainObject event) {
        if (fragments != null) {
            for (BaseFrament tabFragment : fragments) {
                tabFragment.onEventMain(event);
            }
        }
    }

    public void onCloseBack(View view) {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEventThread(EventThreadObject event) {
        if (fragments != null) {
            for (BaseFrament tabFragment : fragments) {
                tabFragment.onEventThread(event);
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (BuildConfig.DEBUG) {
//            com.android.debug.hv.ViewServer.get(this).setFocusedWindow(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (BuildConfig.DEBUG) {

//            com.android.debug.hv.ViewServer.get(this).removeWindow(this);
        }
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    /**
     * 就是说Activity类实现了Window的Callback接口。那就是看下Activity实现的onContentChanged方法。如下：
     * <p>
     * public void onContentChanged() {
     * }
     * 咦？onContentChanged是个空方法。那就说明当Activity的布局改动时，即setContentView()或者addContentView()方法执行完毕时就会调用该方法。
     * <p>
     * 所以当我们写App时，Activity的各种View的findViewById()方法等都可以放到该方法中，系统会帮忙回调。
     */
    @Override
    public void onContentChanged() {
    }
}
