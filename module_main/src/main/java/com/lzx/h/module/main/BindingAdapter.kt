package com.lzx.h.module.main

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.facebook.drawee.view.SimpleDraweeView
import com.squareup.lib.ImageUtils
import com.squareup.lib.view.MindleViewPager

/**
 * Created by liangzhenxiong on 2017/11/25.
 */
class BindingAdapter {
    companion object {
        @BindingAdapter("banners")
        @JvmStatic
        fun setBannerImage(mindleViewPager: MindleViewPager, bannerModels: List<BannerModel>) {
            mindleViewPager.setAdapter({ container, position ->
                val imageView = SimpleDraweeView(container.context)
                imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                ImageUtils.loadImage(bannerModels[position].getImgurl(), imageView)
                imageView.setOnClickListener { }
                imageView
            }, bannerModels, R.drawable.mtadvert_indicator_selected, R.drawable.mtadvert_indicator_normal)


        }
    }

}