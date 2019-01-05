package com.work.app.ztea.ui.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dream.library.utils.annotation.annotation.ViewInject;
import com.work.app.ztea.R;
import com.work.app.ztea.adapter.OrderStatisAdapter;
import com.work.app.ztea.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huiliu
 */
public class OrderStatisActivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.rv_order_statis)
    RecyclerView mOrderStatis;

    private List<String> mDatas;
    private OrderStatisAdapter mVipProxyAdapter;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_order_statis;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        setTopTitle("订单统计");
        initRecycler();
    }

    private void initRecycler() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mDatas.add("");
        }
        mVipProxyAdapter = new OrderStatisAdapter(this, mDatas, this);
        mOrderStatis.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mOrderStatis.setAdapter(mVipProxyAdapter);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        startAct(OrderDetailActivity.class);
    }
}
