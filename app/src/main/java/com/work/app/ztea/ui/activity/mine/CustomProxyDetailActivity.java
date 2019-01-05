package com.work.app.ztea.ui.activity.mine;

import android.os.Bundle;

import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseActivity;

/**
 * @author huiliu
 *
 */
public class CustomProxyDetailActivity extends BaseActivity {

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_custom_proxy;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        setTopTitle("商品信息设置");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
