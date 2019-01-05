package com.work.app.ztea.ui.activity.mine;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dream.library.utils.annotation.annotation.ViewInject;
import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseActivity;
import com.work.app.ztea.ui.fragment.mine.CustomProxyFragment;
import com.work.app.ztea.ui.fragment.mine.VipProxyFragment;

/**
 * @author huiliu
 */
public class MyOrderActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    @ViewInject(R.id.rg_proxy_setting)
    RadioGroup mRadioGroup;
    @ViewInject(R.id.rb_proxy_order)
    RadioButton mProxyOrder;
    @ViewInject(R.id.rb_custom_order)
    RadioButton mCustomOrder;
    @ViewInject(R.id.rb_custom_all_order)
    RadioButton mAllOrder;

    private int mIndex;
    private Fragment[] mFragments;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_my_order;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        setTopTitle("我的征订");
        mRadioGroup.setOnCheckedChangeListener(this);
        initFragment();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }


    private void initFragment() {
        CustomProxyFragment customProxyFragment = new CustomProxyFragment();
        VipProxyFragment vipProxyFragment = new VipProxyFragment();

        //添加到数组
        mFragments = new Fragment[]{vipProxyFragment, customProxyFragment};

        //开启事务

        FragmentTransaction ft =
                getSupportFragmentManager().beginTransaction();

        //添加首页
        ft.add(R.id.fl_container, vipProxyFragment).commit();

        setIndexSelected(0);
    }


    private void setIndexSelected(int index) {
        if (mIndex == index) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();


        //隐藏
        ft.hide(mFragments[mIndex]);
        //判断是否添加
        if (!mFragments[index].isAdded()) {
            ft.add(R.id.fl_container, mFragments[index]).show(mFragments[index]);
        } else {
            ft.show(mFragments[index]);
        }

        ft.commit();
        //再次赋值
        mIndex = index;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_proxy_order:
                setIndexSelected(0);
                break;
            case R.id.rb_custom_order:
                setIndexSelected(1);
                break;
            case R.id.rb_custom_all_order:
                startAct(StatisticsActivity.class);
                break;
            default:
                break;
        }
    }
}
