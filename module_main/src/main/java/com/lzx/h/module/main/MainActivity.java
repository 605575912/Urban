package com.lzx.h.module.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.lib.BaseApplication;
import com.squareup.lib.EventMainObject;
import com.squareup.lib.EventThreadObject;
import com.squareup.lib.ImageUtils;
import com.squareup.lib.activity.TabBaseActivity;
import com.squareup.lib.frament.BaseFrament;
import com.squareup.lib.utils.AppLibUtils;
import com.squareup.lib.utils.LogUtil;
import com.squareup.lib.utils.ToastUtils;

@Route(path = "/main/mainactivity")
public class MainActivity extends TabBaseActivity implements View.OnClickListener {

    LinearLayout tabs_layout;
    TabsCache tabsCache;
    ViewPager viewPager;
    LoadEmptyViewControl loadEmptyViewControl;

    @Override
    public int setFromLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.e("onCreate");
        tabs_layout = (LinearLayout) findViewById(R.id.tabs_layout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(tabAdapter);
        FrameLayout frameLayout = (FrameLayout) viewDataBinding.getRoot().findViewById(R.id.container);
        loadEmptyViewControl = new LoadEmptyViewControl(getActivity());
        loadEmptyViewControl.addLoadView(frameLayout);
        tabsCache = new TabsCache();
        tabsCache.getCacheData();
    }

    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                long lasttime = (long) msg.obj;
                Message msg0 = handler.obtainMessage(1);
                msg0.obj = --lasttime;
                handler.sendMessageDelayed(msg0, 1000);
            }
        }
    };


    boolean transtatus;

    private LinearLayout addTabs(TabModel tabModel) {
        LinearLayout indexLayout = null;
        for (TabsBean tabsBean : tabModel.getTabs()) {
            LinearLayout linearLayout = new LinearLayout(getActivity());
            SimpleDraweeView imageView = new SimpleDraweeView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.weight = 1;
            indexLayout = tabsBean.getIndex() == 1 ? linearLayout : indexLayout;
            linearLayout.setTag(R.id.maintabs_id, tabsBean);
            linearLayout.setOnClickListener(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams imgparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imgparams.weight = 1;
            imgparams.gravity = Gravity.CENTER_HORIZONTAL;
            linearLayout.addView(imageView, imgparams);
            TextView textView = new TextView(getActivity());
            textView.setText(tabsBean.getTitle());
            textView.setTextSize(11);
            textView.setTextColor(Color.BLACK);
            LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textparams.gravity = Gravity.CENTER_HORIZONTAL;
            linearLayout.addView(textView, textparams);
            tabs_layout.addView(linearLayout, params);
            TabFragment tabFragment = new TabFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("TabsBean", tabsBean);
            tabFragment.setArguments(bundle);
            fragments.add(tabFragment);
            tabAdapter.notifyDataSetChanged();

        }
        return indexLayout;
    }

    @Override
    protected boolean isAllTranslucentStatus() {
        return true;
    }

    @Override
    public void onEventMain(EventMainObject event) {
        for (BaseFrament tabFragment : fragments) {
            tabFragment.onEventMain(event);
        }
        if (tabsCache != null) {
            if (event.getCommand().equals(tabsCache.getCommand())) {
                if (event.getData() instanceof TabModel) {
                    TabModel tabModel = (TabModel) event.getData();
                    tabs_layout.setBackgroundColor(Color.parseColor(tabModel.getColor()));
                    LinearLayout indexLayout = addTabs(tabModel);
                    viewPager.setOffscreenPageLimit(fragments.size());
                    loadEmptyViewControl.loadcomplete();
                    if (indexLayout != null) {
                        onClick(indexLayout);
                    }
                } else {
                    loadEmptyViewControl.loadError();
                }
            }
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.e("onpause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.e("onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.e("onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.e("onDestroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.e("onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.e("onStop");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        BaseApplication.Exit();
    }

    @Override
    public void onEventThread(EventThreadObject event) {
        ToastUtils.showToast(AppLibUtils.getversionCode(getActivity()) + "");
//        if (event.getCommand().equals(url)) {
//            if (event.isSuccess()) {
//                String per = (String) event.getData();
//                LogUtil.i(per);
//            } else {
//                LogUtil.i("请求失败 或者JSON 错误");
//            }
//        }

    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < tabs_layout.getChildCount(); i++) {
            TabsBean tabsBean = (TabsBean) tabs_layout.getChildAt(i).getTag(R.id.maintabs_id);
            if (v == tabs_layout.getChildAt(i)) {
                tabsBean.setIndex(1);
            } else {
                tabsBean.setIndex(0);
            }
            SimpleDraweeView imageView = (SimpleDraweeView) ((LinearLayout) tabs_layout.getChildAt(i)).getChildAt(0);
            if (tabsBean.getIndex() == 1) {
                transtatus = tabsBean.isTranstatus();
//                setStatus(transtatus,0);
                viewPager.setCurrentItem(i, false);
                ImageUtils.loadImage(tabsBean.getPressedimgurl(), imageView);
            } else {
                ImageUtils.loadImage(tabsBean.getNormalimgurl(), imageView);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        APPAccountManager.INSTANCE.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
