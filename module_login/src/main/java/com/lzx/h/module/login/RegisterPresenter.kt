package com.lzx.h.module.login

import android.app.Activity
import android.view.View
import android.widget.RadioButton
import com.lzx.h.module.common.DefaultDialog
import com.lzx.h.module.common.DefaultLinstener
import com.lzx.h.module.common.UserPreferences
import com.lzx.h.module.common.web.WebActivity
import com.squareup.lib.HttpUtils
import com.squareup.lib.utils.ToastUtils

/**
 * Created by liangzhenxiong on 2017/11/24.
 */
class RegisterPresenter constructor(activity: Activity) {
    var activity = activity

    constructor(name: String, activity: Activity) : this(activity) {

    }

    fun manonclick(view: View) {
        var us = UserPreferences()
        ToastUtils.showToast("即将推出！")
    }

    fun urlonclick(view: View) {
        WebActivity.StartActivity(activity, "http://hd.youyuan.com//html/protocol/index.html?protocol=交友赚钱")
    }

    fun registeronclick(view: View) {
        var dialog = DefaultDialog()
        dialog.showLoading(activity, object : DefaultLinstener() {


        })
        HttpUtils.INSTANCE.getAsynThreadHttp("file:///android_asset/", String::class.java, object : HttpUtils.HttpListener {
            override fun failed(model: Any?) {
            }

            override fun success(model: Any?, data: String?) {
            }
        })

    }

    fun womenonclick(view: View) {
        view as RadioButton
//        RegisterActivity.StartActivity(activity)
//        activity.finish()

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