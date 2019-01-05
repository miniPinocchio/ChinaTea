package com.work.app.ztea.ui.activity.goods;

import android.content.Intent;
import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.work.app.ztea.base.BaseRecyclerViewRefreshActivity;
import com.work.app.ztea.entity.BaseEntity;
import com.work.app.ztea.entity.GoodsListEntity;
import com.work.app.ztea.entity.GoodsOrderEntity;
import com.work.app.ztea.entity.LoginEntity;
import com.work.app.ztea.entity.ShopCarListEntity;
import com.work.app.ztea.http.Api;
import com.work.app.ztea.utils.UserService;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * 购物车
 */
public class ShopCarActivity extends BaseRecyclerViewRefreshActivity {

    @ViewInject(R.id.checkbox_shop_all)
    ImageView checkbox_shop_all;

    @ViewInject(R.id.layout_total_money)
    LinearLayout layout_total_money;

    @ViewInject(R.id.tv_shop_price)
    TextView tv_shop_price;

    @ViewInject(R.id.bt_shop_zhifu)
    TextView bt_shop_zhifu;
    private boolean isCheckAll;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_shop_car;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        setTopTitle("购物车");
        setRightTitle("编辑");

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initPullRefreshAndLoadMore();
        initListener();
    }

    private void initListener() {
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                BigDecimal money = getMoney();//算钱
                //设置到控件上
                tv_shop_price.setText("¥ "+String.valueOf(money)+"元");
            }
        });
    }

    private BigDecimal getMoney() {
        BigDecimal amount = new BigDecimal(0.0);
        ArrayList<ShopCarListEntity.ShopCar> shopCarList = mAdapter.getAll();
        for (int i = 0; i < shopCarList.size(); i++) {
            if (shopCarList.get(i).isCheck()) {
//                int num = mList.get(i).getNum();
                ShopCarListEntity.ShopCar save = shopCarList.get(i);
                BigDecimal price = (new BigDecimal(save.getPrice())).multiply(BigDecimal.valueOf(Integer.parseInt(save.getGoods_num())));

//                saveList.add(save);
                amount = amount.add(price);
            }
        }
        return amount;
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
    }

    @Override
    protected void initAdapter() {
        mAdapter = new RecyclerAdapter<ShopCarListEntity.ShopCar>(APP.getInstance(), R.layout.item_car_info) {
            @Override
            protected void convert(RecyclerAdapterHelper helper, final ShopCarListEntity.ShopCar item) {
                helper.setText(R.id.tv_item_price,"¥ "+ item.getPrice());
                helper.setText(R.id.tv_item_name, item.getTitle());
                helper.setText(R.id.tv_num, item.getGoods_num());
                helper.setText(R.id.tv_goods_spec, "数量："+item.getGoods_num()+item.getGoods_attr());
                helper.setImageResource(R.id.checkbox_one,item.isCheck()?R.drawable.xz:R.drawable.wxzg);
                Glide.with(mContext)
                        .load(item.getImg_url())
                        .into((ImageView) helper.getView(R.id.iv_item_icon));
                helper.getView(R.id.checkbox_one).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        item.setCheck(!item.isCheck());
                        notifyDataSetChanged();
                        setAllCheck();
                    }
                });
                helper.getView(R.id.iv_add_num).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String goods_num = item.getGoods_num();
                        showProgressDialog();
                        Api.shopCarNum(UserService.getUserInfo().getToken(), item.getCart_id(),Integer.parseInt(goods_num)+1, new TextHttpResponseHandler() {
                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                hideProgressDialog();
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                hideProgressDialog();
                                BaseEntity mBaseEntity = AbGsonUtil.json2Bean(responseString, BaseEntity.class);
                                if (mBaseEntity.isOk()) {
                                    item.setGoods_num(String.valueOf(Integer.parseInt(item.getGoods_num())+1));
                                    notifyDataSetChanged();
                                } else {
                                    showToast(mBaseEntity.msg);
                                }
                            }
                        });
                    }
                });
                helper.getView(R.id.iv_del_num).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.equals(item.getGoods_num(),"1")){
                            showToast("不能再减了");
                            return;
                        }
                        String goods_num = item.getGoods_num();
                        showProgressDialog();
                        Api.shopCarNum(UserService.getUserInfo().getToken(), item.getCart_id(),Integer.parseInt(goods_num)-1, new TextHttpResponseHandler() {
                            @Override
                            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                hideProgressDialog();
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                hideProgressDialog();
                                BaseEntity mBaseEntity = AbGsonUtil.json2Bean(responseString, BaseEntity.class);
                                if (mBaseEntity.isOk()) {
                                    item.setGoods_num(String.valueOf(Integer.parseInt(item.getGoods_num())-1));
                                    notifyDataSetChanged();
                                } else {
                                    showToast(mBaseEntity.msg);
                                }
                            }
                        });
                    }
                });
            }
        };
    }

    /**
     * 设置是否全部选中
     */
    private void setAllCheck() {
        ArrayList<ShopCarListEntity.ShopCar> shopCarList = mAdapter.getAll();
        for (ShopCarListEntity.ShopCar shopCar:shopCarList) {
            if (!shopCar.isCheck()){
                isCheckAll = false;
                checkbox_shop_all.setImageResource(R.drawable.wxzg);
                return;
            }
        }
        isCheckAll = true;
        checkbox_shop_all.setImageResource(R.drawable.xz);
    }

    @Override
    protected void loadData() {
        String token = UserService.getUserInfo().getToken();
        Api.shopCarList(token, mPage, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                hideProgressDialog();
                Log.d("params","ShopCarList = "+responseString);
                ShopCarListEntity mPlanSortEntity = AbGsonUtil.json2Bean(responseString, ShopCarListEntity.class);
                if (mPlanSortEntity.isOk()) {
                    onLoadDataSuccess(mPlanSortEntity.data);
                }
            }
        });
    }

    @OnClick({R.id.bt_shop_zhifu,R.id.tv_right,R.id.checkbox_shop_all})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.bt_shop_zhifu://结算
                String zhifu = bt_shop_zhifu.getText().toString().trim();
                if (TextUtils.equals(zhifu,"删除")){
                    delShopCar();
                    return;
                }
                netGoodsOrder();
                break;
            case R.id.tv_right://编辑
                String right = tv_right.getText().toString().trim();
                tv_right.setText(TextUtils.equals(right,"编辑")?"完成":"编辑");
                bt_shop_zhifu.setText(TextUtils.equals(right,"编辑")?"删除":"去结算");
                layout_total_money.setVisibility(TextUtils.equals(right,"编辑")?View.GONE:View.VISIBLE);
                break;
            case R.id.checkbox_shop_all://全部选中
                ArrayList<ShopCarListEntity.ShopCar> shopCarList = mAdapter.getAll();
                isCheckAll = !isCheckAll;
                for (ShopCarListEntity.ShopCar shopCar:shopCarList) {
                    shopCar.setCheck(isCheckAll);
                }
                checkbox_shop_all.setImageResource(isCheckAll?R.drawable.xz:R.drawable.wxzg);
                mAdapter.notifyDataSetChanged();
                break;
        }
    }

    private String carId = "";
    /**
     * 下订单
     */
    private void netGoodsOrder(){
        ArrayList<ShopCarListEntity.ShopCar> shopCarList = mAdapter.getAll();
        carId = "";
        for (ShopCarListEntity.ShopCar shopCar:shopCarList){
            if (shopCar.isCheck()){
                carId += shopCar.getCart_id()+",";
            }
        }
        if (TextUtils.isEmpty(carId)){
            showToast("请选择要购买的商品");
            return;
        }
        carId  = carId.substring(0,carId.lastIndexOf(","));
        showProgressDialog();
        LoginEntity.Login userInfo = UserService.getUserInfo();
        Log.d("params","carId = "+carId);
        Api.orderSureTwo(userInfo.getToken(), carId,new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                hideProgressDialog();
                Log.d("params","orderDetail = "+responseString);
                GoodsOrderEntity detailsEntity = AbGsonUtil.json2Bean(responseString, GoodsOrderEntity.class);
                if (detailsEntity.isOk() && detailsEntity.data != null) {
                    startActivity(new Intent(ShopCarActivity.this,GoodsBalanceActivity.class)
                            .putExtra("goodsOrder",detailsEntity.data).putExtra("carId",carId));
                }
            }
        });
    }

    /**
     * 删除商品
     */
    private void delShopCar(){
        ArrayList<ShopCarListEntity.ShopCar> shopCarList = mAdapter.getAll();
        String carId = "";
        for (ShopCarListEntity.ShopCar shopCar:shopCarList){
            if (shopCar.isCheck()){
                carId = shopCar.getCart_id()+",";
            }
        }
        if (TextUtils.isEmpty(carId)){
            showToast("请选择要删除的商品");
            return;
        }
        carId  = carId.substring(0,carId.lastIndexOf(","));
        showProgressDialog();
        Api.delShopCarGoods(UserService.getUserInfo().getToken(), carId,isCheckAll?"clear":"del", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                hideProgressDialog();
                BaseEntity mBaseEntity = AbGsonUtil.json2Bean(responseString, BaseEntity.class);
                if (mBaseEntity.isOk()) {
                    mBGARefreshLayout.beginRefreshing();
                } else {
                    showToast(mBaseEntity.msg);
                }
            }
        });
    }
    @Override
    protected boolean isLoadingMoreEnabled() {
        return false;
    }
}
