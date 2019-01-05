package com.work.app.ztea.ui.activity;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.dream.library.utils.annotation.annotation.ViewInject;
import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseActivity;
import com.work.app.ztea.utils.WebUtils;

public class WebViewActivity extends BaseActivity {

    @ViewInject(R.id.webview)
    WebView mWebView;

    public static final String BUNDLE_KEY_URL = "BUNDLE_KEY_URL";
    public static final String BUNDLE_ID = "BUNDLE_NEWS_ID";
    public static final String BUNDLE_KEY_TITLE = "BUNDLE_KEY_TITLE";

    private ProgressBar mProgressBar = null;

    private String id;
    private String title;
    private String url;

    @Override
    protected void getBundleExtras(Bundle extras) {
        id = (String) extras.get(BUNDLE_ID);
        title = (String) extras.get(BUNDLE_KEY_TITLE);
        url = (String) extras.get(BUNDLE_KEY_URL);
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        setTopTitle(title);
        mProgressBar = (ProgressBar) LayoutInflater.from(mContext).inflate(com.dream.library.R.layout.progress_horizontal, null);
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
//        if(mWebView != null) {
//            mWebView = (WebView) findViewById(R.id.webview);
//        }
        WebUtils.initWeb(mWebView);
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (!TextUtils.isEmpty(url)) {
            mWebView.loadUrl(url);
        }
    }
}
