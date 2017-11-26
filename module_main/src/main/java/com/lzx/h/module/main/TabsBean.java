package com.lzx.h.module.main;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/06/09 0009.
 */

public class TabsBean implements Parcelable {
    private int id;
    private boolean transtatus;
    private String normalimgurl;
    private String pressedimgurl;
    private String jumpcontent;
    private String title;
    int index = 0;

    protected TabsBean(Parcel in) {
        id = in.readInt();
        transtatus = in.readByte() != 0;
        normalimgurl = in.readString();
        pressedimgurl = in.readString();
        jumpcontent = in.readString();
        title = in.readString();
        index = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeByte((byte) (transtatus ? 1 : 0));
        dest.writeString(normalimgurl);
        dest.writeString(pressedimgurl);
        dest.writeString(jumpcontent);
        dest.writeString(title);
        dest.writeInt(index);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TabsBean> CREATOR = new Creator<TabsBean>() {
        @Override
        public TabsBean createFromParcel(Parcel in) {
            return new TabsBean(in);
        }

        @Override
        public TabsBean[] newArray(int size) {
            return new TabsBean[size];
        }
    };

    public boolean isTranstatus() {
        return transtatus;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public TabsBean() {

    }

    public int getId() {
        return id;
    }

    public String getNormalimgurl() {
        return normalimgurl;
    }

    public String getPressedimgurl() {
        return pressedimgurl;
    }

    public String getJumpcontent() {
        return jumpcontent;
    }

    public String getTitle() {
        return title;
    }






}
