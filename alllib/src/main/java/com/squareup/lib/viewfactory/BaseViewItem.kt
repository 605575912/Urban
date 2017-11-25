package com.squareup.lib.viewfactory

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

import com.squareup.lib.annotation.KeepNotProguard

/**
 * Created by liangzhenxiong on 2017/4/6 0006.
 * 视图Item
 */
@KeepNotProguard
interface BaseViewItem {
    val viewType: Int

    fun createView(parent: ViewGroup): View

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)

    fun createViewHolder(parent: ViewGroup): RecyclerViewHolder
}
