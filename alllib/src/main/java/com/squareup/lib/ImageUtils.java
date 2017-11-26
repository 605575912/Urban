package com.squareup.lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ImageView;

import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imageformat.ImageFormatChecker;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.squareup.lib.utils.IProguard;

import java.io.File;


public class ImageUtils implements IProguard.ProtectClassAndMembers {


    /**
     * 加载网络图片
     *
     * @param url
     * @param imageView
     */
    public static void loadImage(String url, SimpleDraweeView imageView) {
        loadImage(url, imageView, 0, null);
    }

    public static void loadImage(String url, SimpleDraweeView imageView, Drawable drawable) {
        loadImage(url, imageView, 0, drawable);
    }

    private static void loadImage(String url, SimpleDraweeView imageView, int defaultResId, Drawable drawable) {
        if (imageView == null) {
            return;
        }
        if (TextUtils.isEmpty(url)) {
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setAutoPlayAnimations(true)

                    .build();
            GenericDraweeHierarchy hierarchy = imageView.getHierarchy();
            if (hierarchy != null)
                if (drawable != null) {
                    hierarchy.setPlaceholderImage(drawable);
                } else if (defaultResId != 0) {
                    try {
                        drawable = BaseApplication.getApplication().getResources().getDrawable(defaultResId);
                        hierarchy.setPlaceholderImage(drawable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            imageView.setController(controller);
            return;
        }
        if (url.startsWith("asset://")) {
            Uri uri = Uri.parse(url);
            imageView.setImageURI(uri);
            return;
        }
        if (url.startsWith("file://")) {
            Uri uri = Uri.parse(url);
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setAutoPlayAnimations(true)
                    .setUri(uri)
                    .setOldController(imageView.getController())
                    .build();
            GenericDraweeHierarchy hierarchy = imageView.getHierarchy();
            if (hierarchy != null)
                if (drawable != null) {
                    hierarchy.setPlaceholderImage(drawable);
                } else if (defaultResId != 0) {
                    try {
                        drawable = BaseApplication.getApplication().getResources().getDrawable(defaultResId);
                        hierarchy.setPlaceholderImage(drawable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            imageView.setController(controller);
            return;
        } else if (url.startsWith("http:")) {
            {
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setUri(url)
                        .setOldController(imageView.getController())
                        .setAutoPlayAnimations(true)
                        .build();
                GenericDraweeHierarchy hierarchy = imageView.getHierarchy();
                if (hierarchy != null && defaultResId != 0) {
                    hierarchy.setPlaceholderImage(defaultResId);
//            RoundingParams roundingParams = new RoundingParams();
//            roundingParams.setCornersRadius(Resources.getSystem().getDisplayMetrics().density * radius);
//            hierarchy.setRoundingParams(roundingParams);
                }
                imageView.setController(controller);
            }
            return;
        } else {
            Uri uri = Uri.parse("file://" + url);
            imageView.setImageURI(uri);
        }
    }

    public static void loadCircleImage(Context context, String url, ImageView imageView, int defaultResId) {
//        if (defaultResId == 0) {
//            if (TextUtils.isEmpty(url)) {
//                imageView.setImageResource(defaultResId);
//                return;
//            }
//            Glide.with(context).load(url).into(imageView);
//        } else {
//            try {
//                Drawable drawable = context.getResources().getDrawable(defaultResId);
//                loadCircleImage(context, url, imageView, drawable);
//            } catch (Exception e) {
//                if (TextUtils.isEmpty(url)) {
//                    return;
//                }
//                Glide.with(context).load(url).into(imageView);
//            }
//
//        }
    }

    public static void loadRoundImage(Context context, String url, ImageView imageView, int defaultResId) {
//        if (defaultResId == 0) {
//            if (TextUtils.isEmpty(url)) {
//                imageView.setImageResource(defaultResId);
//                return;
//            }
//            Glide.with(context).load(url).into(imageView);
//        } else {
//            try {
//                Drawable drawable = context.getResources().getDrawable(defaultResId);
//                loadRoundImage(context, url, imageView, drawable);
//            } catch (Exception e) {
//                if (TextUtils.isEmpty(url)) {
//                    return;
//                }
//                Glide.with(context).load(url).into(imageView);
//            }
//
//        }
    }

    public static void loadRoundImage(Context context, String url, final ImageView imageView, Drawable drawable) {
//        if (TextUtils.isEmpty(url)) {
//            imageView.setImageDrawable(drawable);
//            return;
//        }
//        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                .placeholder(drawable)
//                .error(drawable)
//                .priority(Priority.HIGH).dontAnimate().transform(new RoundTransform(20, imageView));
//        Glide.with(context).load(url).apply(options).into(imageView);
    }

    public static void loadCircleImage(Context context, String url, SimpleDraweeView imageView, Drawable drawable) {
//        if (TextUtils.isEmpty(url)) {
//            imageView.setImageDrawable(drawable);
//            return;
//        }
//        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                .placeholder(drawable)
//                .error(drawable)
//                .priority(Priority.HIGH).dontAnimate().transform(new GlideCircleTransform(context));
//        Glide.with(context).load(url).apply(options).into(imageView);
    }

    public static void loadWebpImage( String url, SimpleDraweeView imageView) {
//        if (TextUtils.isEmpty(url)) {
//            imageView.setImageDrawable(drawable);
//            return;
//        }
//        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                .placeholder(drawable)
//                .error(drawable)
//                .priority(Priority.HIGH).dontAnimate();
//        Glide.with(context).load(url).apply(options).into(imageView);
        if (imageView == null) {
            return;
        }
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(url)
                .setAutoPlayAnimations(true)
                .build();
        GenericDraweeHierarchy hierarchy = imageView.getHierarchy();
//        if (hierarchy != null && defaultResId != 0) {
//            hierarchy.setPlaceholderImage(defaultResId);
////            RoundingParams roundingParams = new RoundingParams();
////            roundingParams.setCornersRadius(Resources.getSystem().getDisplayMetrics().density * radius);
////            hierarchy.setRoundingParams(roundingParams);
//        }
        imageView.setController(controller);
    }

    public static void loadImage(Context context, String url, SimpleDraweeView imageView, int defaultResId) {
//        if (TextUtils.isEmpty(url)) {
//            imageView.setImageDrawable(drawable);
//            return;
//        }
//        RequestOptions options = new RequestOptions()
//                .centerCrop()
//                .placeholder(drawable)
//                .error(drawable)
//                .priority(Priority.HIGH).dontAnimate();
//        Glide.with(context).load(url).apply(options).into(imageView);
        if (imageView == null) {
            return;
        }
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(url)
                .setAutoPlayAnimations(true)
                .build();
        GenericDraweeHierarchy hierarchy = imageView.getHierarchy();
//        if (hierarchy != null && defaultResId != 0) {
//            hierarchy.setPlaceholderImage(defaultResId);
////            RoundingParams roundingParams = new RoundingParams();
////            roundingParams.setCornersRadius(Resources.getSystem().getDisplayMetrics().density * radius);
////            hierarchy.setRoundingParams(roundingParams);
//        }
        imageView.setController(controller);
    }

    //    public static void loadImageAsBitmap(Context context, String url, ImageView imageView, int defaultResId) {
//        Glide.with(context).load(url).asBitmap().centerCrop().placeholder(defaultResId).into(imageView);
//    }
//    static class RoundTransform extends BitmapTransformation {
//        int radius;
//        ImageView imageView;
//
//        public RoundTransform(int radius, ImageView imageView) {
//            this.radius = radius;
//            this.imageView = imageView;
//        }
//
//        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
//            return circleCrop(toTransform, outWidth, outHeight);
//        }
//
//        private Bitmap circleCrop(Bitmap source, int outWidth, int outHeight) {
//            if (source == null) return null;
//            if (imageView != null) {
//                outHeight = imageView.getMeasuredHeight();
//                outWidth = imageView.getMeasuredWidth();
//            }
//            Bitmap result = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);
//            int sourcewidth = source.getWidth();
//            int sourceheight = source.getHeight();
//            float sale = sourcewidth * 1.0f / sourceheight;
//            float outsale = outWidth * 1.0f / outHeight;
//            float destwidth;
//            float destheight;
//            Rect srcR = new Rect(0, 0, 0, 0);
//            if (outsale > sale) {
//                destheight = (sourcewidth / outsale);
//                srcR.right = sourcewidth;
//                srcR.top = (int) ((sourceheight - destheight) / 2);
//                srcR.bottom = srcR.top + (int) destheight;
//            } else {
//                destwidth = (sourceheight * outsale);
//                srcR.left = (int) ((sourcewidth - destwidth) / 2);
//                srcR.right = srcR.left + (int) destwidth;
//                srcR.bottom = sourceheight;
//            }
//            Canvas canvas = new Canvas(result);
//            Paint paint = new Paint();
//            paint.setAntiAlias(true);
//            RectF rect = new RectF(0, 0, outWidth, outHeight);
//            canvas.drawRoundRect(rect, radius, radius, paint);
//            paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
//            canvas.drawBitmap(source, srcR, rect, paint);
//            return result;
//        }
//
//
//        @Override
//        public void updateDiskCacheKey(MessageDigest messageDigest) {
//
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            return false;
//        }
//
//        @Override
//        public int hashCode() {
//            return 0;
//        }
//
//
//    }
//
//    static class GlideCircleTransform extends BitmapTransformation {
//
//        private Paint mBorderPaint;
//        private float mBorderWidth;
//
//        public GlideCircleTransform(Context context) {
//        }
//
//        public GlideCircleTransform(Context context, int borderWidth, int borderColor) {
////            mBorderWidth = Resources.getSystem().getDisplayMetrics().density * borderWidth;
//            mBorderWidth = borderWidth;
//
//            mBorderPaint = new Paint();
//            mBorderPaint.setDither(true);
//            mBorderPaint.setAntiAlias(true);
//            mBorderPaint.setColor(borderColor);
//            mBorderPaint.setStyle(Paint.Style.STROKE);
//            mBorderPaint.setStrokeWidth(mBorderWidth);
//        }
//
//
//        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
//            return circleCrop(pool, toTransform);
//        }
//
//        private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
//            if (source == null) return null;
//
//            int size = (int) (Math.min(source.getWidth(), source.getHeight()) - (mBorderWidth / 2));
//            int x = (source.getWidth() - size) / 2;
//            int y = (source.getHeight() - size) / 2;
//            // TODO this could be acquired from the pool too
//            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
//            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
//            if (result == null) {
//                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
//            }
//            Canvas canvas = new Canvas(result);
//            Paint paint = new Paint();
//            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
//            paint.setAntiAlias(true);
//            float r = size / 2f;
//            canvas.drawCircle(r, r, r, paint);
//            if (mBorderPaint != null) {
//                float borderRadius = r - mBorderWidth / 2;
//                canvas.drawCircle(r, r, borderRadius, mBorderPaint);
//            }
//            return result;
//        }
//
//
//        @Override
//        public void updateDiskCacheKey(MessageDigest messageDigest) {
//
//        }
//
//        @Override
//        public boolean equals(Object o) {
//            return false;
//        }
//
//        @Override
//        public int hashCode() {
//            return 0;
//        }
//
//
//    }
//    /**
//     * 加载带尺寸的图片
//     *
//     * @param context
//     * @param url
//     * @param width
//     * @param height
//     * @param imageView
//     */
//    public static void loadImageWithSize(Context context, String url, int width, int height, ImageView imageView) {
//        Glide.with(context).load(url).override(width, height).into(imageView);
//    }

    /**
     * 加载本地图片
     *
     * @param context
     * @param path
     * @param imageView
     */
    public static void loadImageWithLocation(Context context, Integer path, ImageView imageView) {
//        Glide.with(context).load(path).into(imageView);
    }

//    /**
//     * 圆形加载
//     *
//     * @param context
//     * @param url
//     * @param imageview
//     */
//    public static void loadCircleImage(Context context, String url, ImageView imageview, int defaultResId) {
//        Glide.with(context).load(url).centerCrop().placeholder(defaultResId)
//                .transform(new GlideCircleTransform(context)).into(imageview);
//
//    }

//    /**
//     * 圆形加载
//     *
//     * @param context
//     * @param url
//     * @param imageview
//     */
//    public static void loadCircleImageWithBorder(Context context, String url, ImageView imageview, int defaultResId, int borderWidth, int borderColor) {
//        Glide.with(context).load(url).centerCrop().placeholder(defaultResId)
//                .transform(new GlideCircleTransform(context, borderWidth, borderColor)).into(imageview);
//    }

//    static class GlideCircleTransform extends BitmapTransformation {
//
//        private Paint mBorderPaint;
//        private float mBorderWidth;
//
//        public GlideCircleTransform(Context context) {
//            super(context);
//        }
//
//        public GlideCircleTransform(Context context, int borderWidth, int borderColor) {
//            super(context);
////            mBorderWidth = Resources.getSystem().getDisplayMetrics().density * borderWidth;
//            mBorderWidth = borderWidth;
//
//            mBorderPaint = new Paint();
//            mBorderPaint.setDither(true);
//            mBorderPaint.setAntiAlias(true);
//            mBorderPaint.setColor(borderColor);
//            mBorderPaint.setStyle(Paint.Style.STROKE);
//            mBorderPaint.setStrokeWidth(mBorderWidth);
//        }
//
//
//        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
//            return circleCrop(pool, toTransform);
//        }
//
//        private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
//            if (source == null) return null;
//
//            int size = (int) (Math.min(source.getWidth(), source.getHeight()) - (mBorderWidth / 2));
//            int x = (source.getWidth() - size) / 2;
//            int y = (source.getHeight() - size) / 2;
//            // TODO this could be acquired from the pool too
//            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
//            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
//            if (result == null) {
//                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
//            }
//            Canvas canvas = new Canvas(result);
//            Paint paint = new Paint();
//            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
//            paint.setAntiAlias(true);
//            float r = size / 2f;
//            canvas.drawCircle(r, r, r, paint);
//            if (mBorderPaint != null) {
//                float borderRadius = r - mBorderWidth / 2;
//                canvas.drawCircle(r, r, borderRadius, mBorderPaint);
//            }
//            return result;
//        }
//
//        @Override
//        public String getId() {
//            return getClass().getName();
//        }
//    }


    /**
     * @param context
     * @param url
     * @param handler
     */
    public static File download(Context context, String url, Handler handler) {
//        download(context, url, handler, Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
        return downLoadImg(context, url, handler);
    }

    private static File downLoadImg(final Context context, final String url, final Handler handler) {
        if (isImageDownloaded(Uri.parse(url), context)) {
            File file1 = getCachedImageOnDisk(Uri.parse(url), context);
            if (file1 == null) {
                return null;
            }
            ImageFormat imageFormat = ImageFormatChecker.getImageFormat(file1.getPath());
            File newfiel = new File(file1.getPath().replace(".cnt", "." + imageFormat.getName()));
            if (!newfiel.exists()) {
                file1.renameTo(newfiel);
            }
            if (newfiel.exists()) {
                if (handler != null) {
                    handler.obtainMessage(0, newfiel.getAbsolutePath()).sendToTarget();
                }
                return newfiel;
            }
        } else {
            ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).setProgressiveRenderingEnabled(true).build();
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(imageRequest, context);
            dataSource.subscribe(new BaseBitmapDataSubscriber() {
                @Override
                public void onNewResultImpl(Bitmap bitmap) {
                    if (isImageDownloaded(Uri.parse(url), context)) {
                        File file1 = getCachedImageOnDisk(Uri.parse(url), context);
                        if (file1 != null) {
                            ImageFormat imageFormat = ImageFormatChecker.getImageFormat(file1.getPath());
                            File newfiel = new File(file1.getPath().replace(".cnt", "." + imageFormat.getName()));
                            file1.renameTo(newfiel);
                            if (newfiel.exists() && handler != null) {
                                handler.obtainMessage(0, newfiel.getAbsolutePath()).sendToTarget();
                            }
                        }
                    }
                }

                @Override
                public void onFailureImpl(DataSource dataSource) {

                }
            }, CallerThreadExecutor.getInstance());
        }
        return null;
    }

    private static boolean isImageDownloaded(Uri loadUri, Context context) {
        if (loadUri == null) {
            return false;
        }
        CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(ImageRequest.fromUri(loadUri), context);
        return ImagePipelineFactory.getInstance().getMainFileCache().hasKey(cacheKey) || ImagePipelineFactory.getInstance().getSmallImageFileCache().hasKey(cacheKey);
    }

    //return file or null
    private static File getCachedImageOnDisk(Uri loadUri, Context context) {
        File localFile = null;
        if (loadUri != null) {
            CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(ImageRequest.fromUri(loadUri), context);
            if (ImagePipelineFactory.getInstance().getMainFileCache().hasKey(cacheKey)) {
                BinaryResource resource = ImagePipelineFactory.getInstance().getMainFileCache().getResource(cacheKey);
                if (resource == null) {
                    return null;
                }
                localFile = ((FileBinaryResource) resource).getFile();
            } else if (ImagePipelineFactory.getInstance().getSmallImageFileCache().hasKey(cacheKey)) {
                BinaryResource resource = ImagePipelineFactory.getInstance().getSmallImageFileCache().getResource(cacheKey);
                if (resource == null) {
                    return null;
                }
                localFile = ((FileBinaryResource) resource).getFile();
            }
        }
        return localFile;
    }
}
