package com.work.app.ztea.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dream.library.utils.AbGsonUtil;
import com.dream.library.utils.annotation.annotation.ViewInject;
import com.dream.library.utils.annotation.annotation.event.OnClick;
import com.dream.library.widgets.XListView;
import com.loopj.android.http.TextHttpResponseHandler;
import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;
import com.work.app.ztea.APP;
import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseFragment;
import com.work.app.ztea.entity.HomeGoodsEntity;
import com.work.app.ztea.entity.LoginEntity;
import com.work.app.ztea.entity.RegisterServerEntity;
import com.work.app.ztea.http.Api;
import com.work.app.ztea.ui.activity.LoginActivity;
import com.work.app.ztea.ui.activity.WebViewActivity;
import com.work.app.ztea.ui.activity.goods.GoodsDetailActivity;
import com.work.app.ztea.ui.activity.goods.GoodsListActivity;
import com.work.app.ztea.ui.activity.goods.IntegralGoodsActivity;
import com.work.app.ztea.ui.activity.goods.ShopCarActivity;
import com.work.app.ztea.ui.activity.goods.SubscribeActivity;
import com.work.app.ztea.utils.UserService;
import com.work.app.ztea.utils.WebUtils;
import com.work.app.ztea.widget.SelfAdaptionRecycler;

import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cz.msebera.android.httpclient.Header;

/**
 * 商城
 * A simple {@link Fragment} subclass.
 */
public class ShoppingFragment extends BaseFragment {

    @ViewInject(R.id.tv_address)
    TextView tv_address;

    @ViewInject(R.id.home_banner)
    BGABanner mBanner;

    @ViewInject(R.id.list_category)
    SelfAdaptionRecycler list_category;

    @ViewInject(R.id.list_tags)
    SelfAdaptionRecycler list_tags;

    public ShoppingFragment() {
        // Required empty public constructor
    }

    private RecyclerAdapter mAdapter = new RecyclerAdapter<HomeGoodsEntity.Goods.CategoryBean>(APP.getInstance(), R.layout.item_goods_category) {
        @Override
        protected void convert(RecyclerAdapterHelper helper, final HomeGoodsEntity.Goods.CategoryBean item) {
            helper.setText(R.id.tv_title, item.getName());
            Glide.with(mContext)
                    .load(item.getImg_url())
//                            .error(R.drawable.banner)
                    .into((ImageView) helper.getView(R.id.iv_pic));
            helper.getItemView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(),GoodsListActivity.class)
                            .putExtra("id",item.getId()).putExtra("title",item.getName()));
                }
            });
        }
    };

    private RecyclerAdapter mAdapterTags = new RecyclerAdapter<HomeGoodsEntity.Goods.TagsBean>(APP.getInstance(), R.layout.item_goods_tag) {
        @Override
        protected void convert(RecyclerAdapterHelper helper, final HomeGoodsEntity.Goods.TagsBean item) {
            helper.setText(R.id.tv_title, item.getName());
            helper.setText(R.id.tv_content, item.getRemark());
            List<HomeGoodsEntity.Goods.TagsBean.GoodsBean> itemGoods = item.getGoods();

            RecyclerAdapter mAdapterGoods = new RecyclerAdapter<HomeGoodsEntity.Goods.TagsBean.GoodsBean>(APP.getInstance(), R.layout.item_home_goods_info) {
                @Override
                protected void convert(RecyclerAdapterHelper helper, final HomeGoodsEntity.Goods.TagsBean.GoodsBean item) {
                    helper.setText(R.id.tv_price,"¥ "+ item.getPrice());
                    helper.setText(R.id.tv_name, item.getTitle());
                    helper.setText(R.id.tv_detail, item.getRemark());
                    Glide.with(mContext)
                            .load(item.getImage())
//                            .error(R.drawable.banner)
                            .into((ImageView) helper.getView(R.id.iv_good_pic));
                    helper.getItemView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getContext(),GoodsDetailActivity.class).putExtra("id",item.getId()));
                        }
                    });
                }
            };
            ((SelfAdaptionRecycler)helper.getView(R.id.view_goods_list)).setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            ((SelfAdaptionRecycler)helper.getView(R.id.view_goods_list)).setAdapter(mAdapterGoods);
            mAdapterGoods.replaceAll(itemGoods);
        }
    };


    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_shopping;
    }

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        list_category.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false));
        list_category.setAdapter(mAdapter);

        list_tags.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        list_tags.setAdapter(mAdapterTags);
    }

    @Override
    protected void initData(View view, @Nullable Bundle savedInstanceState) {
        getHomeGoods();
    }

    @OnClick({R.id.tv_address,R.id.layout_search,R.id.iv_search,R.id.iv_shop_car,
            R.id.tv_rengou,R.id.tv_jifen})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_address://定位地址

                break;
            case R.id.layout_search://搜索

                break;
            case R.id.iv_search://扫一扫

                break;
            case R.id.iv_shop_car://购物车
                LoginEntity.Login userInfo = UserService.getUserInfo();
                if (userInfo == null || TextUtils.isEmpty(userInfo.getToken())){
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getContext(), ShopCarActivity.class));

                break;
            case R.id.tv_rengou://认购专区
                startActivity(new Intent(getContext(), SubscribeActivity.class));
                break;
            case R.id.tv_jifen://积分商城
                startActivity(new Intent(getContext(), IntegralGoodsActivity.class));
                break;
        }
    }

    /**
     * 商城首页
     */
    private void getHomeGoods(){
        showProgressDialog();
        Api.getHomeGoods(new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
                showToast("请求失败");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i("params", "用户协议"+responseString);
                hideProgressDialog();
                HomeGoodsEntity entity = AbGsonUtil.json2Bean(responseString, HomeGoodsEntity.class);
                if (entity.isOk() && entity.data != null ) {
                    //设置轮播图
                    if (entity.data.getSlideShow() != null) {
                        setBanner(entity.data.getSlideShow());
                    }
                    //设置中间选项
                    if (entity.data.getCategory() != null) {
                        mAdapter.clear();
                        mAdapter.replaceAll(entity.data.getCategory());
                    }
                    //设置推荐数据
                    if (entity.data.getTags() != null){
                        mAdapterTags.clear();
                        mAdapterTags.replaceAll(entity.data.getTags());
                    }
                } else {
                    showToast(entity.msg);
                }
            }
        });
    }

    public void setBanner(final List<HomeGoodsEntity.Goods.SlideShowBean> list) {
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
}
