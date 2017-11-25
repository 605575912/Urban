package com.lzx.h.module.common;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.squareup.lib.viewfactory.DataBindBaseViewItem;

/**
 * Created by liangzhenxiong on 2017/9/30.
 */

public class LineItem extends DataBindBaseViewItem {
    private String color = "#d2d1d1";

    public LineItem() {

    }

    public LineItem(String color) {
        if (TextUtils.isEmpty(color)) {
            return;
        } else if (!color.startsWith("#") || color.length() < 7) {
            return;
        }
        this.color = color;
    }

    @Override
    public int createViewID(ViewGroup parent) {
        return R.layout.lineitem_layout;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.getViewDataBinding().setVariable(BR.color, color);

    }
}
