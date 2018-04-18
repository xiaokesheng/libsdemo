package com.zxb.libsdemo.activity.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.zxb.libsdemo.BuildConfig;
import com.zxb.libsdemo.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yufangyuan on 2018/4/2.
 */
@SuppressLint("SetJavaScriptEnabled")
public class WebViewActivity extends FragmentActivity implements View.OnClickListener {

    private WebView webView;
    private ClickViewHolder clickViewHolder;
    private BasePresentor presentor;

    private String mUrl;

    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_web);
        clickViewHolder = new ClickViewHolder();
        initViews();
        initParams();

        if (null != PresentorFactory.getFactory()) {
            presentor = PresentorFactory.getFactory().getPresentor(this, webView, clickViewHolder, type);
        } else {
            presentor = new DefaultPresentor(this, webView, clickViewHolder);
        }


        removeJsInterfaceBelow17();

        setWebviewSettings();
        setListeners();

        presentor.syncCookies(this, mUrl);

        presentor.setWebAppInterface();
        setWebClient();

        firstLoadUrl();
    }

    private void initParams() {
        mUrl = "";  // TODO
        type = getIntent().getIntExtra("type", 0);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        presentor.onNewIntent(intent);
    }

    private void initViews() {
        clickViewHolder.tvBack = (TextView) findViewById(R.id.tvBack);
        clickViewHolder.tvClose = (TextView) findViewById(R.id.tvClose);
        clickViewHolder.tvMenu = (TextView) findViewById(R.id.tvMenu);
        clickViewHolder.tvShare = (TextView) findViewById(R.id.tvShare);
        clickViewHolder.tvTitle = (TextView) findViewById(R.id.tvTitle);
        webView = (WebView) findViewById(R.id.webView);
    }

    private void firstLoadUrl() {
        Map<String, String> extraHeaders = new HashMap<>();
        extraHeaders.put("UserAgent", "ZhaopinApp");
        try {
            // TODO 统计sdk用
//            zlstsc.showWebView(webView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        webView.loadUrl(mUrl, extraHeaders);
    }

    private void removeJsInterfaceBelow17() {
        if (Build.VERSION.SDK_INT > 10 && Build.VERSION.SDK_INT < 17) {
            webView.removeJavascriptInterface("searchBoxJavaBridge_");
            webView.removeJavascriptInterface("accessibility");
            webView.removeJavascriptInterface("accessibilityTraversal");
        }
    }

    private void setWebviewSettings() {
        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setWebContentsDebuggingEnabled(true);
        }
        // 得到设置属性的对象
        WebSettings webSettings = webView.getSettings();
        webSettings.setAllowFileAccess(false);
        webSettings.setAllowFileAccessFromFileURLs(false);
        webSettings.setAllowUniversalAccessFromFileURLs(false);
        webSettings.setSavePassword(false);
        // 使能使用JavaScript
        webSettings.setJavaScriptEnabled(true);
        // 自适应手机屏幕
        webSettings.setLoadWithOverviewMode(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        // 支持中文，否则页面中中文显示乱码
        webSettings.setDefaultTextEncodingName("UTF-8");
        // TODO
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

    }

    private void setListeners() {
        setOnClickLiseners(this, clickViewHolder.tvBack, clickViewHolder.tvClose,
                clickViewHolder.tvShare, clickViewHolder.tvMenu);
    }

    private void setWebClient() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                presentor.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                presentor.shouldOverrideUrlLoading(view, url);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                // TODO 异常页面上报
                presentor.onReceivedError(view, request, error);
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                presentor.onPageFinished(view, url);
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
                presentor.onReceivedSslError(view, handler, error);
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                try {
                    // TODO progress
                } catch (Exception e) {
                }
                super.onProgressChanged(view, newProgress);
                presentor.onProgressChanged(view, newProgress);
            }
            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> valueCallback) {
                presentor.openFileChooserBelow3_0(valueCallback);
            }

            // For Android  >= 3.0
            public void openFileChooser(ValueCallback valueCallback, String acceptType) {
                presentor.openFileChooserUp3_0(valueCallback, acceptType);
            }

            //For Android  >= 4.1
            public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
                presentor.openFileChooserUp4_1(valueCallback, acceptType, capture);
            }

            // For Android >= 5.0
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                presentor.onShowFileChooserUp5_1(webView, filePathCallback, fileChooserParams);
                return true;
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, true);
                presentor.onGeolocationPermissionsShowPrompt(origin, callback);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                presentor.onReceivedTitle(view, title);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presentor.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvClose:
                presentor.onClose();
                break;
            case R.id.tvShare:
                presentor.onShare();
                break;
            case R.id.tvBack:
                presentor.onBack();
                break;
            case R.id.tvMenu:
                presentor.onMenu();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        // TODO 后退逻辑
        presentor.onBackPressed();
    }

    private void setOnClickLiseners(View.OnClickListener listener, View... views) {
        if (views.length > 0) {
            for (View view : views) {
                view.setOnClickListener(listener);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presentor.onActivityResult(requestCode, resultCode, data);
    }
}
