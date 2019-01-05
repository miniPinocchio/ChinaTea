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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dream.library.utils.annotation.annotation.ViewInject;
import com.dream.library.utils.annotation.annotation.event.OnClick;
import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseFragment;
import com.work.app.ztea.utils.WebUtils;

/**
 * 首页
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    @ViewInject(R.id.tv_address)
    TextView tv_address;

    @ViewInject(R.id.tv_msg_num)
    TextView tv_msg_num;

    @ViewInject(R.id.web_view)
    WebView mWebView;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
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
        mWebView.loadUrl("http://zhongcha.hqdemo.cn/index.php?m=mobile&c=index&a=index");
    }

    @OnClick({R.id.tv_address,R.id.layout_search,R.id.layout_msg})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_address://定位地址

                break;
            case R.id.layout_search://搜索

                break;
            case R.id.layout_msg://消息

                break;
        }
    }
}
