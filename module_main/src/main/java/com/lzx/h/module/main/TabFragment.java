package com.lzx.h.module.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.squareup.lib.EventMainObject;
import com.squareup.lib.HttpUtils;
import com.squareup.lib.frament.ListFrament;
import com.squareup.lib.utils.AppLibUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/06/09 0009.
 */

public class TabFragment extends ListFrament {


    TabsBean tabsBean;
    LoadEmptyViewControl loadEmptyViewControl;
    FrameLayout frameLayout;
    View titleview;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getFromLayoutID() {
        return R.layout.tab_layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tabsBean = getArguments().getParcelable("TabsBean");
        if (tabsBean != null) {
            onRefresh();
        } else {
            tabsBean = new TabsBean();
        }
    }

    protected void onRefresh() {
//        HttpUtils.INSTANCE.getAsynMainHttp(tabsBean.getJumpcontent(), DataUnit.class);//返回根据JSON解析的对象
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        frameLayout = (FrameLayout) contentView.findViewById(R.id.container);
        loadEmptyViewControl = new LoadEmptyViewControl(getActivity());
        loadEmptyViewControl.addLoadView(frameLayout);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (titleview != null) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof LinearLayoutManager) {
                        LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                        int firstItemPosition = linearManager.findFirstVisibleItemPosition();
//                        if (list.get(firstItemPosition) instanceof BannerView) {
//                            View view = recyclerView.getChildAt(0);
//                            float maxh = view.getHeight() - titleview.getHeight();
//                            float alpha = Math.abs(view.getY() / maxh);
//                            if (alpha > 1) {
//                                titleview.setBackgroundColor(Color.argb(255, 51, 249, 222));
//                            } else {
//                                titleview.setBackgroundColor(Color.argb((int) (alpha * 255), 51, 249, 222));
//                            }
//                        }
                    }
                }
            }
        });
        return contentView;
    }


//    private void addTitleView(int titletype) {
//        if (titletype == 1) {
//            titleview = LayoutInflater.from(getActivity()).inflate(R.layout.home_title_layout, frameLayout, false);
//            titleview.setPadding(0, AppLibUtils.getStatusBarHeight(), 0, 0);
//            ViewDataBinding viewDataBinding = DataBindingUtil.bind(titleview);
//            viewDataBinding.setVariable(BR.onclick, this);
//            viewDataBinding.setVariable(BR.title, "稻香");
//            frameLayout.addView(titleview);
//        }
//    }

    public void onEventMain(EventMainObject event) {
        if (tabsBean == null) {
            return;
        }

        if (event.getCommand().equals(tabsBean.getJumpcontent())) {
//            if (event.getData() instanceof DataUnit) {
//                finishRefreshing();
//                loadEmptyViewControl.loadcomplete();
//                list.clear();
////                addData((DataUnit) event.getData());
//            } else {
//                loadEmptyViewControl.loadError(event.getData().toString());
//            }
        }
    }

//    private void addData(DataUnit dataUnit) {
//        if (dataUnit.getTitletype() > 0) {
//            addTitleView(dataUnit.getTitletype());
//        }
//        if (dataUnit.getMinecards() != null) {
//            for (MineCard mineCard : dataUnit.getMinecards()) {
//                if (mineCard.getLogincard() != null) {
//                    LoginCardView loginCardView = new LoginCardView(getActivity());
//                    list.add(loginCardView);
//                    MineSpaceView mineSpaceView = new MineSpaceView();
//                    list.add(mineSpaceView);
//                }
//                List<MineCardUnit> cardUnits = mineCard.getCardUnits();
//                if (cardUnits != null) {
//                    for (MineCardUnit mineCardUnit : cardUnits) {
//                        List<MineItemData> items = mineCardUnit.getItems();
//                        if (items != null) {
//                            for (int i = 0; i < items.size(); i++) {
//                                MineItemView mineItemView = new MineItemView(getActivity(), items.get(i));
//                                list.add(mineItemView);
//                                if (i < items.size() - 1) {
//                                    MineLineView mineLineView = new MineLineView();
//                                    list.add(mineLineView);
//                                }
//                                if (i == items.size() - 1) {
//                                    MineSpaceView mineSpaceView = new MineSpaceView();
//                                    list.add(mineSpaceView);
//                                }
//                            }
//
//                        }
//                    }
//
//                }
//
//            }
//        }
//        if (dataUnit.getCards() != null) {
//            for (Card card : dataUnit.getCards()) {
//                List<BannerModel> banners = card.getBanners();
//                if (banners != null && banners.size() > 0) {
//                    BannerView bannerView = new BannerView(banners);
//                    list.add(bannerView);
//                }
//                List<ColumnData> columnitems = card.getColumnitems();
//                if (columnitems != null && columnitems.size() > 0) {
//                    ColumnView columnView = new ColumnView(columnitems);
//                    list.add(columnView);
//                    MineSpaceView mineSpaceView = new MineSpaceView();
//                    list.add(mineSpaceView);
//                }
//                List<DiscountData> discountdatas = card.getDiscountdatas();
//                if (discountdatas != null && discountdatas.size() > 0) {
//                    DiscountView discountView = new DiscountView(discountdatas);
//                    list.add(discountView);
//                    MineSpaceView mineSpaceView = new MineSpaceView();
//                    list.add(mineSpaceView);
//                }
//                List<CardUnit> cardUnits = card.getCardUnits();
//                if (cardUnits != null) {
//                    for (CardUnit cardUnit : cardUnits) {
//                        List<ItemData> itemDatas = cardUnit.getItems();
//                        if (itemDatas == null || itemDatas.size() == 0) {
//                            continue;
//                        }
//                        if (itemDatas.size() == 1) {
//                            ItemView baseViewItem = new ItemView(getActivity(), itemDatas.get(0));
//                            list.add(baseViewItem);
//                            continue;
//                        }
//                        ChangedItemView baseViewItem = new ChangedItemView(getActivity(), itemDatas);
//                        list.add(baseViewItem);
//
//                    }
//                    MineSpaceView mineSpaceView = new MineSpaceView();
//                    list.add(mineSpaceView);
//                }
//                List<ItemData> itemDatas = card.getItems();
//                if (itemDatas != null) {
//                    for (ItemData itemData : itemDatas) {
//                        DoubleItemView mainItemView = new DoubleItemView(getActivity());
//                        mainItemView.setItemData(itemData);
//                        list.add(mainItemView);
//                    }
//                    MineSpaceView mineSpaceView = new MineSpaceView();
//                    list.add(mineSpaceView);
//                }
//
//            }
//        }
//        List<ItemData> itemDatas = dataUnit.getItems();
//        if (itemDatas != null) {
//            for (ItemData itemData : dataUnit.getItems()) {
//                if (itemData.getType() == 1) {//推荐
//                    PushItemView mainItemView = new PushItemView(getActivity(), itemData);
//                    list.add(mainItemView);
//                    LineView lineView = new LineView();
//                    list.add(lineView);
//                } else {
//
//                }
//
//            }
//        }
//        notifyDataSetChanged();
//    }

    public void onClick(View view) {
//        Intent intent = new Intent();
//        intent.setClass(getActivity(), SearchHisActivity.class);
//        getActivity().startActivity(intent);
    }
}
