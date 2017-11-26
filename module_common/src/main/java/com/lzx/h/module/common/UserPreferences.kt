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
        return shared.getString("name", "")
    }

    fun getuserpsd(): String {
        return shared.getString("psw", "")
    }

    fun setUserModel(userModel: UserModel) {
        var edit = shared.edit()
        edit.putString("name", userModel.name)
        edit.putInt("age", userModel.age)
        edit.putInt("sex", userModel.sex)
        edit.putString("psw", userModel.psw)
        edit.apply()
    }

    fun setImage(image: String) {
        var edit = shared.edit()
        edit.putString("image", image)
        edit.apply()
    }
    fun islogin(): Boolean {

        if (getusername().isNullOrEmpty()) {
            return false
        }
        if (getuserpsd().isNullOrEmpty()) {
            return false
        }
        return true

    }

}