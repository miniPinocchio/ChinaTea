package com.work.app.ztea.ui.activity.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.dream.library.utils.annotation.annotation.ViewInject;
import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseActivity;

/**
 * @author huiliu
 * 订货详情
 */
public class OrderDetailActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.rl_goods_detail)
    RelativeLayout mRlGoodsDetail;
    @ViewInject(R.id.rl_goods_money)
    RelativeLayout mRlGoodsMoney;
    @ViewInject(R.id.rl_sale_money)
    RelativeLayout mRlSaleMoney;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        setTopTitle("订货详情");

        mRlGoodsDetail.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_goods_detail:
                startAct(DisGoodsActivity.class);
                break;
            case R.id.rl_goods_money:
                startAct(DisGoodsActivity.class);
                break;
            case R.id.rl_sale_money:
                startAct(DisSaleGoodsActivity.class);
                break;
            default:
                break;
        }
    }
}
