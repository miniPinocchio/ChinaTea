package com.work.app.ztea.ui.activity.mine;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.dream.library.eventbus.EventCenter;
import com.dream.library.utils.AbGsonUtil;
import com.dream.library.utils.annotation.annotation.ViewInject;
import com.dream.library.utils.annotation.annotation.event.OnClick;
import com.loopj.android.http.TextHttpResponseHandler;
import com.work.app.ztea.R;
import com.work.app.ztea.alipay.AuthResult;
import com.work.app.ztea.alipay.PayResult;
import com.work.app.ztea.base.BaseActivity;
import com.work.app.ztea.entity.BaseEntity;
import com.work.app.ztea.entity.LoginEntity;
import com.work.app.ztea.entity.MineHomeEntity;
import com.work.app.ztea.entity.OrderDataEntity;
import com.work.app.ztea.entity.OrderDetailEntity;
import com.work.app.ztea.http.Api;
import com.work.app.ztea.ui.MainActivity;
import com.work.app.ztea.ui.activity.goods.GoodsBalanceActivity;
import com.work.app.ztea.ui.activity.goods.PayResultActivity;
import com.work.app.ztea.utils.CashierInputFilter;
import com.work.app.ztea.utils.UserService;
import com.work.app.ztea.widget.ObservableScrollView;
import com.work.app.ztea.wxapi.WxUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 会员中心
 */
public class MemberActivity extends BaseActivity {

    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_AUTH_FLAG = 2;


    @ViewInject(R.id.tv_title_bg)
    TextView tv_title_bg;

    @ViewInject(R.id.scroll_view)
    ObservableScrollView scroll_view;
    @ViewInject(R.id.mCircleImageView)
    CircleImageView mCircleImageView;
    @ViewInject(R.id.tv_name)
    TextView tv_name;
    @ViewInject(R.id.tv_huiyuan_level)
    TextView tv_huiyuan_level;
    @ViewInject(R.id.tv_huiyuan_id)
    TextView tv_huiyuan_id;

    @ViewInject(R.id.tv_balance)
    TextView tv_balance;
    @ViewInject(R.id.tv_recharge_money)
    TextView tv_recharge_money;

    @ViewInject(R.id.et_money)
    EditText et_money;

