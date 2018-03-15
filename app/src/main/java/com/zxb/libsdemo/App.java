package com.zxb.libsdemo;


import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.DefaultApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.zxb.libsdemo.tinkerpack.Log.MyLogImp;
import com.zxb.libsdemo.tinkerpack.util.SampleApplicationContext;
import com.zxb.libsdemo.tinkerpack.util.TinkerManager;
import com.zxb.libsdemo.util.CrashHandler;
import com.zxb.libsdemo.util.J;
import com.zxb.libsdemo.util.ToastMgr;

/**
 * Created by yufangyuan on 2017/8/14.
 */
@SuppressWarnings("unused")
@DefaultLifeCycle(application = "com.zxb.libsdemo.tinkerpack.app.App",
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false)
public class App extends DefaultApplicationLike {

    public static Application mApp;

    public App(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime, long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        //you must install multiDex whatever tinker is installed!
        MultiDex.install(base);

        SampleApplicationContext.application = getApplication();
        SampleApplicationContext.context = getApplication();
        TinkerManager.setTinkerApplicationLike(this);

        TinkerManager.initFastCrashProtect();
        //should set before tinker is installed
        TinkerManager.setUpgradeRetryEnable(true);

        //optional set logIml, or you can use default debug log
        TinkerInstaller.setLogIml(new MyLogImp());

        //installTinker after load multiDex
        //or you can put com.tencent.tinker.** to main dex
        TinkerManager.installTinker(this);
        Tinker tinker = Tinker.with(getApplication());

        ToastMgr.builder.init(getApplication());
        mApp = getApplication();

        CrashHandler.getInstance().init(mApp);
        J.init(mApp);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = getApplication();
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks callback) {
        getApplication().registerActivityLifecycleCallbacks(callback);
    }

    public static Application getApp() {
        return mApp;
    }
}
