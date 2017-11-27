package com.lzx.h.module.main;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.lib.viewfactory.DataBindBaseViewItem;

/**
 * Created by Administrator on 2017/06/09 0009.
 */

public class ItemView extends DataBindBaseViewItem implements View.OnClickListener {
    ItemData itemData;
    Activity activity;

    public ItemView(Activity activity, ItemData itemData) {
        this.itemData = itemData;
        this.activity = activity;
    }

    public void setItemData(ItemData itemData) {
        this.itemData = itemData;
    }





    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (viewHolder.getViewDataBinding() != null) {
            viewHolder.getViewDataBinding().setVariable(BR.itemdata, itemData);
            viewHolder.getViewDataBinding().setVariable(BR.onclick, this);
        }
    }


    @Override
    public int createViewID(ViewGroup parent) {
        return R.layout.item_layout;
    }

    @Override
    public void onClick(View v) {
//        Intent intent = LauncherUrl.launcherActivity(itemData.getJumpcontent());
//        if (intent != null && activity != null) {
//            activity.startActivity(intent);
//        }
    }
}
