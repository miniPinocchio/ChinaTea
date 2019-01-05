package com.work.app.ztea.ui.activity.goods;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;
import com.work.app.ztea.APP;
import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseActivity;
import com.work.app.ztea.base.BaseRecyclerViewRefreshActivity;
import com.work.app.ztea.entity.GoodsListEntity;
import com.work.app.ztea.http.Api;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cz.msebera.android.httpclient.Header;

/**
 * 商品列表
 */
public class GoodsListActivity extends BaseRecyclerViewRefreshActivity {

    @ViewInject(R.id.tv_check_1)
    TextView tv_check_1;

    @ViewInject(R.id.tv_check_2)
    TextView tv_check_2;

    @ViewInject(R.id.tv_check_3)
    TextView tv_check_3;

    @ViewInject(R.id.tv_check_4)
    TextView tv_check_4;
    private String id;
    private String title;

    private int production_date = 0;//年份（排序），1降序、2升序
    private int price = 0;//价格（排序），1降序、2升序
    private int crdate = 0;//新品（排序），1降序、2升序

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_goods_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        setVisibleLeft(true);
        initPullRefreshAndLoadMore();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        setTopTitle(title);
        showProgressDialog();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
    }

    @Override
    protected void initAdapter() {
        mAdapter = new RecyclerAdapter<GoodsListEntity.GoodsData.GoodsListBean>(APP.getInstance(), R.layout.item_goods_list_info) {
            @Override
            protected void convert(RecyclerAdapterHelper helper, final GoodsListEntity.GoodsData.GoodsListBean item) {
                helper.setText(R.id.tv_price,"¥ "+ item.getPrice());
                helper.setText(R.id.tv_name, item.getTitle());
                helper.setText(R.id.tv_detail, item.getRemark());
                helper.setVisible(R.id.tv_detail, TextUtils.isEmpty(item.getRemark())?View.GONE:View.VISIBLE);
                Glide.with(mContext)
                        .load(item.getImage())
//                            .error(R.drawable.banner)
                        .into((ImageView) helper.getView(R.id.iv_good_pic));
                helper.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(GoodsListActivity.this,GoodsDetailActivity.class).putExtra("id",item.getId()));
                    }
                });
            }
        };
    }

    @Override
    protected void loadData() {
        Api.getGoodsList(id, mPage,production_date,price,crdate, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                hideProgressDialog();
                Log.d("params","GoodsList = "+responseString);
                GoodsListEntity mPlanSortEntity = AbGsonUtil.json2Bean(responseString, GoodsListEntity.class);
                if (mPlanSortEntity.isOk()) {
                    onLoadDataSuccess(mPlanSortEntity.data.getGoods_list());
                }
            }
        });
    }

    @OnClick({R.id.tv_check_1,R.id.tv_check_2,R.id.tv_check_3,R.id.tv_check_4})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_check_1://综合
                setUI(1);
                break;
            case R.id.tv_check_2://年份
                setUI(2);
                break;
            case R.id.tv_check_3://新品
                setUI(3);
                break;
            case R.id.tv_check_4://价格
                setUI(4);
                break;
        }
    }

    private void setUI(int i) {
        switch (i){
            case 1:
                production_date = 0;
                price = 0;
                crdate = 0;
                tv_check_1.setTextColor(getResources().getColor(R.color.orange));
                tv_check_2.setTextColor(getResources().getColor(R.color.color_address_black));
                tv_check_3.setTextColor(getResources().getColor(R.color.color_address_black));
                tv_check_4.setTextColor(getResources().getColor(R.color.color_address_black));
                break;
            case 2:
                production_date = (production_date == 0?1:production_date == 1?2:1);
                price = 0;
                crdate = 0;
                tv_check_1.setTextColor(getResources().getColor(R.color.color_address_black));
                tv_check_2.setTextColor(getResources().getColor(R.color.orange));
                tv_check_3.setTextColor(getResources().getColor(R.color.color_address_black));
                tv_check_4.setTextColor(getResources().getColor(R.color.color_address_black));
                break;
            case 3:
                crdate = (crdate == 0?1:crdate == 1?2:1);
                production_date = 0;
                price = 0;
                tv_check_1.setTextColor(getResources().getColor(R.color.color_address_black));
                tv_check_2.setTextColor(getResources().getColor(R.color.color_address_black));
                tv_check_3.setTextColor(getResources().getColor(R.color.orange));
                tv_check_4.setTextColor(getResources().getColor(R.color.color_address_black));
                break;
            case 4:
                price = (price == 0?1:price == 1?2:1);
                production_date = 0;
                crdate = 0;
                tv_check_1.setTextColor(getResources().getColor(R.color.color_address_black));
                tv_check_2.setTextColor(getResources().getColor(R.color.color_address_black));
                tv_check_3.setTextColor(getResources().getColor(R.color.color_address_black));
                tv_check_4.setTextColor(getResources().getColor(R.color.orange));
                break;
        }
        mBGARefreshLayout.beginRefreshing();
    }

    @Override
    protected boolean isLoadingMoreEnabled() {
        return true;
    }
}
