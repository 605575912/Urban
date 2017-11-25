package com.squareup.lib;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.squareup.lib.utils.IProguard;

/**
 * Created by lzx on 2017/2/16.
 */

public class WrapContentLinearLayoutManager extends LinearLayoutManager implements IProguard.ProtectClassAndMembers {

    //默认垂直方向
    public WrapContentLinearLayoutManager(Context context) {
        super(context, VERTICAL, false);
    }

    //方向
    public WrapContentLinearLayoutManager(Context context, int orientation){
        super(context, orientation, false);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
