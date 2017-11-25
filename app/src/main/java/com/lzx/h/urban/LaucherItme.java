//package com.lzx.h.urban;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.squareup.lib.BaseApplication;
//import com.squareup.lib.utils.AppLibUtils;
//import com.squareup.lib.viewfactory.DataBindBaseViewItem;
//
//import org.jetbrains.annotations.NotNull;
//
///**
// * Created by liangzhenxiong on 2017/11/20.
// */
//
//public class LaucherItme extends DataBindBaseViewItem {
//    public LaucherItme() {
//    }
//
//    @Override
//    public int createViewID(ViewGroup parent) {
//        return R.layout.aboutapp_layout;
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        ViewHolder viewHolder = (ViewHolder) holder;
//        viewHolder.getViewDataBinding().setVariable(BR.versionname, AppLibUtils.Companion.getAppVersionName(BaseApplication.getApplication()));
//        viewHolder.getViewDataBinding().getRoot().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
//    }
//}