    @ViewInject(R.id.check_wx)
    ImageView check_wx;
    @ViewInject(R.id.check_zfb)
    ImageView check_zfb;
    @ViewInject(R.id.check_yhk)
    ImageView check_yhk;
    //充值方式
    private int checkPay;
    private MineHomeEntity.Mine mine;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    Log.d("params", "responseInfo = " + resultInfo + "，resultStatus = " + resultStatus);
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(MemberActivity.this, "充值成功", Toast.LENGTH_SHORT).show();
                        //充值成功
                        EventBus.getDefault().post(new EventCenter(Api.RECHARGE_OK));
                        if (is_ask == 1){
                            updateLevel();
                            return;
                        }
                        finish();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(MemberActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(MemberActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(MemberActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(MemberActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }

    };

    private InputFilter[] filters = {new CashierInputFilter()};
    private int is_ask;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_member;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        setTopTitle("会员充值");
        mine = (MineHomeEntity.Mine) getIntent().getSerializableExtra("bean");
        et_money.setFilters(filters);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initListener();
        initUI();
    }

    private void initUI() {
        if (mine != null) {
            Glide.with(mContext)
                    .load(mine.getImage())
//                            .error(R.drawable.banner)
                    .into(mCircleImageView);
            tv_name.setText(mine.getName());
            tv_huiyuan_level.setText(mine.getLevelname());
            Drawable sm=getResources().getDrawable(R.drawable.zunjin);
            switch (mine.getLevel()){
                case "1":
                    sm=getResources().getDrawable(R.drawable.zunjin);
                    break;
                case "2":
                    sm=getResources().getDrawable(R.drawable.zunxiang);
                    break;
                case "3":
                    sm=getResources().getDrawable(R.drawable.zunzuan);
                    break;
            }
            sm.setBounds(0, 0, sm.getMinimumWidth(), sm.getMinimumHeight());
            tv_huiyuan_level.setCompoundDrawables(sm, null, null, null);
            tv_huiyuan_id.setText("ID："+ mine.getNumber());
            tv_balance.setText(mine.getAmount());
        }
    }

    private void initListener() {
        et_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String money = s.toString().trim();
                if (TextUtils.isEmpty(money)){
                    tv_recharge_money.setText(String.valueOf(10000));
                }else {
                    tv_recharge_money.setText(money);
                }
            }
        });
        scroll_view.setScrollViewListener(new ObservableScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                int scroll = px2dp(MemberActivity.this, y);
                tv_title_bg.setAlpha((float) scroll / 200);
            }
        });
    }

    //将px转换为dp
    public int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    @OnClick({R.id.tv_button_money,R.id.layout_wx,R.id.layout_zfb,R.id.layout_yhk,R.id.tv_recharge})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_button_money://充值金额
                tv_recharge_money.setText(String.valueOf(10000));
                break;
            case R.id.layout_wx://微信充值
                setCheckUi(2);
                break;
            case R.id.layout_zfb://支付宝充值
                setCheckUi(1);
                break;
            case R.id.layout_yhk://银行卡充值
                setCheckUi(3);
                break;
            case R.id.tv_recharge://充值
                getOrderDetail();
                break;
        }
    }

    private void setCheckUi(int check){
        checkPay = check;
        check_zfb.setImageResource(check == 1?R.drawable.xzg:R.drawable.wxzg);
        check_wx.setImageResource(check == 2?R.drawable.xzg:R.drawable.wxzg);
        check_yhk.setImageResource(check == 3?R.drawable.xzg:R.drawable.wxzg);
    }

    private void getOrderDetail(){
        String token = UserService.getUserInfo().getToken();
        String recharge_money = tv_recharge_money.getText().toString().trim();
        showProgressDialog();
        Api.rechargeMember(token,String.valueOf(checkPay), recharge_money,new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                hideProgressDialog();
                Log.d("params","member = "+responseString);
                OrderDataEntity detailsEntity = AbGsonUtil.json2Bean(responseString, OrderDataEntity.class);
                if (detailsEntity.isOk() && detailsEntity.data!=null) {
                    is_ask = detailsEntity.data.getIs_ask();
                    switch (checkPay){
                        case 1://支付宝支付
                            aliPay(detailsEntity.data.getAlipayAppParam());
                            break;
                        case 2://微信支付
                            WxUtils.getInstance().ActiveWXPay(detailsEntity.data);
                            break;
                    }
                }else {
                    showToast(detailsEntity.msg);
                }
            }
        });
    }

    /**
     * 支付宝支付
     *
     * @param info
     */
    private void aliPay(String info) {
        final String orderInfo = info;   // 订单信息
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(MemberActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    // 是否升级尊金会员
    private void updateLevel() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("充值成功");
        builder.setMessage("是否升级尊金会员？");
        builder.setPositiveButton("确定", new RechargeImpl());
        builder.setNegativeButton("取消", null);
        Dialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * 升级尊金会员
     */
    private class RechargeImpl implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            String token = UserService.getUserInfo().getToken();
            showProgressDialog();
            Api.updateLevel(token,new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    hideProgressDialog();
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    hideProgressDialog();
                    Log.d("params","member = "+responseString);
                    BaseEntity detailsEntity = AbGsonUtil.json2Bean(responseString, BaseEntity.class);
                    if (detailsEntity.isOk()) {
                        showToast("升级成功!");
                        EventBus.getDefault().post(new EventCenter(Api.RECHARGE_OK));
                        finish();
                    }else {
                        showToast(detailsEntity.msg);
                    }
                }
            });
        }
    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {
        super.onEventComing(eventCenter);
        if (eventCenter.getEventCode() == Api.PAY_SUCCESS) {//支付成功
            //充值成功
            EventBus.getDefault().post(new EventCenter(Api.RECHARGE_OK));
            if (is_ask == 1){
                updateLevel();
                return;
            }
            finish();
        }else if (eventCenter.getEventCode() == Api.PAY_SUCCESS){
//            startActivity(new Intent(GoodsBalanceActivity.this,PayResultActivity.class));
        }
    }

    @Override
    protected boolean isBindEventBus() {
        return true;
    }

}
