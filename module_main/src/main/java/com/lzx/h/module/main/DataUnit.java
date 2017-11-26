package com.lzx.h.module.main;

import com.squareup.lib.utils.IProguard;

import java.util.List;

/**
 * Created by Administrator on 2017/05/31 0031.
 */

public class DataUnit implements IProguard.ProtectMembers {
    private int titletype = -1;//-1 w无  1  搜索
    private List<Card> cards;//大卡片
    private List<ItemData> items;//一组Item
//    private List<MineCard> minecards;//一组Item
//    private List<DetailCard> detailcards;//详情


    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<ItemData> getItems() {
        return items;
    }

    public void setItems(List<ItemData> items) {
        this.items = items;
    }

    public int getTitletype() {
        return titletype;
    }

//    public List<MineCard> getMinecards() {
//        return minecards;
//    }
//
//    public List<DetailCard> getDetailcards() {
//        return detailcards;
//    }
}
