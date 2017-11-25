package com.lzx.h.module.common.utils

import android.graphics.Color
import android.view.View

/**
 * Created by liangzhenxiong on 2017/11/25.
 */
class BindingAdapter {
    companion object {
        @android.databinding.BindingAdapter("background")

        @JvmStatic
        fun background(view: View?, color: String?) {
            view?.setBackgroundColor(Color.parseColor(color))
        }
    }

}