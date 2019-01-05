package com.work.app.ztea.ui.activity.mine;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dream.library.eventbus.EventCenter;
import com.dream.library.utils.AbGsonUtil;
import com.dream.library.utils.annotation.annotation.ViewInject;
import com.dream.library.utils.annotation.annotation.event.OnClick;
import com.loopj.android.http.TextHttpResponseHandler;
import com.work.app.ztea.APP;
import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseActivity;
import com.work.app.ztea.entity.LoginEntity;
import com.work.app.ztea.entity.RegisterServerEntity;
import com.work.app.ztea.http.Api;
import com.work.app.ztea.ui.MainActivity;
import com.work.app.ztea.ui.activity.WebViewActivity;
import com.work.app.ztea.utils.CustomUtils;
import com.work.app.ztea.utils.UserService;

import org.greenrobot.eventbus.EventBus;

import cz.msebera.android.httpclient.Header;

/**
 * 设置
 */
public class SettingActivity extends BaseActivity {

    @ViewInject(R.id.tv_version)
    TextView tv_version;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        APP.getContext().addActivity(this);
        setVisibleLeft(true);
        setTopTitle("设置");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String version = CustomUtils.getVersionName(this);
        tv_version.setText("v"+version);
    }

    @OnClick({R.id.layout_set_1,R.id.layout_set_2,R.id.layout_set_3,R.id.layout_set_4,R.id.layout_set_5})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.layout_set_1://安全设置
                startActivity(new Intent(this,AqSettingActivity.class));
                break;
            case R.id.layout_set_2://用户协议
                getUserServer();
                break;
            case R.id.layout_set_3://意见反馈
                startActivity(new Intent(this,FeedBackActivity.class));
                break;
            case R.id.layout_set_4://清除缓存

                break;
            case R.id.layout_set_5://退出登录
                exitLogin();
                break;
        }
    }

    /**
     * 获取用户协议
     */
    private void getUserServer(){
        Api.getUserSite(new TextHttpResponseHandler() {
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
                    Bundle bundle = new Bundle();
                    bundle.putString(WebViewActivity.BUNDLE_KEY_TITLE, loginEntity.data.getTitle());
                    bundle.putString(WebViewActivity.BUNDLE_KEY_URL, loginEntity.data.getLink());
                    readyGo(WebViewActivity.class, bundle);
                } else {
                    showToast(loginEntity.msg);
                }
            }
        });
    }

    // 是否确定退出登录
    private void exitLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("温馨提示");
        builder.setMessage("确定退出登录？");
        builder.setPositiveButton("确定", new ExitLoginImpl());
        builder.setNegativeButton("取消", null);
        Dialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * 退出登录
     */
    private class ExitLoginImpl implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            EventBus.getDefault().post(new EventCenter(Api.TOKEN_OUT));
            UserService.saveUserInfo(new LoginEntity.Login());
            startActivity(new Intent(SettingActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        APP.getContext().removeActivity(this);
    }
}
