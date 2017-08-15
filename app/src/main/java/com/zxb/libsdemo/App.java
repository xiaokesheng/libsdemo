package com.zxb.libsdemo;

import android.app.Application;

/**
 * Created by yufangyuan on 2017/8/14.
 */

public class App extends Application {

    public static App mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = (App) getApplicationContext();
    }

    public static App getApp() {
        return mApp;
    }
}
