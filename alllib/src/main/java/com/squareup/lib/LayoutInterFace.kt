package com.squareup.lib

import android.support.annotation.LayoutRes

import com.squareup.lib.utils.IProguard

/**
 * Created by H on 2017/9/1.
 */

interface LayoutInterFace : IProguard.ProtectClassAndMembers {
    @LayoutRes
    fun setFromLayoutID(): Int
}
