//package com.squareup.lib.apk;
//
//import android.app.Activity;
//
//import com.squareup.lib.BaseApplication;
//
//import java.io.File;
//import java.util.List;
//
//import androidx.pluginmgr.PluginManager;
//import androidx.pluginmgr.environment.PlugInfo;
//
///**
// * Created by liangzhenxiong on 2017/11/24.
// */
//
//public class ApkInstall {
//    public void install(Activity activity) {
//        PluginManager.init(BaseApplication.getApplication());
//        PluginManager plugMgr = PluginManager.getSingleton();
//        try {
//            List<PlugInfo> plugs = plugMgr
//                    .loadPlugin(new File("/sdcard/crash/app-debug.apk"));
//            PluginManager.getSingleton().dump();
////                    setPlugins(plugs);
//            plugMgr.startMainActivity(activity, plugs.get(0));
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
////							dialogLoading.dismiss();
//        }
//    }
//}
