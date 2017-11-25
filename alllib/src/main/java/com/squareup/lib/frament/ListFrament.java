package com.squareup.lib.frament;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.squareup.lib.R;
import com.squareup.lib.WrapContentLinearLayoutManager;
import com.squareup.lib.annotation.KeepNotProguard;
import com.squareup.lib.viewfactory.BaseViewItem;
import com.squareup.lib.viewfactory.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by H on 2017/9/1.
 */

public class ListFrament extends BaseFrament {
    protected RecyclerView recyclerView;
    protected List<BaseViewItem> list;
    protected RecyclerViewAdapter adapter;
    protected TwinklingRefreshLayout twinklingRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        twinklingRefreshLayout = (TwinklingRefreshLayout) contentView.findViewById(R.id.RefreshLayoutid);
        if (twinklingRefreshLayout != null) {
            twinklingRefreshLayout.setEnableRefresh(true);
            twinklingRefreshLayout.setEnableLoadmore(false);
            twinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
                @Override
                public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                    ListFrament.this.onRefresh();
                }

                @Override
                public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                    ListFrament.this.onLoadMore();
                }
            });
        }
        recyclerView = (RecyclerView) contentView.findViewById(R.id.RecyclerViewid);
        if (recyclerView != null) {
            WrapContentLinearLayoutManager wrapContentLinearLayoutManager = new WrapContentLinearLayoutManager(getActivity());
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(wrapContentLinearLayoutManager);
            list = new ArrayList<BaseViewItem>();
            adapter = new RecyclerViewAdapter(getActivity(), list);
            recyclerView.setAdapter(adapter);
        }
        return contentView;
    }
    @KeepNotProguard
    protected void finishRefreshing() {
        if (recyclerView != null) {
            twinklingRefreshLayout.finishRefreshing();
        }
    }
    @KeepNotProguard
    protected final void notifyDataSetChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

    }
}
