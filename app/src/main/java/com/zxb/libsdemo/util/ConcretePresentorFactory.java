package com.zxb.libsdemo.util;

import android.app.Activity;
import android.webkit.WebView;

import com.zxb.libsdemo.activity.webview.BasePresentor;
import com.zxb.libsdemo.activity.webview.ClickViewHolder;
import com.zxb.libsdemo.activity.webview.DefaultPresentor;
import com.zxb.libsdemo.activity.webview.MaPresentor;
import com.zxb.libsdemo.activity.webview.PresentorFactory;

/**
 * Created by yufangyuan on 2018/4/2.
 */

public class ConcretePresentorFactory extends PresentorFactory {
    @Override
    public BasePresentor getPresentor(Activity a, WebView view, ClickViewHolder holder, int type) {
        switch (type) {
            case 0:
                return new DefaultPresentor(a, view, holder);
            case 1:
                return new MaPresentor(a, view, holder);
            case 2:
                return new PayPresentor(a, view, holder);
            default:
                return new DefaultPresentor(a, view, holder);
        }
    }
}
