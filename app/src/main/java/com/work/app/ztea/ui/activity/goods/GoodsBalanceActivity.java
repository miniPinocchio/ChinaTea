package com.work.app.ztea.ui.activity.goods;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.dream.library.eventbus.EventCenter;
import com.dream.library.utils.AbGsonUtil;
import com.dream.library.utils.annotation.annotation.ViewInject;
import com.dream.library.utils.annotation.annotation.event.OnClick;
import com.loopj.android.http.TextHttpResponseHandler;
import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;
import com.work.app.ztea.APP;
import com.work.app.ztea.R;
import com.work.app.ztea.alipay.AuthResult;
import com.work.app.ztea.alipay.PayResult;
import com.work.app.ztea.base.BaseActivity;
import com.work.app.ztea.entity.DefaultAddrEntity;
import com.work.app.ztea.entity.GoodsOrderEntity;
import com.work.app.ztea.entity.HomeGoodsEntity;
import com.work.app.ztea.entity.LoginEntity;
import com.work.app.ztea.entity.MyAddressListEntity;
import com.work.app.ztea.entity.OrderDataEntity;
import com.work.app.ztea.entity.OrderDetailEntity;
import com.work.app.ztea.http.Api;
import com.work.app.ztea.ui.activity.WebViewActivity;
import com.work.app.ztea.ui.activity.mine.InvoiceActivity;
import com.work.app.ztea.ui.activity.mine.MineAddressActivity;
import com.work.app.ztea.utils.UserService;
import com.work.app.ztea.widget.SelfAdaptionRecycler;
import com.work.app.ztea.wxapi.WxUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import cz.msebera.android.httpclient.Header;

/**
 * 商品结算页
 */
public class GoodsBalanceActivity extends BaseActivity {

    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_AUTH_FLAG = 2;

    @ViewInject(R.id.layout_receipt)
    RelativeLayout layout_receipt;
    @ViewInject(R.id.iv_address_logo)
    ImageView iv_address_logo;
    @ViewInject(R.id.tv_name)
    TextView tv_name;
    @ViewInject(R.id.tv_phone)
    TextView tv_phone;
    @ViewInject(R.id.tv_address)
    TextView tv_address;
    @ViewInject(R.id.tv_no_select_address)
    TextView tv_no_select_address;

    @ViewInject(R.id.goods_list)
    SelfAdaptionRecycler goods_list;
    @ViewInject(R.id.tv_ps_type)
    TextView tv_ps_type;
    @ViewInject(R.id.check_true)
    ImageView check_true;
    @ViewInject(R.id.check_false)
    ImageView check_false;
    @ViewInject(R.id.et_remark)
    EditText et_remark;

    @ViewInject(R.id.tv_goods_num)
    TextView tv_goods_num;//共计商品数量
    @ViewInject(R.id.tv_goods_price)
    TextView tv_goods_price;//小计金额

    @ViewInject(R.id.tv_zjk_price)
    TextView tv_zjk_price;//尊金卡金额
    @ViewInject(R.id.check_zjk)
    CheckBox check_zjk;
    @ViewInject(R.id.check_wx)
    ImageView check_wx;
    @ViewInject(R.id.check_zfb)
    ImageView check_zfb;
    @ViewInject(R.id.check_yhk)
    ImageView check_yhk;
    @ViewInject(R.id.check_xianxia)
    ImageView check_xianxia;

    @ViewInject(R.id.tv_hetong)
    TextView tv_hetong;
    @ViewInject(R.id.cb_remember)
    CheckBox cb_remember;
    @ViewInject(R.id.tv_fapiao)
    TextView tv_fapiao;
    @ViewInject(R.id.cb_fapiao)
    CheckBox cb_fapiao;

    @ViewInject(R.id.tv_total_price)
    TextView tv_total_price;//总计金额
    //是否需要存茶
    private boolean isNeedCuncha;


    private RecyclerAdapter mAdapterGoods = new RecyclerAdapter<OrderDetailEntity.OrderDetail.GoodsListBean>(APP.getInstance(), R.layout.item_sure_order_info) {
        @Override
        protected void convert(RecyclerAdapterHelper helper, final OrderDetailEntity.OrderDetail.GoodsListBean item) {
            helper.setText(R.id.tv_item_price,"¥ "+ item.getPrice()+"元");
            helper.setText(R.id.tv_item_name, item.getTitle());
            helper.setText(R.id.tv_num,"x"+ item.getGoods_num());
            helper.setText(R.id.tv_goods_spec, "数量："+item.getGoods_num()+item.getGoods_attr());
            Glide.with(mContext)
                    .load(item.getImg_url())
                    .into((ImageView) helper.getView(R.id.iv_item_icon));
        }
    };
    private String address_id;

