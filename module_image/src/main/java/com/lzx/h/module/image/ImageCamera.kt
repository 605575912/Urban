package com.lzx.h.module.image

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.facebook.drawee.view.SimpleDraweeView
import com.lzx.h.module.image.databinding.CameraLayoutBinding
import com.lzx.h.module.image.databinding.ImageitemLayoutBinding
import com.squareup.lib.BaseApplication
import com.squareup.lib.ImageUtils
import com.squareup.lib.viewfactory.DataBindBaseViewItem


class ImageCamera : DataBindBaseViewItem() {


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as DataBindBaseViewItem.ViewHolder
//        var itembind = viewHolder.viewDataBinding as CameraLayoutBinding
//        var drawable = BaseApplication.getApplication().getResources().getDrawable(R.drawable.item_imag_camera);
//
//        ImageUtils.loadImage("", itembind.image as SimpleDraweeView?, drawable)
    }

    override fun createViewID(parent: ViewGroup): Int {
        return R.layout.camera_layout
    }


}
