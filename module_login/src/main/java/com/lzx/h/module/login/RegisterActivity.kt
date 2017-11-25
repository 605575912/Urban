package com.lzx.h.module.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.lzx.h.module.login.databinding.RegisterLayoutBinding
import com.squareup.lib.activity.BaseActivity

/**
 * Created by liangzhenxiong on 2017/11/22.
 */
class RegisterActivity : BaseActivity() {
    override fun isAllTranslucentStatus(): Boolean {
        return false
    }

    override fun isTranslucentStatus(): Boolean {
        return true
    }

    companion object {
        open fun StartActivity(activity: Activity) {
            var intent = Intent(activity, (RegisterActivity::class.java))
            activity.startActivity(intent)
        }
    }

    override fun setFromLayoutID(): Int {
        return R.layout.register_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
//        ARouter.getInstance().inject(this)
        super.onCreate(savedInstanceState)
        var loginview = viewDataBinding as RegisterLayoutBinding
        loginview.presenter = RegisterPresenter(activity)
    }
}