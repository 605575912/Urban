//package com.lzx.h.module.main;
//
//import android.app.Activity;
//import android.support.v7.widget.RecyclerView;
//import android.view.ViewGroup;
//
//import com.squareup.lib.viewfactory.DataBindBaseViewItem;
//
//import org.jetbrains.annotations.NotNull;
//
///**
// * Created by Administrator on 2017/05/27 0027.
// */
//
//public class DoubleItemView extends DataBindBaseViewItem {
//    ItemData itemData;
//    Activity activity;
//
//    @Override
//    public int createViewID(@NotNull ViewGroup parent) {
//        return R.layout.douitem_layout;
//    }
//
//    public DoubleItemView(Activity activity) {
//        this.activity = activity;
//    }
//
//    public void setItemData(ItemData itemData) {
//        this.itemData = itemData;
//    }
//
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        ViewHolder viewHolder = (ViewHolder) holder;
////        if (viewHolder.getActivityMainBinding() != null) {
////            viewHolder.getActivityMainBinding().setItemdata(itemData);
//////            viewHolder.getActivityMainBinding().setDoubleonclick(new DoubleOnClick(activity, itemData));
////        }
//    }
//
//
//}
