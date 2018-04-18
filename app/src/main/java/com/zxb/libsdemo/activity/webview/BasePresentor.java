package com.zxb.libsdemo.activity.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by yufangyuan on 2018/4/2.
 */
public abstract class BasePresentor {

    Activity mActivity;
    WebView mWebView;
    ClickViewHolder viewHolder;

    public BasePresentor(Activity activity, WebView webView, ClickViewHolder viewHolder) {
        this.mActivity = activity;
        this.mWebView = webView;
        this.viewHolder = viewHolder;
    }

    protected abstract void initParams();

    protected abstract void afterParams();

    protected abstract void setListener();

    protected void syncCookies(Context context, String url) {

    }

    protected void resume() {

    }

    public void onResume() {

    }

    public void onPause() {

    }

    public void onDestroy() {

    }

    public void onCreate() {

    }

    public void onStart() {

    }

    protected void onNewIntent(Intent intent) {

    }

    public void exit() {
        mActivity.finish();
    }

    protected void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        }
    }

    protected void onBack() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        }
    }

    protected void onClose() {
        mActivity.finish();
    }

    protected void onShare() {
        Log.e("webview", "share~~~");
    }

    protected void onMenu() {
        Log.e("webview", "menu~~~");
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {


    }

    public abstract boolean shouldOverrideUrlLoading(WebView view, String url);

    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

    }

    public void onPageFinished(WebView view, String url) {

    }

    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    // For Android < 3.0
    public void openFileChooserBelow3_0(ValueCallback<Uri> valueCallback) {
    }

    // For Android  >= 3.0
    public void openFileChooserUp3_0(ValueCallback valueCallback, String acceptType) {
    }

    //For Android  >= 4.1
    public void openFileChooserUp4_1(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
    }

    // For Android >= 5.0
    public boolean onShowFileChooserUp5_1(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        return true;
    }

    public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
    }
    public void onReceivedTitle(WebView view, String title) {
    }

    public void onProgressChanged(WebView view, int newProgress) {
    }

    public void setWebAppInterface() {

    }
}
