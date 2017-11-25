package com.lzx.h.module.login

import android.app.Activity
import android.view.View
import com.lzx.h.module.common.UserPreferences
import com.squareup.lib.utils.ToastUtils

/**
 * Created by liangzhenxiong on 2017/11/24.
 */
class LoginShowPresenter constructor(activity: Activity) {
    var activity = activity

    constructor(name: String, activity: Activity) : this(activity) {

    }

    fun loginonclick(view: View) {
        var us = UserPreferences()
        ToastUtils.showToast("即将推出！")
    }

    fun registeronclick(view: View) {

        RegisterActivity.StartActivity(activity)
        activity.finish()

//        var ob = ARouter.getInstance().build("/main/mainactivity").navigation(activity.applicationContext, object : NavigationCallback {
//            override fun onLost(postcard: Postcard?) {
////                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onFound(postcard: Postcard?) {
////                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//
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

    }
}