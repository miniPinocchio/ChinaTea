package com.work.app.ztea.ui.activity.goods;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.work.app.ztea.base.BaseActivity;
import com.work.app.ztea.entity.BaseEntity;
import com.work.app.ztea.entity.GoodsOrderEntity;
import com.work.app.ztea.entity.LoginEntity;
import com.work.app.ztea.entity.OrderDetailEntity;
import com.work.app.ztea.http.Api;
import com.work.app.ztea.ui.MainActivity;
import com.work.app.ztea.ui.activity.LoginActivity;
import com.work.app.ztea.ui.activity.mine.EditPwdActivity;
import com.work.app.ztea.ui.activity.mine.InvoiceActivity;
import com.work.app.ztea.ui.activity.mine.MineAddressActivity;
import com.work.app.ztea.ui.activity.mine.MineOrderActivity;
import com.work.app.ztea.utils.DateUtils;
import com.work.app.ztea.utils.UserService;
import com.work.app.ztea.widget.SelfAdaptionRecycler;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;

import cz.msebera.android.httpclient.Header;

/**
 * 支付结果
 */
public class PayResultActivity extends BaseActivity {

    @ViewInject(R.id.iv_result)
    ImageView iv_result;
    @ViewInject(R.id.tv_result)
    TextView tv_result;

    @ViewInject(R.id.tv_order_num)
    TextView tv_order_num;
    @ViewInject(R.id.tv_order_time)
    TextView tv_order_time;

    @ViewInject(R.id.goods_list)
    SelfAdaptionRecycler goods_list;

    @ViewInject(R.id.tv_name)
    TextView tv_name;
    @ViewInject(R.id.tv_phone)
    TextView tv_phone;
    @ViewInject(R.id.tv_address)
    TextView tv_address;

    private String orders_id;

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

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_pay_result;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        orders_id = getIntent().getStringExtra("orders_id");
        setTopTitle(TextUtils.isEmpty(orders_id)?"支付失败":"支付成功");
        tv_result.setText(!TextUtils.isEmpty(orders_id)?"订单支付成功":"购买失败");
        iv_result.setImageResource(TextUtils.isEmpty(orders_id)?R.drawable.gmsb:R.drawable.cg);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        goods_list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        goods_list.setAdapter(mAdapterGoods);
        getOrderDetail();
    }

    private void initUI(OrderDetailEntity.OrderDetail orderBean) {
        if (orderBean != null) {
            mAdapterGoods.replaceAll(orderBean.getGoods_list());
        }
        tv_name.setText("收货人："+orderBean.getName());
        tv_phone.setText("电话："+orderBean.getMobile());
        tv_address.setText("收货地址："+orderBean.getProvince()+orderBean.getCity()+orderBean.getArea()+orderBean.getAddress());

        tv_order_num.setText(orderBean.getNumber());
        tv_order_time.setText(DateUtils.stampToDate(orderBean.getCrdate()));
    }

    @OnClick({R.id.tv_go_shop,R.id.tv_order})
    public void OnClick(View view) {
        Intent intent=new Intent();
        switch (view.getId()) {
            case R.id.tv_go_shop://继续购物
                EventBus.getDefault().post(new EventCenter(Api.OPEN_SHOP,100));
                break;
            case R.id.tv_order://查看订单
                EventBus.getDefault().post(new EventCenter(Api.OPEN_SHOP,101));
                break;

        }
        intent.setClass(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//它可以关掉所要到的界面中间的activity
        startActivity(intent);
        finish();
    }

    private void getOrderDetail(){
        String token = UserService.getUserInfo().getToken();
        showProgressDialog();
        Api.orderDetail(token,orders_id, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                hideProgressDialog();
                Log.d("params","orderDetail = "+responseString);
                OrderDetailEntity detailEntity = AbGsonUtil.json2Bean(responseString, OrderDetailEntity.class);
                if (detailEntity.isOk() && detailEntity.data != null) {
                    initUI(detailEntity.data);
                }else {
                    showToast(detailEntity.msg);
                }
            }
        });
    }
}
