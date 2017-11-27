package com.lzx.h.module.main;

/**
 * Created by Administrator on 2017/05/31 0031.
 */

public class UserData {

    /**
     * imgurl : https: //ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3527597864,2116312579&fm=117&gp=0.jpg
     * content :
     * title :
     * location :
     * dis : 100
     * price : 11
     * originaprice : 12
     * sellcount : 110
     * jumpcontent :
     * type : 1
     */

    private String imgurl;
    private String content;
    private String name;
    private String location;
    private String bgurl;
    private int dis;
    private int sellcount;
    private String jumpcontent;
    private int type;

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getDis() {
        return dis;
    }

    public void setDis(int dis) {
        this.dis = dis;
    }


    public int getSellcount() {
        return sellcount;
    }

    public void setSellcount(int sellcount) {
        this.sellcount = sellcount;
    }

    public String getJumpcontent() {
        return jumpcontent;
    }

    public void setJumpcontent(String jumpcontent) {
        this.jumpcontent = jumpcontent;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBgurl() {
        return bgurl;
    }

    public void setBgurl(String bgurl) {
        this.bgurl = bgurl;
    }
}
