package com.zjk.re;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by zhongjiakang on 16/2/19.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
