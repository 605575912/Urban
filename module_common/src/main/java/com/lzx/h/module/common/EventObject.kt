package com.lzx.h.module.common

/**
 * Created by liangzhenxiong on 2017/10/10.
 */

class EventObject<T> constructor(type: EventObjectType, t: T) {
    var type = type
    var value: T? = t
//
//     {
//        this.type = type
//    }
}
