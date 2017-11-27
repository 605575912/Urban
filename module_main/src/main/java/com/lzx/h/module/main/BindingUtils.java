package com.lzx.h.module.main;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.squareup.lib.BaseApplication;
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
        ImageUtils.loadImage(userface, iv, R.drawable.abc_btn_default_mtrl_shape, null, ScalingUtils.ScaleType.CENTER_CROP);
    }

    @android.databinding.BindingAdapter("iconbackground")
    public static void seticonbackground(final RelativeLayout iv, String url) {
        if (TextUtils.isEmpty(url)) {
            iv.setBackgroundResource(R.drawable.useritem_bg);
        } else {
            ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).setProgressiveRenderingEnabled(true).build();
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, BaseApplication.getApplication());
            dataSource.subscribe(new BaseBitmapDataSubscriber() {
                @Override
                public void onNewResultImpl(Bitmap bitmap) {
                    iv.setBackground(new BitmapDrawable(bitmap));
//                    if (isImageDownloaded(Uri.parse(url), context)) {
//                        File file1 = getCachedImageOnDisk(Uri.parse(url), context);
//                        if (file1 != null) {
//                            ImageFormat imageFormat = ImageFormatChecker.getImageFormat(file1.getPath());
//                            File newfiel = new File(file1.getPath().replace(".cnt", "." + imageFormat.getName()));
//                            file1.renameTo(newfiel);
//                            if (newfiel.exists() && handler != null) {
//                                handler.obtainMessage(0, newfiel.getAbsolutePath()).sendToTarget();
//                            }
//                        }
//                    }
                }

                @Override
                public void onFailureImpl(DataSource dataSource) {

                }
            }, CallerThreadExecutor.getInstance());
        }
//        ImageUtils.loadImage(userface, iv, R.drawable.abc_btn_default_mtrl_shape, null, ScalingUtils.ScaleType.CENTER_CROP);
    }

    @android.databinding.BindingAdapter("setuserhead")
    public static void setuserhead(SimpleDraweeView iv, String str) {
        ImageUtils.loadImage(str, iv);
    }
}
