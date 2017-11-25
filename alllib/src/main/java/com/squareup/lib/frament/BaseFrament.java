package com.squareup.lib.frament;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.lib.EventMainObject;
import com.squareup.lib.EventThreadObject;
import com.squareup.lib.annotation.KeepNotProguard;


/**
 * Created by Administrator on 2017/06/09 0009.
 */
@KeepNotProguard
public abstract class BaseFrament extends Fragment {
    @KeepNotProguard
    protected View contentView;

    @KeepNotProguard
    protected void onRefresh() {
    }

    @KeepNotProguard
    protected void onLoadMore() {
    }

    public
    @LayoutRes
    int getFromLayoutID() {
        return 0;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView = inflater.inflate(getFromLayoutID(), container, false);
        return contentView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @KeepNotProguard
    public void onEventMain(EventMainObject event) {
    }
    @KeepNotProguard
    public void onEventThread(EventThreadObject event) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
