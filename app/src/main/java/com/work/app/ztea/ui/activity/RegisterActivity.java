package com.work.app.ztea.ui.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
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
import com.work.app.ztea.entity.RegisterServerEntity;
import com.work.app.ztea.http.Api;
import com.work.app.ztea.ui.MainActivity;

import cz.msebera.android.httpclient.Header;

/**
 * 注册
 */
public class RegisterActivity extends BaseActivity {

    @ViewInject(R.id.et_name)
    EditText et_name;

    @ViewInject(R.id.et_phone)
    EditText et_phone;

    @ViewInject(R.id.et_code)
    EditText et_code;

    @ViewInject(R.id.tv_get_code)
    TextView tvGetCode;

    @ViewInject(R.id.et_password)
    EditText et_password;

    @ViewInject(R.id.et_password_again)
    EditText et_password_again;

    @ViewInject(R.id.cb_remember)
    CheckBox cb_remember;

    //用户协议
    private RegisterServerEntity.Server server;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        setTopTitle("注册");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        getRegisterServer();
    }

    @OnClick({R.id.tv_commit,R.id.tv_get_code,R.id.tv_xy})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_commit://注册
                final String username = et_name.getText().toString().trim();
                final String phone = et_phone.getText().toString().trim();
                final String code = et_code.getText().toString().trim();
                final String password = et_password.getText().toString().trim();
                final String passwordAgain = et_password_again.getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    showToast(et_name.getHint().toString().trim());
                    return;
                }
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
                if (TextUtils.isEmpty(passwordAgain)) {
                    showToast(et_password_again.getHint().toString().trim());
                    return;
                }
                if (!TextUtils.equals(password,passwordAgain)){
                    showToast("两次密码输入不一致");
                    return;
                }
                if (!cb_remember.isChecked()){
                    showToast("请同意注册协议");
                    return;
                }
                showProgressDialog();
                Api.register("",password,passwordAgain,phone,username, code, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        hideProgressDialog();
                        showToast("请求失败");
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Log.i("params", "注册数据"+responseString);
                        hideProgressDialog();
                        LoginEntity loginEntity = AbGsonUtil.json2Bean(responseString, LoginEntity.class);
                        if (loginEntity.isOk()) {
                            showToast("注册成功");
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
            case R.id.tv_xy://用户协议
                if (server == null){
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString(WebViewActivity.BUNDLE_KEY_TITLE, server.getTitle());
                bundle.putString(WebViewActivity.BUNDLE_KEY_URL, server.getLink());
                readyGo(WebViewActivity.class, bundle);
                break;
        }
    }

    /**
     * 获取用户协议
     */
    private void getRegisterServer(){
        Api.getRegisterSite(new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
                showToast("请求失败");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i("params", "用户协议"+responseString);
                hideProgressDialog();
                RegisterServerEntity loginEntity = AbGsonUtil.json2Bean(responseString, RegisterServerEntity.class);
                if (loginEntity.isOk()) {
                    server = loginEntity.data;
                } else {
                    showToast(loginEntity.msg);
                }
            }
        });
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
        Api.sendCode("1",phoneCode,new TextHttpResponseHandler() {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
