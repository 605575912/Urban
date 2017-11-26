//package com.lzx.h.module.login
//
//import android.app.Activity
//import android.view.View
//import com.alibaba.android.arouter.facade.Postcard
//import com.alibaba.android.arouter.facade.callback.NavigationCallback
//import com.alibaba.android.arouter.launcher.ARouter
//import com.lzx.h.module.common.UserPreferences
//import com.squareup.lib.utils.ToastUtils
//
///**
// * Created by liangzhenxiong on 2017/11/24.
// */
//class LoginPresenter constructor(activity: Activity) {
//    var activity = activity
//
//    constructor(name: String, activity: Activity) : this(activity) {
//
//    }
//
//    fun womenonclick(view: View) {
//        var us = UserPreferences()
//        ToastUtils.showToast("即将推出！")
//    }
//
//    fun manonclick(view: View) {
//        var ob = ARouter.getInstance().build("/main/mainactivity").navigation(activity.applicationContext, object : NavigationCallback {
//            override fun onLost(postcard: Postcard?) {
////                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onFound(postcard: Postcard?) {
////                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//
//                activity.finish()
//            }
//
//            override fun onInterrupt(postcard: Postcard?) {
////                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onArrival(postcard: Postcard?) {
////                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//        })
//
//    }
//}