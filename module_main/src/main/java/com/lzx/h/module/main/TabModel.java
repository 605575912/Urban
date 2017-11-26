package com.lzx.h.module.main;

import java.util.List;

/**
 * Created by Administrator on 2017/06/09 0009.
 */

public class TabModel {
    String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    private List<TabsBean> tabs;

    public List<TabsBean> getTabs() {
        return tabs;
    }

    public void setTabs(List<TabsBean> tabs) {
        this.tabs = tabs;
    }

}
