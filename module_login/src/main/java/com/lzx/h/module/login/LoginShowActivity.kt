package com.lzx.h.module.login

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.lzx.h.module.login.databinding.LoginShowlayoutBinding
import com.squareup.lib.ImageUtils
import com.squareup.lib.activity.BaseActivity


/**
 * Created by liangzhenxiong on 2017/11/22.
 */
@Route(path = "/login/list")
class LoginShowActivity : BaseActivity() {
    override fun isTranslucentStatus(): Boolean {
        return true
    }

    override fun isAllTranslucentStatus(): Boolean {
        return true
    }

    override fun setFromLayoutID(): Int {
        return R.layout.login_showlayout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var loginview = viewDataBinding as LoginShowlayoutBinding
        loginview.presenter = LoginShowPresenter(activity)
        ImageUtils.loadWebpImage("asset:///50.webp", loginview.image)
    }
}