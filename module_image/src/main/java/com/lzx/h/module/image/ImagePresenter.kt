package com.lzx.h.module.image

import android.Manifest
import android.app.Activity
import android.os.Handler
import android.os.Message
import android.view.View
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.lzx.h.module.common.DefaultDialog
import com.lzx.h.module.common.DefaultLinstener
import com.lzx.h.module.common.UserPreferences
import com.squareup.lib.activity.PermissionHandler
import com.squareup.lib.activity.PermissionsGrantActivity

/**
 * Created by liangzhenxiong on 2017/11/24.
 */
class ImagePresenter constructor(activity: Activity) {
    var activity = activity
    var handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
        }
    }

    constructor(name: String, activity: Activity) : this(activity) {

    }

    var dir: String? = null
    fun chooseonclick(view: View) {
        PermissionsGrantActivity.grantPermissions(activity, arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE), object : PermissionHandler {
            override fun onPermissionsResult(grantedpermissions: Array<String>?, denied_permissions: Array<String>?) {
                if (denied_permissions == null) {
                    ChooseActivity.start(activity)
                }
            }
        })
    }

    fun uploadonclick(view: View) {
        if (dir.isNullOrEmpty()) {
            chooseonclick(view)
        } else {

            var dialog = DefaultDialog()
            dialog.showLoading(activity, object : DefaultLinstener() {


            })
            handler.postDelayed({
                var user = UserPreferences()
                user.setImage(this!!.dir!!)
                outonclick(view)
            }, 1000)

        }

    }

    fun outonclick(view: View) {
        var ob = ARouter.getInstance().build("/main/mainactivity").navigation(activity.applicationContext, object : NavigationCallback {
            override fun onLost(postcard: Postcard?) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onFound(postcard: Postcard?) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

                activity.finish()
            }

            override fun onInterrupt(postcard: Postcard?) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onArrival(postcard: Postcard?) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })

    }
}