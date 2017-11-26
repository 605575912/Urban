package com.squareup.lib;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.squareup.lib.activity.BaseActivity;
import com.squareup.lib.utils.CrashHandler;
import com.squareup.lib.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION.SDK_INT;

/**
 * Demo Application示例.
 *
 * @author wenjiewu
 * @since 2017/1/3
 */
public class BaseApplication extends Application implements Application.ActivityLifecycleCallbacks {
    private static BaseApplication application;

    public static BaseApplication getApplication() {
//        return TestBaseApplication.application;
        return application;
    }

    List<BaseActivity> activities = new ArrayList<BaseActivity>();

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        if (BuildConfig.DEBUG) {
            CrashHandler.Companion.getInstance().init(getApplication());
        }
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(application)
                .setBaseDirectoryName("Fresco")
                .setBaseDirectoryPathSupplier(new Supplier<File>() {
                    @Override
                    public File get() {
                        return new File(FileUtils.getDiskCacheDir());
                    }
                }).build();
        ImagePipelineConfig.Builder configBuilder = OkHttpImagePipelineConfigFactory.newBuilder(application, HttpUtils.INSTANCE.getmOkHttpClient());
        // 设置内存配置
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 4;
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                cacheSize, // Max total size of elements in the cache
                200,                     // Max entries in the cache
                50 * 1024 * 1024, // Max total size of elements in eviction queue
                50,                     // Max length of eviction queue
                10 * 1024 * 1024);
        configBuilder.setBitmapMemoryCacheParamsSupplier(
                new Supplier<MemoryCacheParams>() {
                    public MemoryCacheParams get() {
                        return bitmapCacheParams;
                    }
                });
        configBuilder.setMainDiskCacheConfig(diskCacheConfig);
        configBuilder.setDownsampleEnabled(true);
        Fresco.initialize(application, configBuilder.build());

//        // 设置是否关闭热更新能力，默认为true
//        Beta.enableHotfix = true;
//        // 设置是否自动下载补丁
//        Beta.canAutoDownloadPatch = true;
//        // 设置是否提示用户重启
//        Beta.canNotifyUserRestart = false;
//        // 设置是否自动合成补丁
//        Beta.canAutoPatch = true;
//        PluginHelper.getInstance().applicationOnCreate(getBaseContext());
        /**
         *  全量升级状态回调
         */
        /*Beta.upgradeStateListener = new UpgradeStateListener() {
            @Override
            public void onUpgradeFailed(boolean b) {

            }

            @Override
            public void onUpgradeSuccess(boolean b) {

            }

            @Override
            public void onUpgradeNoVersion(boolean b) {
                Toast.makeText(getApplicationContext(), "最新版本", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUpgrading(boolean b) {
                Toast.makeText(getApplicationContext(), "onUpgrading", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadCompleted(boolean b) {

            }
        };*/

//        /**
//         * 补丁回调接口，可以监听补丁接收、下载、合成的回调
//         */
//        Beta.betaPatchListener = new BetaPatchListener() {
//            @Override
//            public void onPatchReceived(String patchFileUrl) {
////                Toast.makeText(getApplicationContext(), patchFileUrl, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onDownloadReceived(long savedLength, long totalLength) {
////                Toast.makeText(getApplicationContext(), String.format(Locale.getDefault(),
////                        "%s %d%%",
////                        Beta.strNotificationDownloading,
////                        (int) (totalLength == 0 ? 0 : savedLength * 100 / totalLength)), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onDownloadSuccess(String patchFilePath) {
////                Toast.makeText(getApplicationContext(), patchFilePath, Toast.LENGTH_SHORT).show();
//                Beta.applyDownloadedPatch();
//            }
//
//            @Override
//            public void onDownloadFailure(String msg) {
//                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onApplySuccess(String msg) {
//                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onApplyFailure(String msg) {
//                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onPatchRollback() {
//
//            }
//        };


        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId,调试时将第三个参数设置为true
//        Bugly.init(this, "b5f9e8654b", true);
        registerActivityLifecycleCallbacks(this);
    }

//    /**
//     * 如果想更新so，可以将System.loadLibrary替换成Beta.loadLibrary
//     */
//    static {
//        Beta.loadLibrary("mylib");
//    }

    private synchronized void change(BaseActivity baseActivity, boolean isadd) {
        if (isadd) {
            activities.add(baseActivity);
        } else {
            activities.remove(baseActivity);
        }
    }

    public void clearAllActivity() {
        for (BaseActivity baseActivity : activities) {
            baseActivity.finish();
        }
    }

    public boolean IndexActivity(String activityname) {
        for (BaseActivity baseActivity : activities) {
            if (baseActivity.getClass().equals(activityname)) {
                return true;
            }
        }
        return false;
    }

    public int getActivitySize() {
        if (activities == null) {
            return 0;
        } else {
            return activities.size();
        }
    }

    protected void enabledStrictMode() {
        if (SDK_INT >= 9) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder() //
                    .detectAll() //
                    .penaltyLog() //
                    .penaltyDeath() //
                    .build());
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        MultiDex.install(base);
//        PluginHelper.getInstance().applicationAttachBaseContext(base);
        super.attachBaseContext(base);
        // you must install multiDex whatever tinker is installed!
//        // 安装tinker
//        Beta.installTinker();


        if (BuildConfig.DEBUG) {
            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
    }


    @TargetApi(9)
    protected void setStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());
    }

    public static void Exit() {
        try {
            getApplication().unregisterActivityLifecycleCallbacks(getApplication());
            getApplication().clearAllActivity();
            System.exit(0);
        } catch (
                Exception e) {
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (activity instanceof BaseActivity) {
            BaseActivity baseActivity = (BaseActivity) activity;
            if (baseActivity.isAddLifecycle()) {
                change(baseActivity, true);
            }
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (activity instanceof BaseActivity) {
            BaseActivity baseActivity = (BaseActivity) activity;
            if (baseActivity.isAddLifecycle()) {
                change(baseActivity, false);
            }
        }
    }
}
