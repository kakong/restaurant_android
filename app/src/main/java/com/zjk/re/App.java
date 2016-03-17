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
        //实例化极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
