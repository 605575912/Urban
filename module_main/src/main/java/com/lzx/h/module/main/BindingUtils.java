package com.lzx.h.module.main;

import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.lib.ImageUtils;
import com.squareup.lib.view.MindleViewPager;

import java.util.List;

/**
 * Created by liangzhenxiong on 2017/11/27.
 */

public class BindingUtils {

    @android.databinding.BindingAdapter("banners")
    public static void setBannerImage(MindleViewPager mindleViewPager, final List<BannerModel> bannerModels) {
        mindleViewPager.setAdapter(new MindleViewPager.LunAdapter() {
            @Override
            public View getview(ViewGroup container, int position) {
                SimpleDraweeView simpleDraweeView = new SimpleDraweeView(container.getContext());
                ImageUtils.loadImage(bannerModels.get(position).getImgurl(), simpleDraweeView, ScalingUtils.ScaleType.CENTER_CROP);
                return simpleDraweeView
                        ;
            }
        },bannerModels, R.drawable.mtadvert_indicator_selected, R.drawable.mtadvert_indicator_normal);
//        mindleViewPager.setAdapter({container, position ->
//                val imageView =
//                (container.context)
//                imageView.scaleType = ImageView.ScaleType.CENTER_CROP
//
//                imageView.setOnClickListener{}
//                imageView
//        }, bannerModels, R.drawable.mtadvert_indicator_selected, R.drawable.mtadvert_indicator_normal)


    }

    @android.databinding.BindingAdapter("iconurl")
    public static void setImage(SimpleDraweeView iv, String userface) {
        ImageUtils.loadImage(iv.getContext(), userface, iv, R.drawable.abc_btn_default_mtrl_shape);
    }

    @android.databinding.BindingAdapter("setuserhead")
    public static void setuserhead(SimpleDraweeView iv, String str) {
        ImageUtils.loadImage(str, iv);
    }
}
