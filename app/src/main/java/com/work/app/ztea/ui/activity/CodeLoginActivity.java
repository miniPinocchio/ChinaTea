package com.work.app.ztea.ui.activity;

import android.content.Intent;
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
import com.work.app.ztea.entity.CodeEntity;
import com.work.app.ztea.entity.LoginEntity;
import com.work.app.ztea.http.Api;
import com.work.app.ztea.ui.MainActivity;

import cz.msebera.android.httpclient.Header;

/**
 * 验证码登录
 */
public class CodeLoginActivity extends BaseActivity {

    @ViewInject(R.id.et_phone)
    EditText et_phone;

    @ViewInject(R.id.et_code)
    EditText et_code;

    @ViewInject(R.id.tv_get_code)
    TextView tvGetCode;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_code_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        setTopTitle("验证码登陆");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick({R.id.tv_login,R.id.tv_get_code})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login://登录
                final String username = et_phone.getText().toString().trim();
                final String code = et_code.getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    showToast(et_phone.getHint().toString().trim());
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    showToast(et_code.getHint().toString().trim());
                    return;
                }

                showProgressDialog();
                Api.codeLogin(username, code, new TextHttpResponseHandler() {
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
                            APP.setToken(loginEntity.data.getToken());
                            readyGo(MainActivity.class);
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
        Api.sendCode("3",phoneCode,new TextHttpResponseHandler() {
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
                    String code_time = entity.data.code_time;
                } else {
                    showToast(entity.msg);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
