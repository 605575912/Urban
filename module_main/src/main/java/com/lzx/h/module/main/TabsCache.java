package com.lzx.h.module.main;

import com.lzx.h.module.common.utils.URLUtils;
import com.squareup.lib.HttpUtils;
import com.squareup.lib.utils.FileUtils;

import java.io.File;

/**
 * Created by Administrator on 2017/06/09 0009.
 */

public class TabsCache {
    private String filename = "tabs.txt";
    private String command = "";

    public String getCommand() {
        return command;
    }

    public void getCacheData() {
        File file = FileUtils.getFile(filename);
        if (file != null) {
            if (file.exists()) {
                command = HttpUtils.INSTANCE.getAsynMainHttp(FileUtils.getFile(filename), TabModel.class);//返回根据JSON解析的对象
                return;
            }
        }
        command = "file:///android_asset/tabs.txt";
        HttpUtils.INSTANCE.getAsynMainHttp(command, TabModel.class);//返回根据JSON解析的对象
    }

    public void dowlNewWorkData() {
        HttpUtils.INSTANCE.getAsynThreadHttp(URLUtils.getInstance().getTabsurl(), TabModel.class, new HttpUtils.HttpListener() {
            @Override
            public void success(Object model, String data) {
                if (model instanceof TabModel) {
                    FileUtils.saveFile(FileUtils.getFile(filename), data);
                }
            }

            @Override
            public void failed(Object model) {

            }
        });//返回根据JSON解析的对象
    }
}
