package com.squareup.lib

import com.squareup.lib.annotation.KeepNotProguard

/**
 * Created by Administrator on 2017/05/25 0025.
 */
@KeepNotProguard
class EventMainObject<T> {
    @KeepNotProguard
    var data: T? = null
    @KeepNotProguard
    var command: String? = null
        get() {
            if (field == null) {
                this.command = ""
            }
            return field
        }
    @KeepNotProguard
    var isSuccess: Boolean = false

    @KeepNotProguard
    private constructor() {
    }

    @KeepNotProguard
    constructor(data: T) {
        this.data = data
    }
}
