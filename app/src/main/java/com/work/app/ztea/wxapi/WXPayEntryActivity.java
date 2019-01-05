package com.work.app.ztea.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.dream.library.eventbus.EventCenter;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.work.app.ztea.http.Api;
import com.work.app.ztea.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Administrator on 2017/12/1.
 * 微信支付回调页
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, WxUtils.WXAppId);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        Toast.makeText(getApplicationContext(), "onReq", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResp(BaseResp resp) {
        int code = resp.errCode;

        if (code == 0) {
            //显示充值成功的页面和需要的操作
            Logger.i("---------------支付成功");
            ToastUtils.showMessage("支付成功");
            EventBus.getDefault().post(new EventCenter(Api.PAY_SUCCESS));
            finish();
        }
        if (code == -1) {
            //错误
            ToastUtils.showMessage("支付失败");
            EventBus.getDefault().post(new EventCenter(Api.PAY_FAIL));
            finish();
        }
        if (code == -2) {
            //用户取消
            ToastUtils.showMessage("支付已取消");
            EventBus.getDefault().post(new EventCenter(Api.PAY_FAIL));
            finish();
        }
    }

}
