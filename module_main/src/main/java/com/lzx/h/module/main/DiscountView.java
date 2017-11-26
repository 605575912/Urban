//package com.lzx.h.module.main;
//
//import android.graphics.Color;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.GridView;
//import android.widget.TextView;
//
//import com.facebook.drawee.view.SimpleDraweeView;
//import com.squareup.lib.ImageUtils;
//import com.squareup.lib.viewfactory.BaseViewItem;
//import com.squareup.lib.viewfactory.RecyclerViewHolder;
//
//import java.util.List;
//
///**
// * Created by Administrator on 2017/06/13 0013.
// */
//
//public class DiscountView implements BaseViewItem {
//    List<DiscountData> discountdatas;
//
//    public DiscountView(List<DiscountData> discountdatas) {
//        this.discountdatas = discountdatas;
//    }
//
//    @Override
//    public int getViewType() {
//        return getClass().hashCode();
//    }
//
//    @Override
//    public View createView(ViewGroup parent) {
//        return null;
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//
//    }
//
//    @Override
//    public RecyclerViewHolder createViewHolder(ViewGroup parent) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.discount_layout, parent, false);
//        return new ViewHolder(view);
//    }
//
//    private class ViewHolder extends RecyclerViewHolder {
//
//        private ViewHolder(View view) {
//            super(view);
//            GridView grid = (GridView) view.findViewById(R.id.grid);
//            ListGridAdapter adapter = new ListGridAdapter(discountdatas);
//            grid.setNumColumns(4);
//            grid.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
//
//        }
//
//    }
//
//    class ListGridAdapter extends BaseAdapter {
//        List<DiscountData> list;
//
//        public ListGridAdapter(List<DiscountData> list) {
//            this.list = list;
//        }
//
//        @Override
//        public int getCount() {
//            return list.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return list.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            ViewHolder holder;
//            if (convertView == null) {
//                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.discount_item, parent, false);
//                holder = new ViewHolder();
//                holder.image = (SimpleDraweeView) convertView.findViewById(R.id.image);
//                holder.tx_ = (TextView) convertView.findViewById(R.id.tx_);
//                holder.tx_content = (TextView) convertView.findViewById(R.id.tx_content);
//                convertView.setTag(holder); //绑定ViewHolder对象
//            } else {
//                holder = (ViewHolder) convertView.getTag(); //取出ViewHolder对象
//            }
//            DiscountData discountData = list.get(position);
//            holder.tx_.setText(discountData.getTitle());
//            holder.tx_content.setText(discountData.getContent());
//            try {
//                holder.tx_content.setTextColor(Color.parseColor(discountData.getColor()));
//            } catch (Exception e) {
//
//            }
//
//            ImageUtils.loadImage(parent.getContext(), discountData.getImg(), holder.image, R.drawable.placeholder_error);
//            return convertView;
//        }
//
//        class ViewHolder {
//            SimpleDraweeView image;
//            TextView tx_;
//            TextView tx_content;
//        }
//    }
//}
