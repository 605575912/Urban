package com.lzx.h.urban.launcher

import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.lzx.h.urban.R
import com.squareup.lib.activity.BaseActivity

class LaucherActivity : BaseActivity() {
    var laucher: LaucherPresenter? = null
    override fun isAllTranslucentStatus(): Boolean {
        return true
    }


    //    private var activitybind: ActivityLaucherBinding? = null
    override fun isTranslucentStatus(): Boolean {
        return true
    }

    override fun setFromLayoutID(): Int = R.layout.activity_laucher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        activitybind = viewDataBinding as ActivityLaucherBinding
        laucher = LaucherPresenter()
        var handler = object : Handler() {
            override fun handleMessage(msg: Message?) {
                super.handleMessage(msg)
            }
        }
        handler.postDelayed({
            laucher?.start(this, handler)
        }, 2000)

    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()
    }

}
