package com.work.app.ztea.ui.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.dream.library.utils.annotation.annotation.ViewInject;
import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseFragment;
import com.work.app.ztea.utils.WebUtils;

/**
 * 学院
 * A simple {@link Fragment} subclass.
 */
public class CollegeFragment extends BaseFragment {

    @ViewInject(R.id.tv_title)
    TextView tv_title;

    @ViewInject(R.id.web_view)
    WebView mWebView;

    public CollegeFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_college;
    }

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        tv_title.setText("学院");
        WebUtils.initWeb(mWebView);
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    hideProgressDialog();
                } else {
                    showProgressDialog("加载中");
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
    protected void initData(View view, @Nullable Bundle savedInstanceState) {
        mWebView.loadUrl("http://zhongcha.hqdemo.cn/index.php?m=mobile&c=index&a=news");
    }

}
