package com.lzx.h.module.main;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lzx.h.module.common.R;
import com.lzx.h.module.common.utils.APPUtils;
import com.squareup.lib.ThreadManager;

/**
 * 高斯背景模糊控件，用布局内的第一个视图做整个控件的背景模糊
 *
 * @author lhy
 */
public class GaussBgBlurLayout extends RelativeLayout {
    //    private final String TAG = getClass().getSimpleName();
    private static final int MSG_INVALIDATE_GAUSSBG = 1; //更新高斯背景
    private ImageView mImageView = null;
    private Bitmap mGaussBitmap = null; //高斯模糊后的图片
    private boolean mDrawGaussBg = true;
    private Handler mUIHandler = null; //UI线程中的Handler
    private Runnable mGaussBlurAction;

    //    private GaussBlugThread mGaussBlurThread ;
    @TargetApi(21)
    public GaussBgBlurLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public GaussBgBlurLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GaussBgBlurLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GaussBgBlurLayout(Context context) {
        super(context);
    }

    public void setDrawGaussBg(boolean flag) {
        mDrawGaussBg = flag;
        if (mImageView != null) {
            mImageView.invalidate(); //通知重画高斯模糊
        }
//	if (mDrawGaussBg ){ //本控件重用时，需要注意线程启动、销毁
//	    if(mGaussBlurThread == null){
//		mGaussBlurThread = new GaussBlugThread();
//		mGaussBlurThread.start();
//	    }
//	}else{
//	    if (mGaussBlurThread != null){
//		mGaussBlurThread.quit();
//		mGaussBlurThread = null ;
//	    }
//	}
    }

    public void setImageBitmap(Bitmap bm) {
        if (mImageView != null) {
            mImageView.setImageBitmap(bm);
        }
    }

    public void setImageDrawable(Drawable d) {
        if (mImageView != null) {
            mImageView.setImageDrawable(d);
        }
    }

    public void setImageResource(int resId) {
        if (mImageView != null) {
            mImageView.setImageResource(resId);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mUIHandler == null) {
            mUIHandler = new MyHandler(this);
        }
        if (mImageView == null) {
            int N = getChildCount();
            View child;
            for (int k = 0; k < N; k++) {
                child = getChildAt(k);
                if (child instanceof ImageView) {
                    mImageView = (ImageView) child;
                    break;
                }
            }
        }
//	if (mDrawGaussBg) {
//	    if (mGaussBlurThread != null) {
//		mGaussBlurThread.quit();
//	    }
//	    mGaussBlurThread = new GaussBlugThread();
//	    mGaussBlurThread.start();
//	}
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//	if (mGaussBlurThread != null){
//	    mGaussBlurThread.quit();
//	    mGaussBlurThread = null ;
//	}
    }

    @Override
    public void dispatchDraw(Canvas canvas) {
        drawGaussBackground(canvas, mGaussBitmap);
        if (mDrawGaussBg && mImageView != null && mImageView.isDirty()) {
//		if (mGaussBlurThread != null) {
            if (mGaussBlurAction == null) {
                Runnable action = new GaussBlurAction(mImageView, mUIHandler);
                mGaussBlurAction = action;
                ThreadManager.Companion.execute(action);
            }
//		    mGaussBlurThread.post(action);
//		} else if (AspLog.isPrintLog){
//			//下面日志用来调试问题
//		    AspLog.i(TAG, "mGaussBlugThread = null");
//		}
        } else {
//		if (mDrawGaussBg) {
//			//下面日志用来调试问题
//			if (mImageView == null) {
//			    if (AspLog.isPrintLog){
//				AspLog.i(TAG, "mImageView = null");
//			    }
//			} else {
//				if (!mImageView.isDirty()) {
//				    if (AspLog.isPrintLog){
//					AspLog.i(TAG, "!mImageView.isDirty()");
//				    }
//				}
//				if (mGaussBitmap == null) {
//				    if (AspLog.isPrintLog){
//					AspLog.i(TAG, "mGaussBitmap = null");
//				    }
//				}
//			}
//		}
        }
        super.dispatchDraw(canvas);
    }

