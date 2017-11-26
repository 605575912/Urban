package com.lzx.h.module.image

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.lzx.h.module.common.EventObject
import com.lzx.h.module.common.EventObjectType
import com.lzx.h.module.image.databinding.ImagegridLayoutBinding
import com.squareup.lib.ThreadManager
import com.squareup.lib.activity.BaseActivity
import com.squareup.lib.viewfactory.DataBindBaseViewItem
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by liangzhenxiong on 2017/11/26.
 */

class ChooseActivity : BaseActivity() {


    companion object {
        fun start(activity: Activity) {
            var intent = Intent(activity, ChooseActivity::class.java)
            activity.startActivity(intent)

        }
    }

    override fun setFromLayoutID(): Int {
        return R.layout.imagegrid_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var choosebind = viewDataBinding as ImagegridLayoutBinding
        var listdata = ArrayList<DataBindBaseViewItem>()
        var adapter = UIListAdapter(listdata)
        choosebind.gridview.adapter = adapter
        var listpath = ImageMap.getInstance().listdata
        if (listpath.isEmpty()) {
            ThreadManager.execute(Runnable {
                listpath = ImageMap.getInstance().imageCursorList
                listdata.add(ImageCamera())
                for (path in listpath) {
                    listdata.add(ImageFloder(path))
                }
                runOnUiThread({
                    adapter.notifyDataSetChanged()
                })
            })

        } else {

            listdata.add(ImageCamera())
            for (path in listpath) {
                listdata.add(ImageFloder(path))
            }
            adapter.notifyDataSetChanged()
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    internal fun EventObject(eventObject: EventObject<*>) {
        if (eventObject.type == EventObjectType.IMAGECHOOSE) {
            finish()
        }
    }
}
