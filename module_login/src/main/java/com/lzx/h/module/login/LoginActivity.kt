package com.lzx.h.module.login

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.lzx.h.module.login.databinding.LoginLayoutBinding
import com.squareup.lib.activity.BaseActivity

/**
 * Created by liangzhenxiong on 2017/11/22.
 */
//@Route(path = "/login/list")
class LoginActivity : BaseActivity() {

//    companion object {
//        open fun StartActivity(activity: Activity) {
//            var intent = Intent(activity, (LoginActivity::class.java))
//            activity.startActivity(intent)
//        }
//    }

    override fun setFromLayoutID(): Int {
        return R.layout.login_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
//        ARouter.getInstance().inject(this)
        super.onCreate(savedInstanceState)
        var loginview = viewDataBinding as LoginLayoutBinding
        loginview.presenter = LoginPresenter(activity)
    }
}