    private String beanId;

    private int checkPay;

    private String carId;

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
                        Toast.makeText(GoodsBalanceActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(new EventCenter(Api.ORDER_PAY_OK));
                        startActivity(new Intent(GoodsBalanceActivity.this,PayResultActivity.class)
                                .putExtra("orders_id",orders_id));
                        finish();
//                        startActivity(new Intent(SubscribeActivity.this, OrderDataActivity.class).putExtra("page",0));
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(GoodsBalanceActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(GoodsBalanceActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(GoodsBalanceActivity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(GoodsBalanceActivity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }

    };
    private String orders_id;


    private GoodsOrderEntity.Order orderBean;

    private OrderDetailEntity.OrderDetail orderDetail;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_goods_balance;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        setTopTitle("商品结算");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        Object object = getIntent().getSerializableExtra("goodsOrder");
        if (object instanceof GoodsOrderEntity.Order){
            orderBean = (GoodsOrderEntity.Order) object;
        }else if (object instanceof OrderDetailEntity.OrderDetail){
            orderDetail = (OrderDetailEntity.OrderDetail) object;
        }

        carId = getIntent().getStringExtra("carId");
        goods_list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        goods_list.setAdapter(mAdapterGoods);
        updateUI();
        initListener();
    }

    private void updateUI() {
        if (orderDetail != null){
            mAdapterGoods.replaceAll(orderDetail.getGoods_list());
            tv_ps_type.setText(TextUtils.equals(orderDetail.getShip_money(),"0")?"快递：免邮":"邮费："+orderDetail.getShip_money());
            tv_goods_num.setText("共"+orderDetail.getGoods_list().size()+"件商品");
            tv_goods_price.setText(orderDetail.getTotal_money());
            tv_total_price.setText("¥ "+orderDetail.getTotal_money()+"元");
            tv_zjk_price.setText("¥ "+orderDetail.getAmount());
            tv_no_select_address.setVisibility(View.GONE);
            iv_address_logo.setVisibility(View.VISIBLE);
            tv_name.setText("收货人："+orderDetail.getName());
            tv_phone.setText("电话："+orderDetail.getMobile());
            tv_address.setText("收货地址："+orderDetail.getProvince()+orderDetail.getCity()+orderDetail.getArea()+orderDetail.getAddress());

            et_remark.setEnabled(false);

            layout_receipt.setEnabled(false);
            check_true.setEnabled(false);
            check_false.setEnabled(false);

            tv_hetong.setEnabled(false);
            cb_remember.setEnabled(false);
            tv_fapiao.setEnabled(false);
            cb_fapiao.setEnabled(false);
            return;
        }
        mAdapterGoods.replaceAll(orderBean.getGoods_list());
        tv_ps_type.setText(TextUtils.equals(orderBean.getShip_money(),"0")?"快递：免邮":"邮费："+orderBean.getShip_money());
        tv_goods_num.setText("共"+orderBean.getGoods_list().size()+"件商品");
        tv_goods_price.setText(orderBean.getMoney());
        tv_total_price.setText("¥ "+orderBean.getTotal_money()+"元");
        tv_zjk_price.setText("¥ "+orderBean.getAmount());
        beanId = orderBean.getInvoice();
        getDefaultAddress();
    }

    private void initListener() {
        cb_fapiao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (TextUtils.equals(beanId,"0")){
                        showToast("请选择发票");
                        cb_fapiao.setChecked(!isChecked);
                        startActivityForResult(new Intent(GoodsBalanceActivity.this,InvoiceActivity.class)
                                .putExtra("check","check"),101);
                    }
                }
            }
        });
        check_zjk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    check_xianxia.setImageResource(R.drawable.wxzg);
                }
            }
        });
    }

    @OnClick({R.id.layout_receipt,R.id.check_true,R.id.check_false,
            R.id.layout_wx,R.id.layout_zfb,R.id.layout_yhk,R.id.layout_xxzk,
            R.id.tv_hetong,R.id.tv_fapiao,R.id.tv_commit_order})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.layout_receipt://选择收货地址
                startActivityForResult(new Intent(this,MineAddressActivity.class).putExtra("check","check"),100);
                break;
            case R.id.check_true://是否存茶
                isNeedCuncha = true;
                check_true.setImageResource(R.drawable.xz);
                check_false.setImageResource(R.drawable.wxzg);
                break;
            case R.id.check_false://是否存茶
                isNeedCuncha = false;
                check_true.setImageResource(R.drawable.wxzg);
                check_false.setImageResource(R.drawable.xz);
                break;
            case R.id.layout_wx://微信支付
                if (checkPay == 2){
                    setCheckUi(0);
                    return;
                }
                setCheckUi(2);
                break;
            case R.id.layout_zfb://支付宝支付
                if (checkPay == 1){
                    setCheckUi(0);
                    return;
                }
                setCheckUi(1);
                break;
            case R.id.layout_yhk://银行卡支付
                if (checkPay == 3){
                    setCheckUi(0);
                    return;
                }
                setCheckUi(3);
                break;
            case R.id.layout_xxzk://线下支付
                if (checkPay == 6){
                    setCheckUi(0);
                    return;
                }
                setCheckUi(6);
                break;
            case R.id.tv_hetong://电子合同
                Bundle bundle = new Bundle();
                bundle.putString(WebViewActivity.BUNDLE_KEY_TITLE, "电子合同");
                bundle.putString(WebViewActivity.BUNDLE_KEY_URL, orderBean.getAgreement());
                readyGo(WebViewActivity.class, bundle);
                break;
            case R.id.tv_fapiao://申请开票
                startActivityForResult(new Intent(this,InvoiceActivity.class)
                        .putExtra("check","check")
                        .putExtra("invoiceId",beanId),101);
                break;
            case R.id.tv_commit_order://提交订单
                if (orderDetail != null){
                    onlinePay();
                    return;
                }
                if (TextUtils.isEmpty(address_id)){
                    showToast("请先选择地址");
                    return;
                }
                if (!check_zjk.isChecked() && checkPay == 0){
                    showToast("请选择支付方式");
                    return;
                }
                if (check_zjk.isChecked() && checkPay == 0){
                    if (Double.parseDouble(orderBean.getAmount()) < Double.parseDouble(orderBean.getTotal_money()))
                    {
                        showToast("余额不足，请选择其他付款方式");
                        return;
                    }
                }
                if (!cb_remember.isChecked()){
                    showToast("请先同意电子合同");
                    return;
                }
                submintOrder();
                break;
        }
    }

    private void setCheckUi(int check){
        checkPay = check;
        check_zfb.setImageResource(check == 1?R.drawable.xzg:R.drawable.wxzg);
        check_wx.setImageResource(check == 2?R.drawable.xzg:R.drawable.wxzg);
        check_yhk.setImageResource(check == 3?R.drawable.xzg:R.drawable.wxzg);
        check_xianxia.setImageResource(check == 6?R.drawable.xzg:R.drawable.wxzg);
        if (check == 4){
            check_zjk.setChecked(false);
        }
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
                PayTask alipay = new PayTask(GoodsBalanceActivity.this);
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

    /**
     * 获取默认地址
     */
    private void getDefaultAddress(){
        LoginEntity.Login userInfo = UserService.getUserInfo();
        showProgressDialog();
        Api.getDefaultAddress(userInfo.getToken(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                hideProgressDialog();
                Log.d("params","DefaultAddress = "+responseString);
                DefaultAddrEntity detailsEntity = AbGsonUtil.json2Bean(responseString, DefaultAddrEntity.class);
                if (detailsEntity.isOk() && detailsEntity.data != null) {
                    address_id = detailsEntity.data.getId();
                    tv_no_select_address.setVisibility(View.GONE);
                    iv_address_logo.setVisibility(View.VISIBLE);
                    tv_name.setText("收货人："+detailsEntity.data.getName());
                    tv_phone.setText("电话："+detailsEntity.data.getMobile());
                    tv_address.setText("收货地址："+detailsEntity.data.getProvince()+detailsEntity.data.getCity()+detailsEntity.data.getArea()+detailsEntity.data.getAddress());
                }
            }
        });
    }

    /**
     * 在线支付
     */
    private void onlinePay(){
        LoginEntity.Login userInfo = UserService.getUserInfo();
        showProgressDialog();
        Api.onlinePay(userInfo.getToken(),orderDetail.getOrder_id(),"","",
                check_zjk.isChecked()?orderDetail.getAmount():"0",String.valueOf(checkPay),new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        hideProgressDialog();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        hideProgressDialog();
                        Log.d("params","onlinePay = "+responseString);
                        OrderDataEntity detailsEntity = AbGsonUtil.json2Bean(responseString, OrderDataEntity.class);
                        if (detailsEntity.isOk() && detailsEntity.data != null) {
                            orders_id = detailsEntity.data.getOrders_id();
                            String total = detailsEntity.data.getTotal();
                            if (check_zjk.isChecked()){
                                if (!TextUtils.isEmpty(total) && Double.parseDouble(total) > 0){
                                    switch (checkPay){
                                        case 1://支付宝支付
                                            aliPay(detailsEntity.data.getAlipayAppParam());
                                            break;
                                        case 2://微信支付
                                            WxUtils.getInstance().ActiveWXPay(detailsEntity.data);
                                            break;
                                    }
                                    return;
                                }
                                EventBus.getDefault().post(new EventCenter(Api.ORDER_PAY_OK));
                                startActivity(new Intent(GoodsBalanceActivity.this,PayResultActivity.class)
                                        .putExtra("orders_id",orders_id));
                                finish();
                            }else {
                                switch (checkPay){
                                    case 1://支付宝支付
                                        aliPay(detailsEntity.data.getAlipayAppParam());
                                        break;
                                    case 2://微信支付
                                        WxUtils.getInstance().ActiveWXPay(detailsEntity.data);
                                        break;
                                }
                            }
                        }else {
                            showToast(detailsEntity.msg);
                        }
                    }
                });
    }

    /**
     * 下单
     */
    private void submintOrder(){
        LoginEntity.Login userInfo = UserService.getUserInfo();
        String remark = et_remark.getText().toString().trim();
        showProgressDialog();
        Api.commintOrder(userInfo.getToken(),orderBean.getOrder_type(),Float.parseFloat(orderBean.getTotal_money()),"",
                check_zjk.isChecked()?orderBean.getAmount():"0","","",address_id, cb_fapiao.isChecked()?beanId:"",
                remark,isNeedCuncha?"0":"1",String.valueOf(checkPay),"",orderBean.getForm(),
                TextUtils.isEmpty(carId)?"":carId,!TextUtils.isEmpty(carId)?"":orderBean.getGoods_list().get(0).getGoods_id(),!TextUtils.isEmpty(carId)?"":orderBean.getGoods_list().get(0).getGoods_num(),new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                hideProgressDialog();
                Log.d("params","order = "+responseString);
                OrderDataEntity detailsEntity = AbGsonUtil.json2Bean(responseString, OrderDataEntity.class);
                if (detailsEntity.isOk() && detailsEntity.data != null) {
                    orders_id = detailsEntity.data.getOrders_id();
                    String total = detailsEntity.data.getTotal();
                    if (check_zjk.isChecked()){
                        if (!TextUtils.isEmpty(total) &&Double.parseDouble(total) > 0){
                            switch (checkPay){
                                case 1://支付宝支付
                                    aliPay(detailsEntity.data.getAlipayAppParam());
                                    break;
                                case 2://微信支付
                                    WxUtils.getInstance().ActiveWXPay(detailsEntity.data);
                                    break;
                            }
                            return;
                        }
                        startActivity(new Intent(GoodsBalanceActivity.this,PayResultActivity.class)
                                .putExtra("orders_id",orders_id));
                        finish();
                    }else {
                        switch (checkPay){
                            case 1://支付宝支付
                                aliPay(detailsEntity.data.getAlipayAppParam());
                                break;
                            case 2://微信支付
                                WxUtils.getInstance().ActiveWXPay(detailsEntity.data);
                                break;
                        }
                    }
                }else {
                    showToast(detailsEntity.msg);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK){
            return;
        }
        switch (requestCode){
            case 100:
                MyAddressListEntity.Address item = (MyAddressListEntity.Address) data.getSerializableExtra("bean");
                if (item != null) {
                    address_id = item.getId();
                    tv_no_select_address.setVisibility(View.GONE);
                    iv_address_logo.setVisibility(View.VISIBLE);
                    tv_name.setText("收货人："+item.getName());
                    tv_phone.setText("电话："+item.getMobile());
                    tv_address.setText("收货地址："+item.getProvince()+item.getCity()+item.getArea()+item.getAddress());
                }
                break;
            case 101:
                beanId = data.getStringExtra("beanId");
                if (!TextUtils.isEmpty(beanId)){
                    cb_fapiao.setChecked(true);
                }
                break;
        }

    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {
        super.onEventComing(eventCenter);
        if (eventCenter.getEventCode() == Api.PAY_SUCCESS) {
            EventBus.getDefault().post(new EventCenter(Api.ORDER_PAY_OK));
            startActivity(new Intent(GoodsBalanceActivity.this,PayResultActivity.class)
                    .putExtra("orders_id",orders_id));
            finish();
        }else if (eventCenter.getEventCode() == Api.PAY_SUCCESS){
//            startActivity(new Intent(GoodsBalanceActivity.this,PayResultActivity.class));
        }else if (eventCenter.getEventCode() == Api.INVOICE_REFRESH){
            beanId = "0";
            cb_fapiao.setChecked(false);
        }
    }

    @Override
    protected boolean isBindEventBus() {
        return true;
    }


}
