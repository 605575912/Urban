package com.squareup.lib.viewfactory

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.squareup.lib.annotation.KeepNotProguard

/**
 * Created by liangzhenxiong on 2017/4/6 0006.
 * 视图Item
 */
@KeepNotProguard
open class DataBindBaseViewItem : BaseViewItem {

    override val viewType: Int
        get() = javaClass.hashCode()

    override fun createView(parent: ViewGroup): View {
        return View(parent.context)
    }

    open fun createViewID(parent: ViewGroup): Int {
        return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {}

    override fun createViewHolder(parent: ViewGroup): RecyclerViewHolder {
        //        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.douitem_layout, parent, false);
        val layoutid = createViewID(parent)
        var view: View? = null
        if (layoutid != 0) {
            try {
                view = LayoutInflater.from(parent.context).inflate(layoutid, parent, false)
            } catch (e: Exception) {

            }

        }
        if (view == null) {
            view = createView(parent)
        }
        val viewDataBinding = DataBindingUtil.bind<ViewDataBinding>(view)
        return ViewHolder(viewDataBinding)
    }


    inner class ViewHolder (viewDataBinding: ViewDataBinding) : RecyclerViewHolder(viewDataBinding.root) {
        var viewDataBinding: ViewDataBinding
            internal set

        init {
            this.viewDataBinding = viewDataBinding
        }
    }

}
