package com.lzx.h.module.main;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 跟GaussBgBlurLayout 搭配使用的图片控件，目的是用于同步刷新高斯模糊背景
 * @author lhy
 *
 */
public class GaussImageView extends SimpleDraweeView {

    public GaussImageView(Context context, AttributeSet attrs, int defStyle) {
	super(context, attrs, defStyle);
    }

    public GaussImageView(Context context, AttributeSet attrs) {
	super(context, attrs);
    }

    public GaussImageView(Context context) {
	super(context);
    }

    @TargetApi(21)
    @Override
    public void invalidateOutline() {
	super.invalidateOutline();
	ViewParent parent = getParent();
	if (parent != null && parent instanceof View){
	    ((View)parent).invalidate();
	}
    }

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	@Override
	public void invalidate(Rect dirty) {
	super.invalidate(dirty);
	ViewParent parent = getParent();
	if (parent != null && parent instanceof View){
	    ((View)parent).invalidate();
	}
    }

    @Override
    public void invalidate(int l, int t, int r, int b) {
	super.invalidate(l, t, r, b);
	ViewParent parent = getParent();
	if (parent != null && parent instanceof View){
	    ((View)parent).invalidate();
	}
    }

    @Override
    public void invalidate() {
	super.invalidate();
	ViewParent parent = getParent();
	if (parent != null && parent instanceof View){
	    ((View)parent).invalidate();
	}
    }

}
