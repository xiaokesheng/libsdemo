package com.zxb.libsdemo.util;

import android.app.Activity;
import android.webkit.WebView;

import com.zxb.libsdemo.activity.webview.BasePresentor;
import com.zxb.libsdemo.activity.webview.ClickViewHolder;

/**
 * Created by yufangyuan on 2018/4/2.
 */

public class PayPresentor extends BasePresentor {


    public PayPresentor(Activity activity, WebView webView, ClickViewHolder viewHolder) {
        super(activity, webView, viewHolder);
    }

    @Override
    protected void initParams() {

    }

    @Override
    protected void afterParams() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return false;
    }
}
