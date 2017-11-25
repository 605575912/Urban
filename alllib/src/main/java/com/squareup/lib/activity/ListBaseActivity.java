//package com.squareup.lib.activity;
//
//import android.os.Bundle;
//import android.support.v7.widget.RecyclerView;
//
//import com.squareup.lib.R;
//import com.squareup.lib.WrapContentLinearLayoutManager;
//import com.squareup.lib.viewfactory.BaseViewItem;
//import com.squareup.lib.viewfactory.RecyclerViewAdapter;
//
//import java.util.ArrayList;
//
///**
// * Created by H on 2017/9/1.
// */
//
//public abstract class ListBaseActivity extends BaseActivity {
//    protected ArrayList<BaseViewItem> list;
//    protected RecyclerView recyclerView;
//    protected RecyclerViewAdapter adapter;
//
//
//    @Override
//    public int setFromLayoutID() {
//        return R.layout.layout;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        recyclerView = (RecyclerView) viewDataBinding.getRoot().findViewById(R.id.RecyclerViewid);
//        list = new ArrayList<BaseViewItem>();
//        adapter = new RecyclerViewAdapter(getActivity(), list);
//        if (recyclerView != null) {
//            WrapContentLinearLayoutManager wrapContentLinearLayoutManager = new WrapContentLinearLayoutManager(getActivity());
//            recyclerView.setHasFixedSize(true);
//            recyclerView.setLayoutManager(wrapContentLinearLayoutManager);
//            recyclerView.setAdapter(adapter);
//
//        }
//    }
//
//    protected void notifyDataSetChanged() {
//        if (adapter != null) {
//            adapter.notifyDataSetChanged();
//        }
//    }
//}
