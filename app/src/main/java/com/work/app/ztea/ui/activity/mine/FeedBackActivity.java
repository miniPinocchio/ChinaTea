package com.work.app.ztea.ui.activity.mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.dream.library.utils.AbGsonUtil;
import com.dream.library.utils.annotation.annotation.ViewInject;
import com.dream.library.utils.annotation.annotation.event.OnClick;
import com.loopj.android.http.TextHttpResponseHandler;
import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseActivity;
import com.work.app.ztea.entity.BaseEntity;
import com.work.app.ztea.entity.MyAddressListEntity;
import com.work.app.ztea.http.Api;
import com.work.app.ztea.utils.UserService;

import cz.msebera.android.httpclient.Header;

/**
 * 意见反馈
 */
public class FeedBackActivity extends BaseActivity {

    @ViewInject(R.id.et_remark_1)
    EditText et_remark_1;
    @ViewInject(R.id.et_remark_2)
    EditText et_remark_2;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        setTopTitle("意见与反馈");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick({R.id.tv_save})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_save://提交
                feedBack();
                break;
        }
    }

    private void feedBack(){
        String mark1 = et_remark_1.getText().toString().trim();
        String mark2 = et_remark_2.getText().toString().trim();
        if (TextUtils.isEmpty(mark1)){
            showToast("请输入简述内容");
            return;
        }
        if (TextUtils.isEmpty(mark2)){
            showToast("请输入详细内容");
            return;
        }
        String token = UserService.getUserInfo().getToken();
        Api.feedBack(token,mark1,mark2, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                hideProgressDialog();
                Log.d("params","feedBack = "+responseString);
                BaseEntity mPlanSortEntity = AbGsonUtil.json2Bean(responseString, BaseEntity.class);
                showToast(mPlanSortEntity.msg);
                if (mPlanSortEntity.isOk()) {
                    et_remark_1.setText("");
                    et_remark_2.setText("");
                }
            }
        });
    }
}
