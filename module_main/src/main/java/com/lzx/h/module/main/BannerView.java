package com.lzx.h.module.main;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.squareup.lib.viewfactory.DataBindBaseViewItem;

import java.util.List;

/**
 * Created by Administrator on 2017/06/09 0009.
 */

public class BannerView extends DataBindBaseViewItem {
    private List<BannerModel> banners;


    public BannerView(List<BannerModel> banners) {
        this.banners = banners;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (viewHolder.getViewDataBinding() != null) {
            viewHolder.getViewDataBinding().setVariable(BR.lists, banners);
        }
    }

    @Override
    public int createViewID(ViewGroup parent) {
        return R.layout.banner_layout;
    }
}
