package com.work.app.ztea.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.dream.library.eventbus.EventCenter;
import com.dream.library.utils.AbGsonUtil;
import com.dream.library.utils.annotation.annotation.ViewInject;
import com.dream.library.utils.annotation.annotation.event.OnClick;
import com.loopj.android.http.TextHttpResponseHandler;
import com.work.app.ztea.APP;
import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseActivity;
import com.work.app.ztea.entity.LoginEntity;
import com.work.app.ztea.http.Api;
import com.work.app.ztea.ui.MainActivity;
import com.work.app.ztea.utils.UserService;

import org.greenrobot.eventbus.EventBus;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends BaseActivity {

    @ViewInject(R.id.et_phone)
    EditText et_phone;

    @ViewInject(R.id.et_password)
    EditText et_password;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTopTitle("登录");
        setRightTitle("注册");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        LoginEntity.Login userInfo = UserService.getUserInfo();
        if (userInfo != null && !TextUtils.isEmpty(userInfo.getToken())) {
            et_phone.setText(userInfo.getMobile());
        }
    }

    @OnClick({R.id.tv_login,R.id.tv_right,R.id.tv_code_login,R.id.tv_forgot_pwd})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login://登录
                final String username = et_phone.getText().toString().trim();
                final String password = et_password.getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    showToast(et_phone.getHint().toString().trim());
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    showToast(et_password.getHint().toString().trim());
                    return;
                }

                showProgressDialog();
                Api.loginForPwd(username, password, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        hideProgressDialog();
                        showToast("请求失败");
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Log.i("params", "登录数据"+responseString);
                        hideProgressDialog();
                        LoginEntity loginEntity = AbGsonUtil.json2Bean(responseString, LoginEntity.class);
                        if (loginEntity.isOk()) {
                            UserService.saveUserInfo(loginEntity.data);
                            EventBus.getDefault().post(new EventCenter(Api.RECHARGE_OK));
                            finish();
                        } else {
                            showToast(loginEntity.msg);
                        }
                    }
                });
                break;
            case R.id.tv_right://注册
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.tv_code_login://验证码登录
                startActivity(new Intent(this,CodeLoginActivity.class));
                break;
            case R.id.tv_forgot_pwd://忘记密码
                startActivity(new Intent(this,ForgotPwdActivity.class));
                break;
        }
    }
}
