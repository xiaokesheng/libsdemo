package com.zxb.libsdemo;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

/**
 * Created by yufangyuan on 2017/8/14.
 */

public class App extends MultiDexApplication {

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
