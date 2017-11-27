package com.lzx.h.module.main;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.lib.viewfactory.DataBindBaseViewItem;


public class UserItemView extends DataBindBaseViewItem implements View.OnClickListener {
    UserData itemData;
    Activity activity;

    public UserItemView(Activity activity, UserData itemData) {
        this.itemData = itemData;
        this.activity = activity;
    }

    public void setItemData(UserData itemData) {
        this.itemData = itemData;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        if (viewHolder.getViewDataBinding() != null) {
            viewHolder.getViewDataBinding().setVariable(BR.userdata, itemData);
//            viewHolder.getViewDataBinding().setVariable(BR.onclick, this);
        }
    }


    @Override
    public int createViewID(ViewGroup parent) {
        return R.layout.useritem_layout;
    }

    @Override
    public void onClick(View v) {
//        Intent intent = LauncherUrl.launcherActivity(itemData.getJumpcontent());
//        if (intent != null && activity != null) {
//            activity.startActivity(intent);
//        }
    }
}
