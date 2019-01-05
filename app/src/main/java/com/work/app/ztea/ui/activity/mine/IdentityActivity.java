package com.work.app.ztea.ui.activity.mine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dream.library.utils.annotation.annotation.event.OnClick;
import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseActivity;

/**
 * 身份认证
 */
public class IdentityActivity extends BaseActivity {


    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_identity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        setTopTitle("身份认证");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick({R.id.layout_set_1,R.id.layout_set_2})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.layout_set_1://身份证认证
                startActivity(new Intent(this,ShimingActivity.class));
                break;
            case R.id.layout_set_2://银行卡绑定
                startActivity(new Intent(this,BindBankActivity.class));
                break;

        }
    }
}
