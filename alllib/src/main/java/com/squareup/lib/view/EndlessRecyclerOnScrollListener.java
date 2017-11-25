package com.squareup.lib.view;//package com.squareup.lib.view;
//
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//
///**
// * Created by Administrator on 2017/05/27 0027.
// */
//
//public abstract class EndlessRecyclerOnScrollListener extends
//        RecyclerView.OnScrollListener {
//
//    private int previousTotal = 0;
//    private boolean loading = true;
//    int firstVisibleItem, visibleItemCount, totalItemCount;
//
//    private int currentPage = 1;
//
//    private LinearLayoutManager mLinearLayoutManager;
//
//    public EndlessRecyclerOnScrollListener() {
//    }
//
//    @Override
//    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//        super.onScrolled(recyclerView, dx, dy);
//        mLinearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//        visibleItemCount = recyclerView.getChildCount();
//        totalItemCount = mLinearLayoutManager.getItemCount();
//        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
//
//        if (loading) {
//            if (totalItemCount > previousTotal) {
//                loading = false;
//                previousTotal = totalItemCount;
//            }
//        }
//        if (!loading
//                && (totalItemCount - visibleItemCount) <= firstVisibleItem) {
//            currentPage++;
//            onLoadMore(currentPage);
//            loading = true;
//        }
//    }
//
//    public abstract void onLoadMore(int currentPage);
//}
