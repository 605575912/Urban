package com.lzx.h.module.image

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.facebook.drawee.view.SimpleDraweeView
import com.lzx.h.module.common.EventObject
import com.lzx.h.module.common.EventObjectType
import com.lzx.h.module.image.databinding.ImageitemLayoutBinding
import com.squareup.lib.BaseApplication
import com.squareup.lib.ImageUtils
import com.squareup.lib.viewfactory.DataBindBaseViewItem
import org.greenrobot.eventbus.EventBus


class ImageFloder constructor(dir: String) : DataBindBaseViewItem() {

    var dir: String = dir

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as DataBindBaseViewItem.ViewHolder
        var itembind = viewHolder.viewDataBinding as ImageitemLayoutBinding
        var drawable = BaseApplication.getApplication().getResources().getDrawable(R.drawable.personage_space_photo_bg);
        ImageUtils.loadImage("file://" + dir, itembind.image as SimpleDraweeView?, drawable)

        itembind.image.setOnClickListener(View.OnClickListener {
            val eventObject = EventObject(EventObjectType.IMAGECHOOSE, dir)
            EventBus.getDefault().post(eventObject)
        })
    }

    override fun createViewID(parent: ViewGroup): Int {
        return R.layout.imageitem_layout
    }


}
