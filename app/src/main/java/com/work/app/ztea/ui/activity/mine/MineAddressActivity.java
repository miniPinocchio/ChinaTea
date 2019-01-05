package com.work.app.ztea.ui.activity.mine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dream.library.utils.AbGsonUtil;
import com.dream.library.utils.annotation.annotation.event.OnClick;
import com.loopj.android.http.TextHttpResponseHandler;
import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;
import com.work.app.ztea.APP;
import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseActivity;
import com.work.app.ztea.base.BaseRecyclerViewRefreshActivity;
import com.work.app.ztea.entity.BaseEntity;
import com.work.app.ztea.entity.MyAddressListEntity;
import com.work.app.ztea.entity.ShopCarListEntity;
import com.work.app.ztea.http.Api;
import com.work.app.ztea.utils.UserService;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * 我的收货地址
 */
public class MineAddressActivity extends BaseRecyclerViewRefreshActivity {


    private String check;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_mine_address;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        setTopTitle("我的收货地址");
        check = getIntent().getStringExtra("check");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initPullRefreshAndLoadMore();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
    }

    @Override
    protected void initAdapter() {
        mAdapter = new RecyclerAdapter<MyAddressListEntity.Address>(APP.getInstance(), R.layout.item_my_address_info) {
            @Override
            protected void convert(RecyclerAdapterHelper helper, final MyAddressListEntity.Address item) {
                helper.setText(R.id.tv_name,item.getName());
                helper.setText(R.id.tv_phone, item.getMobile());
                helper.setText(R.id.tv_address, item.getProvince()+item.getCity()+item.getArea()+item.getAddress());
                helper.setImageResource(R.id.iv_is_default,TextUtils.equals(item.getIs_default(),"1")?R.drawable.mrdz:R.drawable.wxzg);
                //选择项
                helper.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(check)){
                            return;
                        }
                        setResult(RESULT_OK,new Intent().putExtra("bean",item));
                        finish();
                    }
                });
                //设置默认地址
                helper.getView(R.id.iv_is_default).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.equals(item.getIs_default(),"1")){
                            showToast("已经是默认地址了");
                            return;
                        }
                        Api.defaultAddr(UserService.getUserInfo().getToken(),item.getId(), new TextHttpResponseHandler() {
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
                });
                //编辑地址
                helper.getView(R.id.tv_edit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivityForResult(new Intent(MineAddressActivity.this,AddAddressActivity.class).putExtra("bean",item),100);
                    }
                });
                //删除地址
                helper.getView(R.id.tv_del).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showProgressDialog();
                        Api.editAddr(UserService.getUserInfo().getToken(), "3",item.getId(),
                                item.getName(),item.getMobile(),item.getIs_default(),
                                item.getProvince(),item.getCity(),item.getArea(),item.getAddress(), new TextHttpResponseHandler() {
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
                });
            }
        };
    }

    @Override
    protected void loadData() {
        String token = UserService.getUserInfo().getToken();
        Api.myAddress(token, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                hideProgressDialog();
                Log.d("params","addressList = "+responseString);
                MyAddressListEntity mPlanSortEntity = AbGsonUtil.json2Bean(responseString, MyAddressListEntity.class);
                if (mPlanSortEntity.isOk()) {
                    onLoadDataSuccess(mPlanSortEntity.data);
                }else {

                }
            }
        });
    }

    @OnClick({R.id.tv_add_address})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add_address://新增地址
                startActivityForResult(new Intent(MineAddressActivity.this,AddAddressActivity.class),100);
                break;

        }
    }

    @Override
    protected boolean isLoadingMoreEnabled() {
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK){
            return;
        }
        mBGARefreshLayout.beginRefreshing();
    }
}
