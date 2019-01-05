package com.work.app.ztea.ui.activity.mine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.dream.library.eventbus.EventCenter;
import com.dream.library.utils.AbGsonUtil;
import com.dream.library.utils.annotation.annotation.event.OnClick;
import com.loopj.android.http.TextHttpResponseHandler;
import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;
import com.work.app.ztea.APP;
import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseRecyclerViewRefreshActivity;
import com.work.app.ztea.entity.BaseEntity;
import com.work.app.ztea.entity.MyAddressListEntity;
import com.work.app.ztea.entity.MyInvoiceListEntity;
import com.work.app.ztea.http.Api;
import com.work.app.ztea.utils.UserService;

import org.greenrobot.eventbus.EventBus;

import cz.msebera.android.httpclient.Header;

/**
 * 发票管理
 */
public class InvoiceActivity extends BaseRecyclerViewRefreshActivity {

    private String check;

    private String invoiceId;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_invoice;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        setTopTitle("申请开票");
        check = getIntent().getStringExtra("check");
        invoiceId = getIntent().getStringExtra("invoiceId");
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
        mAdapter = new RecyclerAdapter<MyInvoiceListEntity.Invoice>(APP.getInstance(), R.layout.item_my_invoice_info) {
            @Override
            protected void convert(RecyclerAdapterHelper helper, final MyInvoiceListEntity.Invoice item) {
                helper.setText(R.id.tv_name,"名称："+item.getTitle());
                helper.setText(R.id.tv_phone, "手机号："+item.getMobile());
                helper.setImageResource(R.id.iv_is_default, TextUtils.equals(item.getIs_default(),"1")?R.drawable.mrdz:R.drawable.wxzg);
                helper.setVisible(R.id.iv_default_logo, TextUtils.equals(item.getIs_default(),"1")?View.VISIBLE:View.GONE);
                //选择项
                helper.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(check)){
                            return;
                        }
                        setResult(RESULT_OK,new Intent().putExtra("beanId",item.getId()));
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
                        Api.defaultInvoice(UserService.getUserInfo().getToken(),item.getId(), new TextHttpResponseHandler() {
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
                        startActivityForResult(new Intent(InvoiceActivity.this,AddInvoiceActivity.class).putExtra("bean",item),100);
                    }
                });
                //删除地址
                helper.getView(R.id.tv_del).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showProgressDialog();
                        Api.editInvoice(UserService.getUserInfo().getToken(), "3",item.getId(),
                                item.getTypes(),item.getTitle(),item.getTax_number(),
                                item.getAddress(),item.getBank_name(),item.getBank_card(),item.getTel(),item.getMobile(),item.getEmail(), new TextHttpResponseHandler() {
                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                        hideProgressDialog();
                                    }

                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                        hideProgressDialog();
                                        BaseEntity mBaseEntity = AbGsonUtil.json2Bean(responseString, BaseEntity.class);
                                        if (mBaseEntity.isOk()) {
                                            if (TextUtils.equals(item.getId(),invoiceId)){
                                                EventBus.getDefault().post(new EventCenter(Api.INVOICE_REFRESH,100));
                                            }
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
        Api.myInvoice(token, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                hideProgressDialog();
                Log.d("params","invoiceList = "+responseString);
                MyInvoiceListEntity mPlanSortEntity = AbGsonUtil.json2Bean(responseString, MyInvoiceListEntity.class);
                if (mPlanSortEntity.isOk()) {
                    onLoadDataSuccess(mPlanSortEntity.data);
                }
            }
        });
    }

    @OnClick({R.id.tv_add})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_add://新增地址
                startActivityForResult(new Intent(InvoiceActivity.this,AddInvoiceActivity.class),100);
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
