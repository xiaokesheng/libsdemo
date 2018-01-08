package com.zxb.libsdemo;


import android.app.Application;

import com.zxb.libsdemo.tinkerpack.util.SampleApplicationContext;

/**
 * Created by yufangyuan on 2017/8/14.
 */

public class App {
//public class App extends Application {

    public static Application mApp;

    static {
        mApp = SampleApplicationContext.application;
        String aaa = mApp.getApplicationContext().getPackageName();
    }

    public static Application getApp() {
        return mApp;
    }
}
