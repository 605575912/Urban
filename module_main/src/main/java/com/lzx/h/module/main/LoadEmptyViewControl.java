package com.lzx.h.module.main;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by lzx on 2017/04/25 0025.
 * 兼顾下拉刷新加载界面
 */

public class LoadEmptyViewControl {
    ImageView progress;
    TextView load_text;
    Context context;
    View load_layout;

    public LoadEmptyViewControl(Context context) {
        this.context = context;
    }

    /**
     * 必须先加载UI
     */
    public void addLoadView(ViewGroup viewGroup) {
        load_layout = viewGroup.findViewById(R.id.load_layout);
        if (load_layout == null) {
            load_layout = LayoutInflater.from(context).inflate(R.layout.load_layout, viewGroup, false);
            viewGroup.addView(load_layout, 0);
        }
        initLoadLayout();
    }

    /**
     * 初始化参数
     */
    private void initLoadLayout() {
        progress = (ImageView) load_layout.findViewById(R.id.progress_icon);
        load_text = (TextView) load_layout.findViewById(R.id.load_text);
        load_layout.setVisibility(View.VISIBLE);
        ((AnimationDrawable) progress.getDrawable()).start();
    }

    public void loadcomplete() {
        load_layout.setVisibility(View.GONE);
        progress.clearAnimation();
    }

    public void loadError(String error) {
        load_layout.setVisibility(View.VISIBLE);
        load_text.setVisibility(View.VISIBLE);
        load_layout.setVisibility(View.VISIBLE);
        load_text.setText(error);
        progress.clearAnimation();
        progress.setImageResource(R.drawable.wushuju);
    }

    public void loadError() {
        loadError(context.getResources().getString(R.string.str_nodata));
    }
}
