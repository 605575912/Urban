package com.squareup.lib

import com.squareup.lib.annotation.KeepNotProguard
import java.lang.ref.WeakReference
import java.util.concurrent.Executors

/**
 * Created by Administrator on 2017/05/25 0025.
 */
@KeepNotProguard
class ThreadManager {
    companion object {
        internal var fixedThreadPool = Executors.newFixedThreadPool(5)

        @KeepNotProguard
        fun execute(runnable: Runnable) {
            fixedThreadPool.execute(runnable)
        }
        @KeepNotProguard
        fun submit(runnable: Runnable) {
            val s = WeakReference(runnable)
            fixedThreadPool.submit(s.get())
        }
    }
}
