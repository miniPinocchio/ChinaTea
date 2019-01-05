package com.work.app.ztea.ui.activity.mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dream.library.utils.AbGsonUtil;
import com.dream.library.utils.annotation.annotation.ViewInject;
import com.dream.library.utils.annotation.annotation.event.OnClick;
import com.loopj.android.http.TextHttpResponseHandler;
import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseActivity;
import com.work.app.ztea.entity.MyOrderListEntity;
import com.work.app.ztea.entity.QrCodeEntity;
import com.work.app.ztea.http.Api;
import com.work.app.ztea.utils.UserService;
import com.work.app.ztea.wxapi.WxUtils;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cz.msebera.android.httpclient.Header;

/**
 * 我的推广码
 */
public class MineCodeActivity extends BaseActivity {

    @ViewInject(R.id.iv_code_img)
    ImageView iv_code_img;

    private QrCodeEntity.QrCode qrCodeBean;


    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_mine_code;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        setTopTitle("我的推广码");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        getShareContent();
    }

    @OnClick({R.id.tv_share})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_share://分享
                WxUtils.getInstance().showShare(this,qrCodeBean.getImage(),qrCodeBean.getShare(),qrCodeBean.getTitle(),qrCodeBean.getContent());
                break;
        }
    }

    private void getShareContent(){
        String token = UserService.getUserInfo().getToken();
        Api.myQrCode(token,  new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                hideProgressDialog();
                Log.d("params","QrCode = "+responseString);
                QrCodeEntity codeEntity = AbGsonUtil.json2Bean(responseString, QrCodeEntity.class);
                if (codeEntity != null && codeEntity.isOk()) {
                    qrCodeBean = codeEntity.data;
                    Glide.with(mContext)
                            .load(qrCodeBean.getImage())
//                            .error(R.drawable.banner)
                            .into(iv_code_img);
                }
            }
        });
    }
}
