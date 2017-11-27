package com.lzx.h.module.main;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.squareup.lib.viewfactory.BaseViewItem;
import com.squareup.lib.viewfactory.RecyclerViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/05/27 0027.
 */

public class ChangedItemView implements BaseViewItem {
    List<ItemData> items;
    Activity activity;

    public ChangedItemView(Activity activity, List<ItemData> items) {
        this.activity = activity;
        this.items = items;
        if (items == null) {
            this.items = new ArrayList<ItemData>();
        }

    }

    @Override
    public int getViewType() {
        return getClass().getName().hashCode() + items.size();
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        for (int i = 0; i < items.size(); i++) {
            ItemView itemView = viewHolder.getItemViews().get(i);
            itemView.setItemData(items.get(i));
            itemView.onBindViewHolder(viewHolder.recyclerViewHolders.get(i), position);
        }
    }

    @Override
    public RecyclerViewHolder createViewHolder(ViewGroup parent) {
        LinearLayout linearLayout = new LinearLayout(parent.getContext());
        linearLayout.setBackgroundColor(Color.GREEN);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(params);
        return new ViewHolder(linearLayout);
    }

    private class ViewHolder extends RecyclerViewHolder {
        List<ItemView> itemViews;
        List<RecyclerViewHolder> recyclerViewHolders;

        public List<ItemView> getItemViews() {
            return itemViews;
        }


        private ViewHolder(LinearLayout linearLayout) {
            super(linearLayout);
            itemViews = new ArrayList<ItemView>();
            recyclerViewHolders = new ArrayList<RecyclerViewHolder>();
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            for (ItemData itemData : items) {
                ItemView itemView = new ItemView(activity, itemData);
                itemViews.add(itemView);
                RecyclerViewHolder recyclerViewHolder = itemView.createViewHolder(linearLayout);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) recyclerViewHolder.itemView.getLayoutParams();
                params.weight = 1;
                linearLayout.addView(recyclerViewHolder.itemView.getRootView(), params);
                recyclerViewHolders.add(recyclerViewHolder);
            }


        }
    }
}