    private void drawGaussBackground(Canvas canvas, Bitmap mGaussBitmap) {
        if (!mDrawGaussBg || mImageView == null || mGaussBitmap == null) {
            return;
        }
        int saveCount = canvas.save();
        Rect destRect = new Rect();
        destRect.left = 0;
        destRect.top = 0;
        destRect.right = getWidth();
        destRect.bottom = getHeight();
        float cscale = getHeight() / (getWidth() + 0.001f);
        float bscale = mGaussBitmap.getHeight() / (mGaussBitmap.getWidth() + 0.001f);
        Rect src = new Rect();
        if (cscale > bscale) {//偏高
            float w = (float) (mGaussBitmap.getHeight() / (getHeight() / (getWidth() + 0.001)));
            src.top = 0;
            src.bottom = mGaussBitmap.getHeight();
            src.left = ((int) (mGaussBitmap.getWidth() - w)) >> 1;
            src.right = mGaussBitmap.getWidth() - src.left;

        } else {//偏宽
            float h = (float) (mGaussBitmap.getWidth() * (getHeight() / (getWidth() + 0.001)));
            src.top = ((int) (mGaussBitmap.getHeight() - h)) >> 1;
            src.bottom = mGaussBitmap.getHeight() - src.top;
            src.left = 0;
            src.right = mGaussBitmap.getWidth();
        }
        canvas.drawBitmap(mGaussBitmap, src, destRect, null);
        canvas.restoreToCount(saveCount);
    }

    /**
     * 叠加一层蒙版 注意性能
     */
    static Bitmap getGaussBitmap(Bitmap original) {
        Bitmap canvasbitmap = Bitmap.createBitmap(original.getWidth(), (original.getHeight() >> 1), Bitmap.Config.ARGB_8888);
        Bitmap blurbitmap = APPUtils.doBlur(original, 12, true);
        if (blurbitmap == null) {// 如果为空时候，该背景会是透明或者空白
            //下面日志用来调试问题
            return canvasbitmap;
        }
        Canvas canvas = new Canvas(canvasbitmap);
        Rect srcRect = new Rect();
        Paint p = new Paint();
        p.setAlpha(179);
        Rect destRect = new Rect(0, 0, canvasbitmap.getWidth(), canvasbitmap.getHeight());
        srcRect.left = 0;
        srcRect.top = original.getHeight() >> 2;
        srcRect.right = original.getWidth();
        srcRect.bottom = (int) (original.getHeight() * 0.75);
        canvas.drawBitmap(blurbitmap, srcRect, destRect, p);
        if (original != blurbitmap) {
            blurbitmap.recycle();
        }
        p.reset();
        Rect wrect = new Rect(0, (int) ((canvasbitmap.getHeight() * 0.45)), canvasbitmap.getWidth(), canvasbitmap.getHeight());
        LinearGradient lgw = new LinearGradient((wrect.width() >> 1), wrect.top, (wrect.width() >> 1),
                wrect.top + (wrect.height() >> 2), Color.argb(242, 255, 255, 255), Color.argb(253, 255, 255, 255),
                Shader.TileMode.CLAMP);
        p.setShader(lgw);
        canvas.drawRect(wrect, p);
        p.reset();
        Rect rect = new Rect(0, 0, canvasbitmap.getWidth(), wrect.top);
        LinearGradient lg = new LinearGradient((rect.width() >> 1), 0, (rect.width() >> 1), (rect.bottom),
                Color.argb(65, 255, 255, 255), Color.argb(242, 255, 255, 255), Shader.TileMode.CLAMP);
        p.setShader(lg);
        canvas.drawRect(rect, p);
        return canvasbitmap;
    }

    /**
     * 高斯模糊ui线程中的处理器
     *
     * @author lhy
     */
    static class MyHandler extends Handler {
        GaussBgBlurLayout mOwnerView;

