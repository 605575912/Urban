package com.lzx.h.module.common

/**
 * Created by Administrator on 2017/05/25 0025.
 */

enum class EventObjectType constructor(

        private val type: Int) {
    IMAGECHOOSE(0x001), //选择照片
    ProjectModel(0x008)
}
