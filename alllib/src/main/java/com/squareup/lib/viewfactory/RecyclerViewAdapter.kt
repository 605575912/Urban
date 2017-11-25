package com.squareup.lib.viewfactory

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

import com.squareup.lib.annotation.KeepNotProguard

/**
 * Created by liangzhenxiong on 2017/4/6 0006.
 * 主适配器
 */
@KeepNotProguard
class RecyclerViewAdapter
//    private LongSparseArray sparseArray = new LongSparseArray();

/**
 * Same as QuickAdapter#QuickAdapter(Context,int) but with
 * some initialization data.
 *
 * @param data A new list is created out of this one to avoid mutable list
 */
(private val mContext: Context, private val mdata: List<BaseViewItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        for (viewItem in mdata) {
            if (viewItem.viewType == viewType) {
                viewHolder = viewItem.createViewHolder(parent)
                if (viewHolder == null) {
                    val contentview = viewItem.createView(parent)
                    if (contentview != null) {
                        viewHolder = RecyclerViewHolder(contentview)
                        break
                    }
                } else {
                    break
                }
            }
        }
        if (viewHolder == null) {
            viewHolder = RecyclerViewHolder(View(mContext))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        mdata[position].onBindViewHolder(holder, position)
    }


    override fun getItemViewType(position: Int): Int {
        return mdata[position].viewType
    }

    override fun getItemCount(): Int {
        return mdata.size
    }
}
