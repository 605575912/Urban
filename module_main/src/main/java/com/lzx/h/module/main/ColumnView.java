//package com.lzx.h.module.main;
//
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
//public class ColumnView implements BaseViewItem {
//    List<ColumnData> columnitems;
//
//    public ColumnView(List<ColumnData> columnitems) {
//        this.columnitems = columnitems;
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
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.column_layout, parent, false);
//        return new ViewHolder(view);
//    }
//
//    private class ViewHolder extends RecyclerViewHolder {
//
//        private ViewHolder(View view) {
//            super(view);
//            GridView grid = (GridView) view.findViewById(R.id.grid);
//            ListGridAdapter adapter = new ListGridAdapter(columnitems);
//            grid.setNumColumns(5);
//            grid.setAdapter(adapter);
//            adapter.notifyDataSetChanged();
//
//        }
//
//    }
//
//    class ListGridAdapter extends BaseAdapter {
//        List<ColumnData> list;
//
//        public ListGridAdapter(List<ColumnData> list) {
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
//                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.column_item, parent, false);
//                holder = new ViewHolder();
//                holder.image = (SimpleDraweeView) convertView.findViewById(R.id.image);
//                holder.tx_ = (TextView) convertView.findViewById(R.id.tx_);
//                convertView.setTag(holder); //绑定ViewHolder对象
//            } else {
//                holder = (ViewHolder) convertView.getTag(); //取出ViewHolder对象
//            }
//            ColumnData columnData = list.get(position);
//            holder.tx_.setText(columnData.getTitle());
//            ImageUtils.loadImage(parent.getContext(), columnData.getImg(), holder.image, R.drawable.placeholder_error);
//            return convertView;
//        }
//
//        class ViewHolder {
//            SimpleDraweeView image;
//            TextView tx_;
//        }
//    }
//}
