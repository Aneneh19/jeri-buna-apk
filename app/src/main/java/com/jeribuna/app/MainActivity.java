package com.jeribuna.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.PermissionRequest;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;

public class MainActivity extends Activity {
    private WebView webView;

    @SuppressLint({"SetJavaScriptEnabled","WebViewApiAvailability"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null) getActionBar().hide();

        WebView.setWebContentsDebuggingEnabled(true);
        webView = new WebView(this);
        setContentView(webView);

        WebSettings s = webView.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setDatabaseEnabled(true);
        s.setAllowFileAccess(true);
        s.setAllowContentAccess(true);
        s.setAllowFileAccessFromFileURLs(true);
        s.setAllowUniversalAccessFromFileURLs(true);
        s.setCacheMode(WebSettings.LOAD_DEFAULT);
        s.setLoadWithOverviewMode(true);
        s.setUseWideViewPort(true);
        s.setBuiltInZoomControls(false);
        s.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onPermissionRequest(PermissionRequest request){
                request.grant(request.getResources());
            }
        });

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
                handler.proceed();
            }
        });

        webView.loadUrl("file:///android_asset/index.html");
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) webView.goBack();
        else moveTaskToBack(true);
    }

    @Override
    protected void onResume() { super.onResume(); webView.onResume(); }

    @Override
    protected void onPause() { super.onPause(); webView.onPause(); }
}
