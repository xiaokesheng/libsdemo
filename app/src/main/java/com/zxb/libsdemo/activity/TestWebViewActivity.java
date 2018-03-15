package com.zxb.libsdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.zxb.libsdemo.R;
import com.zxb.libsdemo.util.Constants;
import com.zxb.libsdemo.util.J;

/**
 * Created by yufangyuan on 2018/3/13.
 */

public class TestWebViewActivity extends BaseActivity {

    WebView wvContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_test_webview);

        wvContent = (WebView) findViewById(R.id.wvContent);

        wvContent.getSettings().setJavaScriptEnabled(true);
//        wvContent.getSettings().setSupportZoom(true);
//        wvContent.getSettings().setBuiltInZoomControls(true);
//        wvContent.getSettings().setUseWideViewPort(true);

        J.j("WebView", Constants.testUrl);
        wvContent.loadUrl(Constants.testUrl);
    }
}
