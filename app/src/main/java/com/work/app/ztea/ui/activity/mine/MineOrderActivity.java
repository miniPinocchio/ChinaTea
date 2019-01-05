package com.work.app.ztea.ui.activity.mine;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.dream.library.utils.annotation.annotation.ViewInject;
import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseActivity;
import com.work.app.ztea.library.ForumFragmentAdapter;
import com.work.app.ztea.ui.fragment.CollegeFragment;
import com.work.app.ztea.ui.fragment.HomeFragment;
import com.work.app.ztea.ui.fragment.MineFragment;
import com.work.app.ztea.ui.fragment.OrderChildFragment;
import com.work.app.ztea.ui.fragment.ShoppingFragment;
import com.work.app.ztea.utils.LayoutUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 我的订单
 */
public class MineOrderActivity extends BaseActivity implements TabLayout.OnTabSelectedListener {

    @ViewInject(R.id.tbLayout)
    TabLayout tabLayout;
    @ViewInject(R.id.view_pager)
    ViewPager viewPager;

    //动态添加fragment
    private List<Fragment> mFragments;
    //显示tab
    private List<String> mStringList;

    private ForumFragmentAdapter mAdapter;
    private int page;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_mine_order;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        setTopTitle("我的订单");
        page = getIntent().getIntExtra("page", 0);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initTab();
    }

    private void initTab() {
        mFragments = new ArrayList<>();
        mStringList = new ArrayList<>();
        addFragment();
        mAdapter = new ForumFragmentAdapter(getSupportFragmentManager(), mFragments, mStringList);
        viewPager.setAdapter(mAdapter);

        tabLayout.addTab(tabLayout.newTab().setText("全部"));
        tabLayout.addTab(tabLayout.newTab().setText("认购单"));
        tabLayout.addTab(tabLayout.newTab().setText("待支付"));
        tabLayout.addTab(tabLayout.newTab().setText("待发货"));
        tabLayout.addTab(tabLayout.newTab().setText("待收货"));
        tabLayout.addTab(tabLayout.newTab().setText("待评价"));
        LayoutUtils.reflex(tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.MODE_SCROLLABLE);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.red));
        tabLayout.setTabTextColors(getResources().getColor(R.color.color_address_black), getResources().getColor(R.color.red));
        tabLayout.addOnTabSelectedListener(this);

        viewPager.setCurrentItem(page);
    }

    private void addFragment(){
        mStringList.add("全部");
        mStringList.add("认购单");
        mStringList.add("待支付");
        mStringList.add("待发货");
        mStringList.add("待收货");
        mStringList.add("待评价");
        for (int i = 0; i < mStringList.size(); i++) {
            OrderChildFragment childFragment = new OrderChildFragment();
            mFragments.add(childFragment);
            Bundle bundle = new Bundle();
            switch (i){
                case 0:
                    bundle.putInt("point", 6);
                    break;
                case 1:
                    bundle.putInt("point", 7);
                    break;
                case 2:
                    bundle.putInt("point", 1);
                    break;
                case 3:
                    bundle.putInt("point", 2);
                    break;
                case 4:
                    bundle.putInt("point", 3);
                    break;
                case 5:
                    bundle.putInt("point", 4);
                    break;
            }
            childFragment.setArguments(bundle);
        }
//        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
