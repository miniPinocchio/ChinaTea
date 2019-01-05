package com.work.app.ztea.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;

import com.dream.library.utils.AbToastUtils;
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
public abstract class BaseListViewPullRefreshFragment<T> extends BaseFragment implements BGARefreshLayout.BGARefreshLayoutDelegate {

    protected static final int STATE_NONE = 0;
    protected static final int STATE_REFRESH = 1;
    protected static final int STATE_LOADMORE = 2;
    protected int mState = STATE_NONE;

    public BGARefreshLayout mBGARefreshLayout;
    public RecyclerView mListView;

    protected RecyclerAdapter<T> mAdapter;

    protected int mPage = 1;
    protected int mPageSize = 20;
    private BGARefreshViewHolder mBGARefreshViewHolder;

    @Override
    protected View getLoadingTargetView() {
        return getView().findViewById(R.id.mBGARefreshLayout);
    }

    protected void initPullRefreshAndLoadMore(View view) {
        mBGARefreshLayout = (BGARefreshLayout) view.findViewById(R.id.mBGARefreshLayout);
        mListView = (RecyclerView) view.findViewById(R.id.mListView);
        showLoading();
        // 下拉刷新
        if (mBGARefreshLayout != null) {
            mBGARefreshViewHolder = new BGANormalRefreshViewHolder(mContext,isLoadingMoreEnabled());
            //设置刷新样式
            mBGARefreshLayout.setRefreshViewHolder(mBGARefreshViewHolder);

            mBGARefreshLayout.setDelegate(this);

        }

        initAdapter();
        mListView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        mListView.setAdapter(mAdapter);
        refreshData();
        //mBGARefreshLayout.autoRefresh();
    }

    public void refreshData() {
        if (mState == STATE_NONE) {
            mState = STATE_REFRESH;
            mPage = 1;
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

    protected abstract boolean isLoadingMoreEnabled();

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
                //mLoadMoreListView.setCanLoadMore(false);
                // mAdapter.notifyDataSetChanged();
            } else {
                //mLoadMoreListView.setCanLoadMore(true);
            }
            // 有从服务器获取到数据，替换数据
            mAdapter.replaceAll(list);
            mAdapter.notifyDataSetChanged();
            // 有数据隐藏EmptyLayout
            showData();

        } else {
            // 没有从服务器获取到数据
            mAdapter.clear();
            showEmpty(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLoading();
                    refreshData();
                }
            });

        }
    }

    private void loadMordDataSuccess(List<T> list) {
        //mLoadMoreListView.onLoadMoreComplete();
        if (list != null && list.size() > 0) {
            if (list.size() < mPageSize) {
                // 获取这页数据时，没有返回mRows条
                // mLoadMoreListView.setCanLoadMore(false);
                // mAdapter.notifyDataSetChanged();
            } else {
                //mLoadMoreListView.setCanLoadMore(true);

            }
            // 有从服务器获取到数据，添加数据
            mAdapter.addAll(list);
            mAdapter.notifyDataSetChanged();
        } else {
            mPage -= 1;
            showToast("没有更多数据了");
            mAdapter.notifyDataSetChanged();
            // 没有从服务器获取到数据
            //mLoadMoreListView.setCanLoadMore(false);
        }
    }

    private void refreshDataFail() {
        mBGARefreshLayout.endLoadingMore();
        showNetworkError(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                refreshData();
            }
        });
    }

    private void loadMordDataFail() {
        mBGARefreshLayout.endLoadingMore();
        mBGARefreshLayout.endRefreshing();
        //mLoadMoreListView.onLoadMoreComplete();
        AbToastUtils.show("服务器异常，请稍候再试");
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        refreshData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        loadMoreData();
        return false;
    }
}
