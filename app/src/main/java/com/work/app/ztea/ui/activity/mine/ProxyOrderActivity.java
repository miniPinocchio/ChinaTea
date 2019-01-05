package com.work.app.ztea.ui.activity.mine;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dream.library.utils.annotation.annotation.ViewInject;
import com.work.app.ztea.R;
import com.work.app.ztea.adapter.CustomProxyAdapter;
import com.work.app.ztea.adapter.LoadMoreAdapter;
import com.work.app.ztea.adapter.VipProxyAdapter;
import com.work.app.ztea.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * @author huiliu
 * 代理订单
 */
public class ProxyOrderActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener {


    @ViewInject(R.id.rb_proxy_setting)
    RadioButton mRbProxySetting;
    @ViewInject(R.id.rb_custom_setting)
    RadioButton mRbCustomSetting;
    @ViewInject(R.id.rg_proxy_setting)
    RadioGroup mRgProxySetting;
    @ViewInject(R.id.rv_proxy)
    RecyclerView mRvProxy;
    @ViewInject(R.id.rv_custom)
    RecyclerView mRvCustom;
//    @ViewInject(R.id.bga_refresh_proxy)
//    BGARefreshLayout mBgaRefreshProxy;

    private List<String> mDatas;
    private VipProxyAdapter mVipProxyAdapter;
    private LoadMoreAdapter mLoadMoreAdapter;
    private CustomProxyAdapter mCustomProxyAdapter;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_proxy_order;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        setTopTitle("代理订单");
        setRightTitle("我的征订", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });


        mRgProxySetting.setOnCheckedChangeListener(this);
        mRgProxySetting.check(R.id.rg_proxy_setting);
        initRecycler();
    }

    private void initRecycler() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mDatas.add("");
        }
        mVipProxyAdapter = new VipProxyAdapter(this, mDatas, this);
        mCustomProxyAdapter = new CustomProxyAdapter(this, mDatas, this);

//        mLoadMoreAdapter = new LoadMoreAdapter(this, mVipProxyAdapter);
//        mLoadMoreAdapter.setLoadMoreListener(this);
        mRvProxy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvProxy.setAdapter(mVipProxyAdapter);
        mRvCustom.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvCustom.setAdapter(mCustomProxyAdapter);

        mRvCustom.setVisibility(View.GONE);
//        initRefreshLayout();
    }

//    private void initRefreshLayout() {
//        // 为BGARefreshLayout 设置代理
//        mBgaRefreshProxy.setDelegate(this);
//        BGAMeiTuanRefreshViewHolder meiTuanRefreshViewHolder = new BGAMeiTuanRefreshViewHolder(this, true);
//        meiTuanRefreshViewHolder.setPullDownImageResource(R.mipmap.bga_refresh_mt_pull_down);
//        meiTuanRefreshViewHolder.setChangeToReleaseRefreshAnimResId(R.drawable.bga_refresh_mt_change_to_release_refresh);
//        meiTuanRefreshViewHolder.setRefreshingAnimResId(R.drawable.bga_refresh_mt_refreshing);
//        mBgaRefreshProxy.setRefreshViewHolder(meiTuanRefreshViewHolder);
//    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_proxy_setting:
                mRvCustom.setVisibility(View.GONE);
                mRvProxy.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_custom_setting:
                mRvCustom.setVisibility(View.VISIBLE);
                mRvProxy.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_item_proxy:
                startAct(VipProxyDetailActivity.class);
                break;
            case R.id.rl_item_custom:
                startAct(CustomProxyDetailActivity.class);
                break;
            default:
                break;
        }
    }

//    @Override
//    public void loadMore() {
//        mRvProxy.swapAdapter(mVipProxyAdapter, false);
//    }
}
