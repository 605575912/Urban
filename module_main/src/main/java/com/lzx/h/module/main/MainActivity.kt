//package com.lzx.h.module.main
//
//import android.Manifest
//import android.app.Activity
//import android.content.Intent
//import android.os.Bundle
//import com.alibaba.android.arouter.facade.annotation.Route
//import com.lzx.h.module.main.databinding.ActivityMainBinding
//import com.squareup.lib.activity.BaseActivity
//import com.squareup.lib.activity.PermissionHandler
//import com.squareup.lib.activity.PermissionsGrantActivity
//
//@Route(path = "/main/mainactivity")
//class MainActivity : BaseActivity() {
//    override fun isAllTranslucentStatus(): Boolean {
//        return true
//    }
//
//    private var activitybind: ActivityMainBinding? = null
//    override fun isTranslucentStatus(): Boolean {
//        return true
//    }
//
//    companion object {
//        fun StartActivity(activity: Activity) {
//            var intent = Intent(activity, (MainActivity::class.java))
//            activity.startActivity(intent)
//        }
//    }
//
//
//    override fun setFromLayoutID(): Int = R.layout.activity_main
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        activitybind = viewDataBinding as ActivityMainBinding
////        ImageUtils.loadWebpImage("asset:///50.webp", activitybind!!.image)
//
////
//        PermissionsGrantActivity.grantPermissions(this, arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), object : PermissionHandler {
//            override fun onPermissionsResult(grantedpermissions: Array<String>?, denied_permissions: Array<String>?) {
//                //                        if (denied_permissions == null || camera == null) {
//                //                        }
////                val apkinstall = ApkInstall()
////                apkinstall.install(this@MainActivity)
//            }
//        })
//
//    }
//
//    override fun onPause() {
////        activitybind!!.image.visibility = View.GONE
//        super.onPause()
//    }
//
//}
