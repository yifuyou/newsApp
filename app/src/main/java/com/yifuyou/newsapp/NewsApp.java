package com.yifuyou.newsapp;

import android.app.Application;

import com.yifuyou.newsapp.common.InitCommon;

public class NewsApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DataInit.init();
        InitCommon.init(this);
    }



}
