package com.work.app.ztea.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dream.library.eventbus.EventCenter;
import com.dream.library.utils.AbGsonUtil;
import com.loopj.android.http.TextHttpResponseHandler;
import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;
import com.work.app.ztea.APP;
import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseFragment;
import com.work.app.ztea.base.BaseRecyclerViewPullRefreshFragment;
import com.work.app.ztea.entity.BaseEntity;
import com.work.app.ztea.entity.GoodsListEntity;
import com.work.app.ztea.entity.GoodsOrderEntity;
import com.work.app.ztea.entity.LoginEntity;
import com.work.app.ztea.entity.MyOrderListEntity;
import com.work.app.ztea.entity.OrderDetailEntity;
import com.work.app.ztea.http.Api;
import com.work.app.ztea.ui.activity.goods.GoodsBalanceActivity;
import com.work.app.ztea.ui.activity.goods.GoodsDetailActivity;
import com.work.app.ztea.ui.activity.goods.GoodsListActivity;
import com.work.app.ztea.ui.activity.goods.PayResultActivity;
import com.work.app.ztea.utils.UserService;

import cz.msebera.android.httpclient.Header;

/**
 * 订单页面
 * A simple {@link Fragment} subclass.
 */
public class OrderChildFragment extends BaseRecyclerViewPullRefreshFragment {


    private int position;