        private MyHandler(GaussBgBlurLayout view) {
            super(view.getContext().getMainLooper());
            mOwnerView = view;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_INVALIDATE_GAUSSBG:
                    Bitmap bmp = (Bitmap) msg.obj;
                    Bitmap lastbmp = mOwnerView.mGaussBitmap;
                    if (bmp != null) {
                        if (lastbmp != null) {
                            lastbmp.recycle();
                        }
                        mOwnerView.mGaussBitmap = bmp;
                        mOwnerView.invalidate();
                    }
                    mOwnerView.mGaussBlurAction = null;
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }

    }

    /**
     * 高斯模糊
     *
     * @author lhy
     */
    static class GaussBlurAction implements Runnable {
        private Bitmap mViewImage;
        private Handler mHandler;

        private GaussBlurAction(View view, Handler uihandler) {
            mViewImage = captureSnap(view);
            mHandler = uihandler;
        }

        private Bitmap captureSnap(View view) {
            int vWidth = view.getMeasuredWidth();
            int vHeight = view.getMeasuredHeight();
            if (vWidth <= 0 || vHeight <= 0) {
                return null;
            }
            Bitmap bmp = Bitmap.createBitmap((int) vWidth, vHeight, Bitmap.Config.ARGB_8888);
            Canvas vcanvas = new Canvas(bmp);
            view.draw(vcanvas);
            return bmp;
        }

//	private Bitmap createGauseBitmap(Bitmap img){
//	    if (img == null){
//		return null ;
//	    }
//	    int vWidth = img.getWidth();
//	    int vHeight = img.getHeight();
//	    if (vWidth <= 0 || vHeight <= 0) {
//		return null;
//	    }
//		int imgHeight = (vHeight >>1);
//	    return Bitmap.createBitmap(vWidth, imgHeight, Bitmap.Config.ARGB_8888);
//	}

        @Override
        public void run() {
            if (mViewImage == null) {
                Message msg = mHandler.obtainMessage(MSG_INVALIDATE_GAUSSBG, null);
                msg.sendToTarget();
                return;
            }
            Bitmap gaussimg = getGaussBitmap(mViewImage);
            if (mViewImage != gaussimg) {
                mViewImage.recycle();
            }
            Message msg = mHandler.obtainMessage(MSG_INVALIDATE_GAUSSBG, gaussimg);
            msg.sendToTarget();
        }
    }

//    static class GaussBlugThread extends HandlerThread{
//	private Handler mHandler ;
//	private ConditionVariable mReadyObj ;
//	public GaussBlugThread() {
//	    super("GaussBlurThread");
//	    mReadyObj = new ConditionVariable();
//	}
//
//	@Override
//	protected void onLooperPrepared() {
//	    super.onLooperPrepared();
//	    mHandler = new Handler();
//	    synchronized(mReadyObj){
//		mReadyObj.open();
//	    }
//	}
//	
////	private void post(Message msg){
////	    if (mHandler != null){
////		mHandler.sendMessage(msg);
////	    }
////	}
//	
//	private void post(Runnable action){
//	    if (mHandler == null){
//		synchronized(mReadyObj){
//		    mReadyObj.block();
//		}
//	    }
//	    mHandler.post(action);
//	}
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        View childview = findViewById(R.id.gaussgroud_id);
//        if (childview != null) {
//            ViewGroup.LayoutParams lp = childview.getLayoutParams();
//            if (lp == null) {
//                return;
//            }
//            lp.width = (int) (getMeasuredWidth() * 0.8);
//            childview.setLayoutParams(lp);
//            View card_title = findViewById(R.id.card_title);
//            if (card_title != null) {
//                ViewGroup.LayoutParams titlelp = card_title.getLayoutParams();
//                if (titlelp != null) {
//                    titlelp.width = (int) (getMeasuredWidth() * 0.7);
//                    card_title.setLayoutParams(titlelp);
//                }
//            }
//            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        }
    }
}
