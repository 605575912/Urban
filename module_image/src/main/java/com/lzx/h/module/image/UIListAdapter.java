package com.lzx.h.module.image;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.squareup.lib.viewfactory.BaseViewItem;
import com.squareup.lib.viewfactory.DataBindBaseViewItem;

import java.util.HashMap;
import java.util.List;

public class UIListAdapter extends BaseAdapter {
    List<DataBindBaseViewItem> datas;
    HashMap<String, Integer> typehash;

    public UIListAdapter(List<DataBindBaseViewItem> datas) {
        this.datas = datas;
        typehash = new HashMap<String, Integer>();
    }


    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int arg0) {
        return datas.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return datas.get(arg0).hashCode();
    }

    int sumtype = 0;

    @Override
    public int getItemViewType(int position) {
        BaseViewItem viewItemData = datas.get(position);
        int type;
        String classname = viewItemData.getClass().getName();
        if (typehash.containsKey(classname)) {
            type = typehash.get(classname);
        } else {
            typehash.put(classname, sumtype);
            type = sumtype;
            sumtype = sumtype + 1;
        }
        return type;
    }

    @Override
    public int getViewTypeCount() {
        return 10;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {
        BaseViewItem viewItemData = datas.get(position);
        DataBindBaseViewItem.ViewHolder vHolder = null;
        int type = getItemViewType(position);
        if (convertView == null) {
            vHolder = (DataBindBaseViewItem.ViewHolder) viewItemData.createViewHolder(arg2);
            convertView = vHolder.getViewDataBinding().getRoot();
            convertView.setTag(vHolder);
        } else {
            vHolder = (DataBindBaseViewItem.ViewHolder) convertView.getTag();
            if (type != vHolder.getItemViewType()) {
                vHolder = (DataBindBaseViewItem.ViewHolder) viewItemData.createViewHolder(arg2);
                convertView = vHolder.getViewDataBinding().getRoot();
                convertView.setTag(vHolder);
            }
        }

        viewItemData.onBindViewHolder(vHolder, position);
        return convertView;
    }


}
