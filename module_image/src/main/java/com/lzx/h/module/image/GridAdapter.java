//package com.lzx.h.module.image;
//
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//
//import com.squareup.lib.BaseApplication;
//import com.squareup.lib.viewfactory.BaseViewItem;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by liangzhenxiong on 2017/11/26.
// */
//
//public class GridAdapter extends BaseAdapter {
//
//    List<BaseViewItem> baselist = new ArrayList<>();
//
//    public GridAdapter(List<BaseViewItem> list) {
//        if (list == null) {
//            return;
//        }
//        baselist.addAll(list);
//    }
//
//    @Override
//    public int getCount() {
//        return baselist.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return baselist.get(i);
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return baselist.get(i).hashCode();
//    }
//
////    @Override
////    public int getViewTypeCount() {
////        return baselist.size();
////    }
////
////    @Override
////    public int getItemViewType(int position) {
////        return baselist.get(position).getViewType();
////    }
//
//    @Override
//    public View getView(int i, View convertView, ViewGroup viewGroup) {
//
////        int type = baselist.get(i).getViewType();
//
//
//        BaseViewItem viewItemData = baselist.get(i);
////        RecyclerView.ViewHolder vHolder = null;
////        int type = getItemViewType(position);
////        if (convertView == null) {
//            convertView = viewItemData.createView(viewGroup);
////            vHolder = viewItemData.createViewHolder(arg2);
////            convertView.setTag(vHolder);
////        } else {
////            vHolder = (RecyclerView.ViewHolder) convertView.getTag();
////        }
////        viewItemData.onBindViewHolder(vHolder, position);
//
////        ViewHolder holder;
////        if (convertView == null) {
////            holder = new ViewHolder();
////            convertView = LayoutInflater.from(BaseApplication.getApplication()).inflate(R.layout.imageitem_layout, viewGroup, false);
////            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
////            convertView.setTag(holder);
////        } else {
////            holder = (ViewHolder) convertView.getTag();
////            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
////        }
//
//        return convertView;
//    }
//
//    class ViewHolder {
//        ImageView imageView;
//
//    }
//
//}
