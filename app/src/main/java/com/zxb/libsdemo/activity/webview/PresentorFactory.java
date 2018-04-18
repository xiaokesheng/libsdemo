package com.zxb.libsdemo.activity.webview;

import android.app.Activity;
import android.webkit.WebView;

/**
 * Created by yufangyuan on 2018/4/2.
 */

public abstract class PresentorFactory {

    public static PresentorFactory instance;

    public static void createNewInstance(PresentorFactory i) {
        instance = i;
    }

    public abstract BasePresentor getPresentor(Activity a, WebView view, ClickViewHolder holder, int type);

    public static PresentorFactory getFactory() {
        return instance;
    }

}
