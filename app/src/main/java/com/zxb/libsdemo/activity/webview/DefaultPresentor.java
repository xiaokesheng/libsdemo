package com.zxb.libsdemo.activity.webview;

import android.app.Activity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by yufangyuan on 2018/4/2.
 */
public class DefaultPresentor extends BasePresentor {

    public DefaultPresentor(Activity context, WebView webView, ClickViewHolder viewHolder) {
        super(context, webView, viewHolder);
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
