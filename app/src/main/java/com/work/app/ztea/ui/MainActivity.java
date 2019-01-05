package com.work.app.ztea.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dream.library.eventbus.EventCenter;
import com.dream.library.utils.annotation.annotation.ViewInject;
import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseActivity;
import com.work.app.ztea.entity.LoginEntity;
import com.work.app.ztea.http.Api;
import com.work.app.ztea.ui.activity.LoginActivity;
import com.work.app.ztea.ui.activity.goods.GoodsBalanceActivity;
import com.work.app.ztea.ui.activity.goods.PayResultActivity;
import com.work.app.ztea.ui.activity.mine.MineOrderActivity;
import com.work.app.ztea.ui.fragment.CollegeFragment;
import com.work.app.ztea.ui.fragment.HomeFragment;
import com.work.app.ztea.ui.fragment.MineFragment;
import com.work.app.ztea.ui.fragment.ShoppingFragment;
import com.work.app.ztea.utils.UserService;
import com.work.app.ztea.widget.MyViewPager;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MainActivity extends BaseActivity {

    private Map<Integer, Fragment> mFragmentMap;

    @ViewInject(R.id.fl)
    public MyViewPager viewPager;
    @ViewInject(R.id.rb_group)
    public RadioGroup mRbGroup;
    @ViewInject(R.id.rb_home)
    public RadioButton rb_home;
    @ViewInject(R.id.rb_collection)
    public RadioButton rb_shop;
    @ViewInject(R.id.rb_tool)
    public RadioButton rb_college;
    @ViewInject(R.id.rb_me)
    public RadioButton rb_me;

    private int lastCheckedId;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mFragmentMap = new HashMap<>();
        showFragment(R.id.rb_home);

        mRbGroup.check(R.id.rb_home);
        lastCheckedId = R.id.rb_home;
        mRbGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_me){
                    LoginEntity.Login userInfo = UserService.getUserInfo();
                    if (userInfo == null || TextUtils.isEmpty(userInfo.getToken())){
                        rb_me.setChecked(false);
                        group.check(lastCheckedId);
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        return;
                    }
                }
                lastCheckedId = checkedId;
                showFragment(checkedId);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initListener();
    }

    private void initListener() {
        rb_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginEntity.Login userInfo = UserService.getUserInfo();
                if (userInfo == null || TextUtils.isEmpty(userInfo.getToken())){
                    return;
                }
                EventBus.getDefault().post(new EventCenter(Api.RECHARGE_OK));
            }
        });
    }


    /**
     * 设置显示 Fragment
     *
     * @param position
     */
    public void showFragment(int position) {
        Fragment fragment = mFragmentMap.get(position);
        if (fragment == null) {
            switch (position) {
                case R.id.rb_home:
                    fragment = new HomeFragment();
                    break;
                case R.id.rb_collection://商城
                    fragment = new ShoppingFragment();
                    break;
                case R.id.rb_tool://学院
                    fragment = new CollegeFragment();
                    break;
                case R.id.rb_me:
                    fragment = new MineFragment();
                    break;
            }
            mFragmentMap.put(position, fragment);
            getSupportFragmentManager().beginTransaction().add(R.id.fl, fragment).commit();
        }

        Set<Integer> integers = mFragmentMap.keySet();
        for (Integer integer : integers) {
            if (position == integer) {
                getSupportFragmentManager().beginTransaction()
                        .show(mFragmentMap.get(integer))
                        .commitAllowingStateLoss();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .hide(mFragmentMap.get(integer))
                        .commitAllowingStateLoss();
            }
        }
    }

    private boolean isTiaozhuan = false;

    @Override
    protected void onEventComing(EventCenter eventCenter) {
        super.onEventComing(eventCenter);
        if (eventCenter.getEventCode() == Api.OPEN_SHOP) {
            int data = (int) eventCenter.getData();
            if (data== 100){
                return;
            }
            isTiaozhuan = true;
        }else if (eventCenter.getEventCode() == Api.TOKEN_OUT){
            mRbGroup.check(R.id.rb_home);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isTiaozhuan){
            isTiaozhuan = false;
            startActivity(new Intent(this, MineOrderActivity.class).putExtra("page",3));
        }
    }

    @Override
    protected boolean isBindEventBus() {
        return true;
    }
}
