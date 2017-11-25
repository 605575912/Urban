package com.lzx.h.module.login

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.lzx.h.module.common.views.AutoLocateHorizontalView
import com.lzx.h.module.login.databinding.RegisterLayoutBinding
import com.squareup.lib.activity.BaseActivity
import com.squareup.lib.viewfactory.BaseViewItem
import com.squareup.lib.viewfactory.RecyclerViewAdapter
import java.util.*

/**
 * Created by liangzhenxiong on 2017/11/22.
 */
class RegisterActivity : BaseActivity() {
    private val contentlist = ArrayList<BaseViewItem>()
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
        contentlist.add(AgeItem(Color.BLACK, "20岁"))
        contentlist.add(AgeItem(Color.BLUE, "21岁"))
        contentlist.add(AgeItem(Color.RED, "22岁"))
        contentlist.add(AgeItem(Color.YELLOW, "23岁"))
        contentlist.add(AgeItem(Color.DKGRAY, "24岁"))
        contentlist.add(AgeItem(Color.WHITE, "25岁"))
        contentlist.add(AgeItem(Color.LTGRAY, "26岁"))
        contentlist.add(AgeItem(Color.DKGRAY, "27岁"))
        contentlist.add(AgeItem(Color.BLACK, "28岁"))
        contentlist.add(AgeItem(Color.BLACK, "29岁"))
        contentlist.add(AgeItem(Color.BLACK, "30岁"))
        contentlist.add(AgeItem(Color.MAGENTA, "31岁"))
        contentlist.add(AgeItem(Color.BLACK, "32岁"))
        contentlist.add(AgeItem(Color.BLACK, "33岁"))
//        var contentadapter = RecyclerViewAdapter(this@RegisterActivity, contentlist)
//        val linearLayoutManager = LinearLayoutManager(this)
//        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
//        loginview.recyclercontent.setLayoutManager(linearLayoutManager)
//        loginview.recyclercontent.setOnSelectedPositionChangedListener(object : AutoLocateHorizontalView.OnSelectedPositionChangedListener {
//
//
//            override fun selectedPositionChanged(pos: Int) {
//            }
//        })
//        loginview.recyclercontent.setInitPos(5)
//        loginview.recyclercontent.setAdapter(contentadapter)

//        if (loginview.recyclercontent != null) {
//            val wrapContentLinearLayoutManager = WrapContentLinearLayoutManager(this@RegisterActivity)
//            loginview.recyclercontent.setHasFixedSize(true)
//            loginview.recyclercontent.setLayoutManager(wrapContentLinearLayoutManager)
//            loginview.recyclercontent.setAdapter(contentadapter)
//        }
    }
}