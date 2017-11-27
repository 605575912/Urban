//package com.lzx.h.module.main
//
//import com.facebook.drawee.drawable.ScalingUtils
//import com.facebook.drawee.view.SimpleDraweeView
//import com.squareup.lib.ImageUtils
//import com.squareup.lib.view.MindleViewPager
//
///**
// * Created by liangzhenxiong on 2017/11/25.
// */
//class BindingAdapter {
//    companion object {
//        @android.databinding.BindingAdapter("banners")
//        @JvmStatic
//        fun setBannerImage(mindleViewPager: MindleViewPager, bannerModels: List<BannerModel>) {
//            mindleViewPager.setAdapter({ container, position ->
//                val imageView = SimpleDraweeView(container.context)
//                ImageUtils.loadImage(bannerModels[position].getImgurl(), imageView, ScalingUtils.ScaleType.CENTER_CROP)
//                imageView.setOnClickListener { }
//                imageView
//            }, bannerModels, R.drawable.mtadvert_indicator_selected, R.drawable.mtadvert_indicator_normal)
//
//
//        }
//
//
//    }
//
//}