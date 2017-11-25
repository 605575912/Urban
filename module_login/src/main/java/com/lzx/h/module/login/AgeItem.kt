package com.lzx.h.module.login

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.lzx.h.module.common.databinding.ItemAgeBinding
import com.squareup.lib.viewfactory.DataBindBaseViewItem

/**
 * Created by liangzhenxiong on 2017/11/25.
 */
class AgeItem constructor(color: Int, string: String) : DataBindBaseViewItem() {
    var string = string
    var color = color

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as DataBindBaseViewItem.ViewHolder
        var itembind = viewHolder.viewDataBinding as ItemAgeBinding
        itembind.tvAge.setText(string)
        itembind.tvAge.setBackgroundColor(color)

    }


    override fun createViewID(parent: ViewGroup): Int {
        return R.layout.item_age
    }
}