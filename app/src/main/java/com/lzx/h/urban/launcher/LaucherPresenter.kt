package com.lzx.h.urban.launcher

import android.Manifest
import android.os.Handler
import com.alibaba.android.arouter.launcher.ARouter
import com.lzx.h.module.common.UserPreferences
import com.squareup.lib.ThreadManager
import com.squareup.lib.activity.PermissionHandler
import com.squareup.lib.activity.PermissionsGrantActivity

/**
 * Created by liangzhenxiong on 2017/11/22.
 */
open class LaucherPresenter {
    fun islogin(): Boolean {

        var us = UserPreferences()
        if (us.islogin()) {
            return true
        }
        return false
    }

    fun start(activity: LaucherActivity, handler: Handler): Unit {
        ThreadManager.execute(Runnable {


            handler.postDelayed({
                PermissionsGrantActivity.grantPermissions(activity, arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), object : PermissionHandler {
                    override fun onPermissionsResult(grantedpermissions: Array<String>?, denied_permissions: Array<String>?) {
                        //                        if (denied_permissions == null || camera == null) {
                        //                        }
                        if (islogin()) {
                            ARouter.getInstance().build("/main/mainactivity").navigation()
                        } else {
                            ARouter.getInstance().build("/login/list").navigation()
                        }

                        activity.finish()
                    }
                })

            }, 3000)
        })

    }


}