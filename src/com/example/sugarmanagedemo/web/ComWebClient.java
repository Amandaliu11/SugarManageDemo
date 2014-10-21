package com.example.sugarmanagedemo.web;

import android.graphics.Bitmap;
import android.os.Message;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 主要用来辅助WebView处理各种通知、请求等事件
 * com.example.webviewtest.ComWebClient
 * @author Amanda
 * Create at 2014年10月13日 上午10:18:59
 */
public class ComWebClient extends WebViewClient {
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        // TODO Auto-generated method stub
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        // TODO Auto-generated method stub
        super.onPageFinished(view, url);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        // TODO Auto-generated method stub
        super.onLoadResource(view, url);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode,
            String description, String failingUrl) {
        // TODO Auto-generated method stub
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    @Override
    public void onFormResubmission(WebView view, Message dontResend,
            Message resend) {
        // TODO Auto-generated method stub
        super.onFormResubmission(view, dontResend, resend);
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url,
            boolean isReload) {
        // TODO Auto-generated method stub
        super.doUpdateVisitedHistory(view, url, isReload);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
