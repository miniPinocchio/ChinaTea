package com.work.app.ztea.ui.activity.mine;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dream.library.eventbus.EventCenter;
import com.dream.library.utils.AbGsonUtil;
import com.dream.library.utils.annotation.annotation.ViewInject;
import com.dream.library.utils.annotation.annotation.event.OnClick;
import com.loopj.android.http.TextHttpResponseHandler;
import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseActivity;
import com.work.app.ztea.entity.IdentifyEntity;
import com.work.app.ztea.entity.LoginEntity;
import com.work.app.ztea.entity.MineHomeEntity;
import com.work.app.ztea.entity.RegisterServerEntity;
import com.work.app.ztea.http.Api;
import com.work.app.ztea.ui.activity.WebViewActivity;
import com.work.app.ztea.utils.UserService;

import org.greenrobot.eventbus.EventBus;

import cz.msebera.android.httpclient.Header;

import static com.work.app.ztea.utils.StringUtil.idCardReplaceWithStar;

/**
 * 实名认证
 */
public class ShimingActivity extends BaseActivity {

    @ViewInject(R.id.et_name)
    EditText et_name;
    @ViewInject(R.id.et_id_card)
    EditText et_id_card;

    @ViewInject(R.id.tv_save)
    TextView tv_save;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_shiming;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        setTopTitle("实名认证");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        getUserInfo();
    }

    /**
     * 个人中心首页
     */
    private void getUserInfo(){
        LoginEntity.Login userInfo = UserService.getUserInfo();
        showProgressDialog();
        Api.getMineInfo(userInfo.getToken(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                hideProgressDialog();
                Log.d("params","UserInfo = "+responseString);
                MineHomeEntity detailsEntity = AbGsonUtil.json2Bean(responseString, MineHomeEntity.class);
                if (detailsEntity.isOk() && detailsEntity.data != null) {
                    MineHomeEntity.Mine mine = detailsEntity.data;
                    et_name.setText(TextUtils.isEmpty(mine.getIdcard())?"":mine.getName());
                    tv_save.setVisibility(!TextUtils.isEmpty(mine.getIdcard())?View.GONE:View.VISIBLE);
                    String idCard = "";
                    if (!TextUtils.isEmpty(mine.getIdcard())){
                        idCard = idCardReplaceWithStar(mine.getIdcard());
                        et_name.setEnabled(false);
                        et_id_card.setEnabled(false);
                    }
                    et_id_card.setText(idCard);
                }
            }
        });
    }




    @OnClick({R.id.tv_save})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_save://保存
                shiming();
                break;
        }
    }

    /**
     * 实名认证
     */
    private void shiming(){
        String name = et_name.toString().trim();
        String id_card = et_id_card.toString().trim();
        if (TextUtils.isEmpty(name)){
            showToast(et_name.getHint().toString());
            return;
        }
        if (TextUtils.isEmpty(id_card)){
            showToast(et_id_card.getHint().toString());
            return;
        }
        String token = UserService.getUserInfo().getToken();
        showProgressDialog();
        Api.shiming(token,id_card,name,new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
                showToast("请求失败");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i("params", "实名认证"+responseString);
                hideProgressDialog();
                IdentifyEntity loginEntity = AbGsonUtil.json2Bean(responseString, IdentifyEntity.class);
                if (loginEntity.isOk()) {
                    showToast(loginEntity.data.result);
                    if (TextUtils.equals(loginEntity.data.result,"身份证号码和真实姓名一致")){
                        getUserInfo();
                        EventBus.getDefault().post(new EventCenter(Api.RECHARGE_OK));
                    }
                } else {
                    showToast(loginEntity.msg);
                }
            }
        });
    }
}
