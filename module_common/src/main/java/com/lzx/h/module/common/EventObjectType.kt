package com.lzx.h.module.common

/**
 * Created by Administrator on 2017/05/25 0025.
 */

enum class EventObjectType private constructor(//项目数据项

        private val type: Int) {
    PoiItem(0x001), //兴趣点
    Place(0x002), //地块
    FILEbrowse(0x003), //文件目录浏览

    Fileup(0x004), //文件上传

    Polyline(0x005), //测量长度
    PlaceItemModel(0x006), //测量长度
    PoiItemModel(0x007), //测量长度
    Feature(0x009), //点击地图上的地块
    UpdatePoi(0x010), //修改兴趣点名称
    FileCache(0x011), //缓存设置
    LoginSet(0x012), //缓存设置
    AboutApp(0x013), //关于APP
    ChangeAccount(0x014), //切换账号
    ChangeGPS(0x015), //GPS设置
    OpenSetting(0x016), //打开GPS设置
    sortorderby(0x017), //GPS设置
    GetLogDate(0x018), //日期
    LayerPostion(0x019), //图层设置
    DelPoi(0x020), //图层设置
    Graffiti(0x021), //图层设置
    UpdateFeature(0x021), //更新图层属性
    MissUI(0x022), //
    SearchModel(0x023), //点击搜索列表
    DownLoadComplete(0x024), //点击搜索列表
    DownLoadProgress(0x025), //下载进度
    MapSet(0x026), //地图设置
    OnlineSet(0x027), //离线设置
    TakeMarkSet(0x028), //添加地图时刻
    PictureSizeSet(0x029), //拍照大小设置
    DownDataSet(0x030), //下载数据设置
    ProjectModel(0x008)
}
