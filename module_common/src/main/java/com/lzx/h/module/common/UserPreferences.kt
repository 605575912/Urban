package com.lzx.h.module.common

import android.content.SharedPreferences
import com.squareup.lib.BaseApplication
import com.squareup.lib.utils.CryptSharedPreferences

/**
 * Created by liangzhenxiong on 2017/11/22.
 */
class UserPreferences() {
    var shared: SharedPreferences = CryptSharedPreferences.getDefaultSharedPreferences(BaseApplication.getApplication())

    fun getusername(): String {
        return ""
    }

    fun getuserpse(): String {
        return ""
    }

    fun islogin(): Boolean {

        if (getusername().isNullOrEmpty()) {
            return false
        }
        if (getuserpse().isNullOrEmpty()) {
            return false
        }
        return true

    }

}