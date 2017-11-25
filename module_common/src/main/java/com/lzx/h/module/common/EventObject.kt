package com.lzx.h.module.common

/**
 * Created by liangzhenxiong on 2017/10/10.
 */

class EventObject<T>(type: EventObjectType) {
    var type = EventObjectType.PoiItem
    var value: T? = null

    init {
        this.type = type
    }
}
