package com.work.app.ztea.ui.activity.mine;

import android.os.Bundle;

import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseActivity;

/**
 * @author huiliu
 */
public class DisGoodsActivity extends BaseActivity {


    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_dis_goods;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        setTopTitle("配货对象");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