    public OrderChildFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt("point");
        }
    }

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_order_child;
    }

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        initPullRefreshAndLoadMore(view);
    }

    @Override
    protected void initData(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
    }

    @Override
    protected void initAdapter() {
        mAdapter = new RecyclerAdapter<MyOrderListEntity.MyOrder>(APP.getInstance(), R.layout.item_my_order_info) {
            @Override
            protected void convert(RecyclerAdapterHelper helper, final MyOrderListEntity.MyOrder item) {
                helper.setText(R.id.tv_order_no,item.getNumber());
                helper.setText(R.id.tv_goods_name,item.getTitle());
                helper.setText(R.id.tv_goods_price,"¥ "+ item.getPay_money()+"元");
                helper.setText(R.id.tv_goods_guige, "共"+item.getGoods_num()+"件商品   实付¥ "+item.getPay_money()+"元");
                helper.setText(R.id.tv_goods_num, "x"+item.getGoods_num());
                switch (item.getState()){
                    case "0":// 0取消，去支付
                        helper.setVisible(R.id.tv_goods_guige,View.VISIBLE);
                        helper.setVisible(R.id.tv_goods_price,View.GONE);
                        helper.setText(R.id.tv_goods_guige,"¥ "+ item.getPay_money()+"元");
                        helper.setTextColor(R.id.tv_goods_guige,getResources().getColor(R.color.orange));
                        helper.setVisible(R.id.tv_button_1,View.VISIBLE);
                        helper.setVisible(R.id.tv_button_2,View.VISIBLE);
                        helper.setText(R.id.tv_button_1,"取消订单");
                        helper.setText(R.id.tv_button_2,"立即支付");
                        helper.setText(R.id.tv_order_status,"待付款");
                        break;
                    case "1":// 1提醒发货
                        helper.setVisible(R.id.tv_goods_guige,View.VISIBLE);
                        helper.setVisible(R.id.tv_button_1,View.VISIBLE);
                        helper.setText(R.id.tv_button_1,"提醒发货");
                        helper.setText(R.id.tv_order_status,"待发货");
                        break;
                    case "2"://2确认收货，查看物流
                        helper.setVisible(R.id.tv_goods_guige,View.VISIBLE);
                        helper.setVisible(R.id.tv_button_1,View.VISIBLE);
                        helper.setVisible(R.id.tv_button_2,View.VISIBLE);
                        helper.setText(R.id.tv_button_1,"查看物流");
                        helper.setText(R.id.tv_button_2,"确认收货");
                        helper.setText(R.id.tv_order_status,"已发货");
                        break;
                    case "3"://3申请发票，再次购买，去评价
                        helper.setVisible(R.id.tv_goods_guige,View.VISIBLE);
                        helper.setVisible(R.id.tv_button_1,View.VISIBLE);
                        helper.setVisible(R.id.tv_button_2,View.VISIBLE);
                        helper.setVisible(R.id.tv_button_3,View.VISIBLE);
                        helper.setText(R.id.tv_button_1,"申请发票");
                        helper.setText(R.id.tv_button_2,"再次购买");
                        helper.setText(R.id.tv_button_3,"评价");
                        helper.setText(R.id.tv_order_status,"交易成功");
                        break;
                }

                Glide.with(mContext)
                        .load(item.getImage())
//                            .error(R.drawable.banner)
                        .into((ImageView) helper.getView(R.id.iv_goods_img));
                helper.getView(R.id.tv_button_1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (item.getState()){
                            case "0"://取消订单
                                orderStatus(item.getId(),"0");
                                break;
                            case "1"://1提醒发货
                                remindOrder(item.getId());
                                break;
                            case "2"://查看物流

                                break;
                            case "3"://申请发票

                                break;
                        }
                    }
                });
                helper.getView(R.id.tv_button_2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (item.getState()){
                            case "0"://立即支付
                                getOrderDetail(item.getId());
                                break;
                            case "2"://确认收货
                                orderStatus(item.getId(),"2");
                                break;
                            case "3"://再次购买

                                break;
                        }
                    }
                });
                helper.getView(R.id.tv_button_3).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //评价

                    }
                });
                helper.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        startActivity(new Intent(getContext(),GoodsDetailActivity.class).putExtra("id",item.getId()));
                    }
                });
            }
        };
    }

    private void getOrderDetail(final String orders_id){
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
                    detailEntity.data.setOrder_id(orders_id);
                    startActivity(new Intent(getContext(),GoodsBalanceActivity.class).putExtra("goodsOrder",detailEntity.data));
                }else {
                    showToast(detailEntity.msg);
                }
            }
        });
    }

    /**
     * 订单状态变更
     * @param orders_id
     * @param status
     */
    private void orderStatus(String orders_id,String status){
        String token = UserService.getUserInfo().getToken();
        showProgressDialog();
        Api.orderStatus(token,orders_id,status, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                hideProgressDialog();
                Log.d("params","orderDetail = "+responseString);
                BaseEntity detailEntity = AbGsonUtil.json2Bean(responseString, BaseEntity.class);
                if (detailEntity.isOk() ) {
                    showToast("订单取消成功");
                    mBGARefreshLayout.beginRefreshing();
                }else {
                    showToast(detailEntity.msg);
                }
            }
        });
    }

    /**
     * 提醒发货
     * @param orders_id
     */
    private void remindOrder(String orders_id){
        String token = UserService.getUserInfo().getToken();
        showProgressDialog();
        Api.remindOrder(token,orders_id, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                hideProgressDialog();
                Log.d("params","orderDetail = "+responseString);
                BaseEntity detailEntity = AbGsonUtil.json2Bean(responseString, BaseEntity.class);
                if (detailEntity.isOk()) {
                    showToast("提醒发货成功");
                }else {
                    showToast(detailEntity.msg);
                }
            }
        });
    }

    @Override
    protected void loadData() {
        String token = UserService.getUserInfo().getToken();
        Api.myOrder(token, String.valueOf(position),String.valueOf(mPage),String.valueOf(mPageSize), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                hideProgressDialog();
                Log.d("params","OrderList = "+responseString);
                MyOrderListEntity mPlanSortEntity = AbGsonUtil.json2Bean(responseString, MyOrderListEntity.class);
                if (mPlanSortEntity != null && mPlanSortEntity.isOk()) {
                    onLoadDataSuccess(mPlanSortEntity.data);
                }
            }
        });
    }

    @Override
    protected boolean isLoadingMoreEnabled() {
        return true;
    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {
        super.onEventComing(eventCenter);
        if (eventCenter.getEventCode() == Api.ORDER_PAY_OK) {
            mBGARefreshLayout.beginRefreshing();
        }
    }

    @Override
    protected boolean isBindEventBus() {
        return true;
    }
}
