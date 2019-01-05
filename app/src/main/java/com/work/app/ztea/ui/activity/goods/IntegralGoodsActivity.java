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
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dream.library.utils.AbGsonUtil;
import com.dream.library.utils.annotation.annotation.ViewInject;
import com.loopj.android.http.TextHttpResponseHandler;
import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;
import com.work.app.ztea.APP;
import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseRecyclerViewRefreshActivity;
import com.work.app.ztea.entity.GoodsListEntity;
import com.work.app.ztea.entity.HomeGoodsEntity;
import com.work.app.ztea.http.Api;

import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cz.msebera.android.httpclient.Header;

/**
 * 积分商城
 */
public class IntegralGoodsActivity extends BaseRecyclerViewRefreshActivity {

    @ViewInject(R.id.home_banner)
    BGABanner mBanner;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_integral_goods;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        setTopTitle("积分商城");
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initPullRefreshAndLoadMore();
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
    }

    @Override
    protected void initAdapter() {
        mAdapter = new RecyclerAdapter<GoodsListEntity.GoodsData.GoodsListBean>(APP.getInstance(), R.layout.item_goods_list_info) {
            @Override
            protected void convert(RecyclerAdapterHelper helper, final GoodsListEntity.GoodsData.GoodsListBean item) {
                helper.setText(R.id.tv_price, TextUtils.isEmpty(item.getPrice())?"":"¥ "+ item.getPrice()+(TextUtils.isEmpty(item.getIntegral())?"":"+"));
                helper.setText(R.id.tv_jifen,TextUtils.isEmpty(item.getIntegral())?"":item.getIntegral()+"积分");
                helper.setVisible(R.id.tv_jifen,TextUtils.isEmpty(item.getIntegral())?View.GONE:View.VISIBLE);
                helper.setText(R.id.tv_name, item.getTitle());
                helper.setText(R.id.tv_detail, item.getRemark());
                helper.setVisible(R.id.tv_detail,TextUtils.isEmpty(item.getRemark())?View.GONE:View.VISIBLE);
                Glide.with(mContext)
                        .load(item.getImage())
//                            .error(R.drawable.banner)
                        .into((ImageView) helper.getView(R.id.iv_good_pic));
                helper.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(IntegralGoodsActivity.this,GoodsDetailActivity.class).putExtra("id",item.getId()));
                    }
                });
            }
        };
    }

    @Override
    protected void loadData() {
        Api.getIntegralList(mPage, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                hideProgressDialog();
                Log.d("params","ntegralList = "+responseString);
                GoodsListEntity mPlanSortEntity = AbGsonUtil.json2Bean(responseString, GoodsListEntity.class);
                if (mPlanSortEntity.isOk()) {
                    //设置轮播图
                    if (mPlanSortEntity.data.getSlideShow() != null) {
                        setBanner(mPlanSortEntity.data.getSlideShow());
                    }
                    onLoadDataSuccess(mPlanSortEntity.data.getGoods_list());
                }
            }
        });
    }

    public void setBanner(final List<GoodsListEntity.GoodsData.SlideShowBean> list) {
        ViewGroup.LayoutParams lp = mBanner.getLayoutParams();
        if (list.size() == 1) {
            mBanner.setAutoPlayAble(false);
        } else {
            mBanner.setAutoPlayAble(true);
        }
        mBanner.setLayoutParams(lp);
        mBanner.setAdapter(new BGABanner.Adapter() {
            @Override
            public void fillBannerItem(final BGABanner banner, View view, Object model, final int position) {
                if (view instanceof ImageView) {
                    final String banners = list.get(position).getImage();
                    ImageView imageView = (ImageView) view;
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    Glide.with(mContext)
                            .load(banners)
//                            .error(R.drawable.banner)
                            .into(imageView);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
            }
        });
        mBanner.setData(list, null);
    }

    @Override
    protected boolean isLoadingMoreEnabled() {
        return true;
    }
}
