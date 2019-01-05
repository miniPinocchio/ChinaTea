package com.work.app.ztea.ui.activity.mine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dream.library.eventbus.EventCenter;
import com.dream.library.utils.AbGsonUtil;
import com.dream.library.utils.annotation.annotation.ViewInject;
import com.dream.library.utils.annotation.annotation.event.OnClick;
import com.loopj.android.http.TextHttpResponseHandler;
import com.work.app.ztea.APP;
import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseActivity;
import com.work.app.ztea.entity.BaseEntity;
import com.work.app.ztea.entity.LoginEntity;
import com.work.app.ztea.http.Api;
import com.work.app.ztea.ui.MainActivity;
import com.work.app.ztea.ui.activity.LoginActivity;
import com.work.app.ztea.utils.UserService;

import org.greenrobot.eventbus.EventBus;

import cz.msebera.android.httpclient.Header;

/**
 * 修改密码
 */
public class EditPwdActivity extends BaseActivity {

    @ViewInject(R.id.et_old_pwd)
    EditText et_old_pwd;
    @ViewInject(R.id.et_new_pwd)
    EditText et_new_pwd;
    @ViewInject(R.id.et_new_pwd_again)
    EditText et_new_pwd_again;


    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_edit_pwd;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        setTopTitle("修改密码");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick({R.id.tv_save})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_save://修改密码
                editPwd();
                break;
        }
    }

    private void editPwd(){
        String mark1 = et_old_pwd.getText().toString().trim();
        String mark2 = et_new_pwd.getText().toString().trim();
        String mark3 = et_new_pwd_again.getText().toString().trim();
        if (TextUtils.isEmpty(mark1)){
            showToast("请输入旧密码");
            return;
        }
        if (TextUtils.isEmpty(mark2)){
            showToast("请输入新密码");
            return;
        }
        if (TextUtils.isEmpty(mark3)){
            showToast("请再次输入新密码");
            return;
        }
        if (!TextUtils.equals(mark2,mark3)){
            showToast("新密码输入不一致");
            return;
        }
        String token = UserService.getUserInfo().getToken();
        Api.editpwd(token,mark1,mark2,mark3, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                hideProgressDialog();
                Log.d("params","feedBack = "+responseString);
                BaseEntity mPlanSortEntity = AbGsonUtil.json2Bean(responseString, BaseEntity.class);
                if (mPlanSortEntity.isOk()) {
                    showToast("密码修改成功，请重新登录！");
                    EventBus.getDefault().post(new EventCenter(Api.TOKEN_OUT));
                    UserService.saveUserInfo(new LoginEntity.Login());
                    startActivity(new Intent(EditPwdActivity.this, LoginActivity.class));
                    APP.getContext().clearActivity();
                    finish();
                }else {
                    showToast(mPlanSortEntity.msg);
                }
            }
        });
    }
}
