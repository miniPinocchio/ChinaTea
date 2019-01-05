package com.work.app.ztea.ui.activity.mine;

import android.os.Bundle;

import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseActivity;

/**
 * @author huiliu
 */
public class DisSaleGoodsActivity extends BaseActivity {


    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_dis_sale_goods;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        setTopTitle("销售费用");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
