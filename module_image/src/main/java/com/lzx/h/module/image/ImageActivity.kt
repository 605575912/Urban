package com.lzx.h.module.image

import android.Manifest
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.facebook.drawee.view.SimpleDraweeView
import com.lzx.h.module.common.EventObject
import com.lzx.h.module.common.EventObjectType
import com.lzx.h.module.image.databinding.ImageLayoutBinding
import com.squareup.lib.ImageUtils
import com.squareup.lib.activity.BaseActivity
import com.squareup.lib.activity.PermissionHandler
import com.squareup.lib.activity.PermissionsGrantActivity
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by liangzhenxiong on 2017/11/22.
 */
@Route(path = "/image/choose")
class ImageActivity : BaseActivity() {
    var loginview: ImageLayoutBinding? = null
    var presenter: ImagePresenter? = null
//    companion object {
//        open fun StartActivity(activity: Activity) {
//            var intent = Intent(activity, (LoginActivity::class.java))
//            activity.startActivity(intent)
//        }
//    }

    override fun setFromLayoutID(): Int {
        return R.layout.image_layout
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginview = viewDataBinding as ImageLayoutBinding

        PermissionsGrantActivity.grantPermissions(activity, arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), object : PermissionHandler {
            override fun onPermissionsResult(grantedpermissions: Array<String>?, denied_permissions: Array<String>?) {
                if (denied_permissions == null) {
                    presenter = ImagePresenter(activity)
                    loginview!!.presenter = presenter
                }
            }
        })

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    internal fun EventObject(eventObject: EventObject<*>) {
        if (eventObject.type == EventObjectType.IMAGECHOOSE) {
            ImageUtils.loadImage("file://" + eventObject.value, loginview!!.setimage as SimpleDraweeView?)
            presenter?.dir = eventObject.value as String?
        }
    }

}