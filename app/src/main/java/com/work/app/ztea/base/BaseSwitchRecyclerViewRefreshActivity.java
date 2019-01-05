package com.work.app.ztea.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dream.library.utils.AbToastUtils;
import com.dream.library.widgets.loadmorerecycler.LoadMoreRecyclerView;
import com.pacific.adapter.RecyclerAdapter;
import com.work.app.ztea.R;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Author:      qiyunfeng
 * Date:        16/1/5 上午11:02
 * Description: BaoTan
 */
public abstract class BaseSwitchRecyclerViewRefreshActivity<T> extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {

    protected static final int STATE_NONE = 0;
    protected static final int STATE_REFRESH = 1;
    protected static final int STATE_LOADMORE = 2;
    protected int mState = STATE_NONE;

    public BGARefreshLayout mBGARefreshLayout;
    public LoadMoreRecyclerView mLoadMoreRecyclerView;

    protected RecyclerAdapter<T> mAdapter;

    protected int mPage = 0;
    protected int mPageSize = 10;
    public BGARefreshViewHolder mBGARefreshViewHolder;


    @Override
    protected View getLoadingTargetView() {
        return findViewById(R.id.mBGARefreshLayout);
    }

    protected void initPullRefreshAndLoadMore() {
        mLoadMoreRecyclerView = (LoadMoreRecyclerView) findViewById(R.id.mRecyclerView);
        mBGARefreshLayout = (BGARefreshLayout) findViewById(R.id.mBGARefreshLayout);
        // 下拉刷新
        if (mBGARefreshLayout != null) {
            mBGARefreshViewHolder = new BGANormalRefreshViewHolder(mContext, false);
            //设置刷新样式
            mBGARefreshLayout.setRefreshViewHolder(mBGARefreshViewHolder);
            mBGARefreshLayout.setDelegate(this);

        }


        // 上拉加载
        if (mLoadMoreRecyclerView != null) {
            mLoadMoreRecyclerView.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
                @Override
                public void onLoadMore() {
                    loadMoreData();
                }
            });
        }
        mLoadMoreRecyclerView.setHasFixedSize(true);
        mLoadMoreRecyclerView.setLayoutManager(getLayoutManager());
        initAdapter();
        mLoadMoreRecyclerView.setAdapter(mAdapter);
        refreshData();
    }

    //切换布局
    protected void switchLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mLoadMoreRecyclerView.switchLayoutManager(layoutManager);
    }

    protected void refreshData() {
        if (mState == STATE_NONE) {
            mState = STATE_REFRESH;
            mPage = 0;
            loadData();
        }
    }

    private void loadMoreData() {
        if (mState == STATE_NONE) {
            mState = STATE_LOADMORE;
            mPage++;
            loadData();
        }
    }

    protected abstract void initAdapter();

    protected abstract void loadData();

    protected abstract RecyclerView.LayoutManager getLayoutManager();

    protected void onLoadDataFail() {
        switch (mState) {
            case STATE_REFRESH:
                refreshDataFail();
                break;
            case STATE_LOADMORE:
                loadMordDataFail();
                break;
        }
        mState = STATE_NONE;
    }

    protected void onLoadDataSuccess(List<T> data) {
        switch (mState) {
            case STATE_REFRESH:
                refreshDataSuccess(data);
                break;
            case STATE_LOADMORE:
                loadMordDataSuccess(data);
                break;
        }
        mState = STATE_NONE;
    }

    private void refreshDataSuccess(List<T> list) {
        if (mBGARefreshLayout == null) return;
        mBGARefreshLayout.endRefreshing();
        if (list != null && list.size() > 0) {
            if (list.size() < mPageSize) {
                // 获取这页数据时，没有返回mRows条
                mLoadMoreRecyclerView.setAutoLoadMoreEnable(false);
            } else {
                mLoadMoreRecyclerView.setAutoLoadMoreEnable(true);
            }
            // 有从服务器获取到数据，替换数据
            mAdapter.replaceAll(list);
            //mAdapter.notifyDataSetChanged();
            mLoadMoreRecyclerView.setAdapter(mAdapter);
            showData();
        } else {
            // 没有从服务器获取到数据
            mAdapter.clear();
            showEmpty(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLoading();
                    refreshData();
                }
            });
        }
    }

    private void loadMordDataSuccess(List<T> list) {
        mLoadMoreRecyclerView.notifyMoreFinish(true);
        if (list != null && list.size() > 0) {
            if (list.size() < mPageSize) {
                // 获取这页数据时，没有返回mRows条
                mLoadMoreRecyclerView.setAutoLoadMoreEnable(false);
            } else {
                mLoadMoreRecyclerView.setAutoLoadMoreEnable(true);
            }
            // 有从服务器获取到数据，添加数据
            mAdapter.addAll(list);
        } else {
            // 没有从服务器获取到数据
            mLoadMoreRecyclerView.setAutoLoadMoreEnable(false);
        }
    }

    private void refreshDataFail() {
        mBGARefreshLayout.endLoadingMore();
        showNetworkError(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                refreshData();
            }
        });
    }

    private void loadMordDataFail() {
        mLoadMoreRecyclerView.notifyMoreFinish(true);
        AbToastUtils.show("服务器异常，请稍候再试");
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        refreshData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }


}
