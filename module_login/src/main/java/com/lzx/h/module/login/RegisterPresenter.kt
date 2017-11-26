package com.lzx.h.module.login

import android.app.Activity
import android.os.Handler
import android.os.Message
import android.view.View
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.lzx.h.module.common.DefaultDialog
import com.lzx.h.module.common.DefaultLinstener
import com.lzx.h.module.common.UserModel
import com.lzx.h.module.common.UserPreferences
import com.lzx.h.module.common.web.WebActivity
import com.squareup.lib.HttpUtils
import com.squareup.lib.utils.AppLibUtils
import com.squareup.lib.utils.ToastUtils

/**
 * Created by liangzhenxiong on 2017/11/24.
 */
class RegisterPresenter constructor(activity: Activity) {
    var activity = activity
    var isman: Int = -1
    var handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
        }
    }

    constructor(name: String, activity: Activity) : this(activity) {

    }

    fun manonclick(view: View) {
        isman = 0
    }

    fun urlonclick(view: View) {
        WebActivity.StartActivity(activity, "http://hd.youyuan.com//html/protocol/index.html?protocol=交友赚钱")
    }

    fun registeronclick(view: View) {
        if (isman == -1) {
            ToastUtils.showToast("请选择性别！")
            return
        }

        var dialog = DefaultDialog()
        dialog.showLoading(activity, object : DefaultLinstener() {


        })
        HttpUtils.INSTANCE.getAsynThreadHttp("file:///android_asset/register.txt", UserModel::class.java, object : HttpUtils.HttpListener {
            override fun failed(model: Any?) {
                dialog.cancel()
            }

            override fun success(model: Any?, data: String?) {
                var user = model as UserModel
                AppLibUtils.setToken(user.token)
                var userper = UserPreferences()
                userper.setUserModel(user)
                ToastUtils.showToast("欢迎你！注册成功")
                handler.postDelayed(
                        {
                            var ob = ARouter.getInstance().build("/image/choose").navigation(activity.applicationContext, object : NavigationCallback {
                                override fun onLost(postcard: Postcard?) {
                                }

                                override fun onFound(postcard: Postcard?) {

                                }

                                override fun onInterrupt(postcard: Postcard?) {
                                }

                                override fun onArrival(postcard: Postcard?) {
                                }
                            })
                            dialog.cancel()
                            activity.finish()
                        }, 1000)
            }
        })

    }

    fun womenonclick(view: View) {
        isman = 1


    }
}