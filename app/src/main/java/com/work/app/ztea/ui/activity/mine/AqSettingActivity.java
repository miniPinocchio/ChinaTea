package com.work.app.ztea.ui.activity.mine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dream.library.utils.annotation.annotation.ViewInject;
import com.dream.library.utils.annotation.annotation.event.OnClick;
import com.work.app.ztea.APP;
import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseActivity;
import com.work.app.ztea.entity.LoginEntity;
import com.work.app.ztea.utils.UserService;

/**
 * 安全设置
 */
public class AqSettingActivity extends BaseActivity {

    @ViewInject(R.id.tv_phone)
    TextView tv_phone;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_aq_setting;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        APP.getContext().addActivity(this);
        setVisibleLeft(true);
        setTopTitle("安全设置");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        LoginEntity.Login userInfo = UserService.getUserInfo();
        if (userInfo != null) {
            tv_phone.setText(userInfo.getMobile());
        }
    }

    @OnClick({R.id.layout_set_1,R.id.layout_set_2})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.layout_set_1://修改密码
                startActivity(new Intent(this,EditPwdActivity.class));
                break;
            case R.id.layout_set_2://手机号码
                startActivity(new Intent(this,EditMobileActivity.class));
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        APP.getContext().removeActivity(this);
    }
}
