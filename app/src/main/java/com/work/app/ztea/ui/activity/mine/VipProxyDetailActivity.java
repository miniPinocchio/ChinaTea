package com.work.app.ztea.ui.activity.mine;

import android.os.Bundle;

import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseActivity;

/**
 * @author huiliu
 * 尊钻代理
 */
public class VipProxyDetailActivity extends BaseActivity {


    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_vip_proxy_detail;
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
