package com.work.app.ztea.ui.activity.goods;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
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
import com.work.app.ztea.http.Api;
import com.work.app.ztea.utils.DateUtils;
import com.work.app.ztea.utils.TimeTools;

import java.util.Date;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cz.msebera.android.httpclient.Header;

/**
 * 认购专区
 */
public class SubscribeActivity extends BaseRecyclerViewRefreshActivity {

    @ViewInject(R.id.home_banner)
    BGABanner mBanner;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_subscribe;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        setTopTitle("认购专区");
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
        mAdapter = new RecyclerAdapter<GoodsListEntity.GoodsData.GoodsListBean>(APP.getInstance(), R.layout.item_subscribe_info) {
            @Override
            protected void convert(final RecyclerAdapterHelper helper, final GoodsListEntity.GoodsData.GoodsListBean item) {
                helper.setText(R.id.tv_price, TextUtils.isEmpty(item.getPrice())?"":"¥ "+ item.getPrice());
                helper.setText(R.id.tv_name, item.getTitle());
                helper.setText(R.id.tv_detail, item.getRemark());

                helper.setVisible(R.id.tv_detail,TextUtils.isEmpty(item.getRemark())?View.GONE:View.VISIBLE);
                Glide.with(mContext)
                        .load(item.getImage())
//                            .error(R.drawable.banner)
                        .into((ImageView) helper.getView(R.id.iv_goods_img));
                helper.setVisible(R.id.tv_btn_1,TextUtils.equals(item.getIs_sample(),"0")?View.GONE:View.VISIBLE);
                //时间相关
                //剩余天数
                long syTimeDate = Long.parseLong(item.getActivity_end()) - (System.currentTimeMillis()/1000);
                String syTime = String.valueOf(syTimeDate/(24*60*60));
                helper.setText(R.id.tv_date,"剩余"+syTime+"天" );
                //开抢时间
                if (System.currentTimeMillis()/1000 > Long.parseLong(item.getActivity_start())){
                    helper.setText(R.id.tv_start_time, "商品已开抢");
                }else {
                    helper.setText(R.id.tv_start_time, DateUtils.formatTimeToStringHourMinute(new Date(Long.parseLong(item.getActivity_start())*1000L))+"准时开抢");
                }
                //倒计时
                long time =getTomorrowBegin()*1000L;
                time = time - System.currentTimeMillis();
                if (time > 0) {
                    CountDownTimer countDownTimer = new CountDownTimer(time, 1000) {
                        public void onTick(long millisUntilFinished) {
                            helper.setText(R.id.tv_hour, TimeTools.getCountTimeByLong(millisUntilFinished));
                            Log.e("TAG", item.getTitle() + " :  " + millisUntilFinished);
                        }
                        public void onFinish() {
                            helper.setText(R.id.tv_hour,"00:00:00");
                        }
                    }.start();

                    countDownMap.put(helper.getView(R.id.tv_hour).hashCode(), countDownTimer);
                } else {
                    helper.setText(R.id.tv_hour,"00:00:00");
                }


                helper.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(SubscribeActivity.this,GoodsDetailActivity.class).putExtra("id",item.getId()));
                    }
                });
            }
        };
    }

    /**
     * 获取明天的凌晨12点时间戳
     * @return
     */
    public long getTomorrowBegin() {
        long now = System.currentTimeMillis() / 1000;
        long daySecond = 60 * 60 * 24;
        long dayTime = now - (now + 8 * 3600) % daySecond + daySecond;
        return dayTime;
    }

    //用于退出activity,避免countdown，造成资源浪费。
    private SparseArray<CountDownTimer> countDownMap = new SparseArray<>();

    /**
     * 清空资源
     */
    public void cancelAllTimers() {
        if (countDownMap == null) {
            return;
        }
        Log.e("TAG",  "size :  " + countDownMap.size());
        for (int i = 0,length = countDownMap.size(); i < length; i++) {
            CountDownTimer cdt = countDownMap.get(countDownMap.keyAt(i));
            if (cdt != null) {
                cdt.cancel();
            }
        }
    }

    @Override
    protected void loadData() {
        Api.getSubscribeList(mPage, new TextHttpResponseHandler() {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelAllTimers();
    }
}
