package com.work.app.ztea.ui.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dream.library.utils.AbGsonUtil;
import com.dream.library.utils.annotation.annotation.ViewInject;
import com.dream.library.utils.annotation.annotation.event.OnClick;
import com.loopj.android.http.TextHttpResponseHandler;
import com.work.app.ztea.APP;
import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseActivity;
import com.work.app.ztea.entity.BaseEntity;
import com.work.app.ztea.entity.CodeEntity;
import com.work.app.ztea.entity.LoginEntity;
import com.work.app.ztea.http.Api;

import cz.msebera.android.httpclient.Header;

/**
 * 找回密码
 */
public class ForgotPwdActivity extends BaseActivity {

    @ViewInject(R.id.et_phone)
    EditText et_phone;

    @ViewInject(R.id.et_code)
    EditText et_code;

    @ViewInject(R.id.tv_get_code)
    TextView tvGetCode;

    @ViewInject(R.id.et_password)
    EditText et_password;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_forgot_pwd;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        setTopTitle("找回密码");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick({R.id.tv_commit,R.id.tv_get_code})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_commit://完成
                final String phone = et_phone.getText().toString().trim();
                final String code = et_code.getText().toString().trim();
                final String password = et_password.getText().toString().trim();

                if (TextUtils.isEmpty(phone)) {
                    showToast(et_phone.getHint().toString().trim());
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    showToast(et_code.getHint().toString().trim());
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    showToast(et_password.getHint().toString().trim());
                    return;
                }
                showProgressDialog();
                Api.forgotPwd(phone,code,password,password, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        hideProgressDialog();
                        showToast("请求失败");
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Log.i("params", "忘记密码"+responseString);
                        hideProgressDialog();
                        BaseEntity loginEntity = AbGsonUtil.json2Bean(responseString, BaseEntity.class);
                        if (loginEntity.isOk()) {
                            finish();
                        } else {
                            showToast(loginEntity.msg);
                        }
                    }
                });
                break;
            case R.id.tv_get_code://获取验证码
                String phoneCode = et_phone.getText().toString().trim();
                if (TextUtils.isEmpty(phoneCode)){
                    showToast("请输入手机号");
                    return;
                }
                getCode();
                getCodeNet();
                break;
        }
    }

    /**
     * 验证码
     */
    private void getCode() {
        Message msg = handler.obtainMessage();
        msg.what = 1002;
        msg.arg1 = 59;
        msg.sendToTarget();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1002:
                    int second = msg.arg1;
                    tvGetCode.setText(second + "s");
                    second -= 1;
                    if (second < 1) {
                        tvGetCode.setText("获取验证码");
                        tvGetCode.setEnabled(true);
                        return;
                    } else {
                        tvGetCode.setEnabled(false);
                    }
                    Message secondMsg = obtainMessage();
                    secondMsg.what = 1002;
                    secondMsg.arg1 = second;
                    sendMessageDelayed(secondMsg, 1000);
                    break;
            }
        }
    };

    /**
     * 获取验证码
     */
    private void getCodeNet(){
        String phoneCode = et_phone.getText().toString().trim();
        Api.sendCode("2",phoneCode,new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
                showToast("请求失败");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i("params", "验证码"+responseString);
                hideProgressDialog();
                CodeEntity entity = AbGsonUtil.json2Bean(responseString, CodeEntity.class);
                if (entity.isOk()) {
                    showToast("验证码发送成功");
                    String code_time = entity.data.code_time;
                } else {
                    showToast(entity.msg);
                }
            }
        });
    }
}
