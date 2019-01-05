package com.work.app.ztea.ui.fragment.mine;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dream.library.utils.annotation.annotation.ViewInject;
import com.work.app.ztea.R;
import com.work.app.ztea.adapter.MyVipProxyAdapter;
import com.work.app.ztea.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author huiliu
 */
public class CustomProxyFragment extends BaseFragment implements View.OnClickListener {

    @ViewInject(R.id.rv_custom_proxy)
    RecyclerView mRvCustomProxy;

    private List<String> mDatas;
    private MyVipProxyAdapter mVipProxyAdapter;

    public CustomProxyFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_custom_proxy;
    }

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        initRecycler();
    }

    private void initRecycler() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mDatas.add("");
        }
        mVipProxyAdapter = new MyVipProxyAdapter(getActivity(), mDatas, this);

        mRvCustomProxy.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRvCustomProxy.setAdapter(mVipProxyAdapter);
    }

    @Override
    protected void initData(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {

    }
}
