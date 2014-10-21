package com.example.sugarmanagedemo.fragment;

import org.kymjs.aframe.ui.BindView;
import org.kymjs.aframe.ui.fragment.BaseFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.sugarmanagedemo.R;
import com.example.sugarmanagedemo.web.ComWebClient;

public class TopicDetailFragment extends BaseFragment {
    @BindView(id = R.id.web)
    private WebView mWebView;
    
    private View mParent;
    
    private WebSettings webSettings;
    private ComWebClient webClient;
    private String strUrl = "www.baidu.com";

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container,
            Bundle bundle) {
        mParent = inflater.inflate(R.layout.fragment_topicdetail, null);
               
        
        return mParent;
    }
    
    @Override
    public void initData(){
        super.initData();
        
        if(this.getArguments()!= null){
            strUrl = this.getArguments().getString("url");
        }
        
        
        //WebSettings
        webSettings = mWebView.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        
        //WebClient
        webClient = new ComWebClient();
        mWebView.setWebViewClient(webClient);
        
        //WebChromeClient
        mWebView.setWebChromeClient(new WebChromeClient()); 
    }
    
    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        
        if(null != strUrl){
            mWebView.loadUrl(strUrl);
        }
    }

}
