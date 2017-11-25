package com.squareup.lib.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.content.PermissionChecker

/**
 * Created by Administrator on 2017/1/20.
 */
object PermissionUtil {
    fun selfPermissionGranted(context: Context, permission: String): Boolean {
        // For Android < Android M, self permissions are always granted.
        var result = true

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (getTargetSdkVersion(context) >= Build.VERSION_CODES.M) {
                // targetSdkVersion >= Android M, we can
                // use Context#checkSelfPermission
                result = context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
            } else {
                // targetSdkVersion < Android M, we have to use PermissionChecker
                result = PermissionChecker.checkSelfPermission(context, permission) == PermissionChecker.PERMISSION_GRANTED
            }
        }
        return result
    }

    private fun getTargetSdkVersion(context: Context): Int {
        var version = 0
        try {
            val info = context.packageManager.getPackageInfo(
                    context.packageName, 0)
            version = info.applicationInfo.targetSdkVersion
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()

        }

        return version
    }
}
