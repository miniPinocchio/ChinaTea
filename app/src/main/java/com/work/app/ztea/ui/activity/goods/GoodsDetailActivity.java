package com.work.app.ztea.ui.activity.goods;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.work.app.ztea.entity.BaseEntity;
import com.work.app.ztea.entity.GoodsDetailsEntity;
import com.work.app.ztea.entity.GoodsListEntity;
import com.work.app.ztea.entity.GoodsOrderEntity;
import com.work.app.ztea.entity.IdentifyEntity;
import com.work.app.ztea.entity.LoginEntity;
import com.work.app.ztea.http.Api;
import com.work.app.ztea.library.MyPagerAdapter;
import com.work.app.ztea.ui.activity.LoginActivity;
import com.work.app.ztea.utils.DateUtils;
import com.work.app.ztea.utils.HtmlUtils;
import com.work.app.ztea.utils.LayoutUtils;
import com.work.app.ztea.utils.TimeTools;
import com.work.app.ztea.utils.UserService;
import com.work.app.ztea.widget.IdeaScrollView;
import com.work.app.ztea.widget.IdeaViewPager;
import com.work.app.ztea.widget.SelfAdaptionRecycler;
import com.work.app.ztea.widget.URLImageParser;
import com.work.app.ztea.wxapi.WxUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * 商品详情
 */
public class GoodsDetailActivity extends BaseActivity implements TabLayout.OnTabSelectedListener, View.OnClickListener {

    @ViewInject(R.id.layout_toolbar)
    RelativeLayout layout_toolbar;
    @ViewInject(R.id.tbLayout)
    TabLayout tabLayout;
    @ViewInject(R.id.iv_shoucang)
    ImageView iv_shoucang;

    @ViewInject(R.id.layout_scroll_view)
    IdeaScrollView layout_scroll_view;
    //商品信息
    @ViewInject(R.id.view_banner)
    IdeaViewPager view_banner;
    @ViewInject(R.id.layout_top)
    RelativeLayout layout_top;
    @ViewInject(R.id.tv_goods_price)
    TextView tv_goods_price;
    @ViewInject(R.id.tv_jifen)
    TextView tv_jifen;
    @ViewInject(R.id.tv_sheng_price)
    TextView tv_sheng_price;
    @ViewInject(R.id.tv_huiyuan_price)
    TextView tv_huiyuan_price;
    @ViewInject(R.id.tv_goods_title)
    TextView tv_goods_title;
    @ViewInject(R.id.tv_goods_remark_1)
    TextView tv_goods_remark_1;
    @ViewInject(R.id.tv_goods_remark_2)
    TextView tv_goods_remark_2;
    @ViewInject(R.id.tv_goods_remark_3)
    TextView tv_goods_remark_3;
    @ViewInject(R.id.tv_guige_1)
    TextView tv_guige_1;
    @ViewInject(R.id.tv_guige_2)
    TextView tv_guige_2;
    @ViewInject(R.id.tv_guige_3)
    TextView tv_guige_3;
    @ViewInject(R.id.tv_guige_4)
    TextView tv_guige_4;
    @ViewInject(R.id.tv_guige_5)
    TextView tv_guige_5;
    @ViewInject(R.id.tv_guige_6)
    TextView tv_guige_6;
    @ViewInject(R.id.tv_guige_7)
    TextView tv_guige_7;
    @ViewInject(R.id.tv_guige_8)
    TextView tv_guige_8;
    @ViewInject(R.id.tv_guige_9)
    TextView tv_guige_9;
    @ViewInject(R.id.tv_guige_10)
    TextView tv_guige_10;
    //商品详情
    @ViewInject(R.id.layout_detail)
    RelativeLayout layout_detail;
    @ViewInject(R.id.tv_goods_detail)
    TextView tv_goods_detail;
    //商品详情
    @ViewInject(R.id.layout_commnet)
    RelativeLayout layout_commnet;
    @ViewInject(R.id.commnet_list)
    SelfAdaptionRecycler commnet_list;
    //积分相关
    @ViewInject(R.id.tv_jifen_button)
    TextView tv_jifen_button;
    @ViewInject(R.id.layout_gwc)
    LinearLayout layout_gwc;

    //认购相关
    @ViewInject(R.id.layout_subscribe)
    LinearLayout layout_subscribe;
    @ViewInject(R.id.tv_date)
    TextView tv_date;
    @ViewInject(R.id.tv_hour)
    TextView tv_hour;
    @ViewInject(R.id.tv_start_time)
    TextView tv_start_time;

    private int itemPoin;

    private RecyclerAdapter mAdapter = new RecyclerAdapter<GoodsListEntity.GoodsData.GoodsListBean>(APP.getInstance(), R.layout.item_comment_info) {
        @Override
        protected void convert(RecyclerAdapterHelper helper, final GoodsListEntity.GoodsData.GoodsListBean item) {
//            helper.setText(R.id.tv_price,"¥ "+ item.getPrice());
//            helper.setText(R.id.tv_name, item.getTitle());
//            helper.setText(R.id.tv_detail, item.getRemark());
//            Glide.with(mContext)
//                    .load(item.getImage())
////                            .error(R.drawable.banner)
//                    .into((ImageView) helper.getView(R.id.iv_good_pic));
//            helper.getItemView().setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
        }
    };
    private boolean isNeedScrollTo;
    private AlertDialog dialogImage;
    //商品id
    private String id;

    private int page = 0;
    //加入规格信息
    private ImageView iv_goods_img;
    private TextView tv_goods_name;
    private TextView tv_goods_num;
    private TextView tv_price;
    private TextView tv_num;
    //包装
    private String guigeBz;
    //库存
    private String stock;
    private String checkBuy;
    //商品id
    private String goodsId;
    //商品图
    private List<String> images = new ArrayList<>();
    private MyPagerAdapter mPageAdapter;
    private GoodsDetailsEntity.Details detailsBean;
    //商品


    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_goods_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        id = getIntent().getStringExtra("id");
        initTab();
        layout_scroll_view.setViewPager(view_banner,0);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initListener();
        commnet_list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        commnet_list.setAdapter(mAdapter);
        List<GoodsListEntity.GoodsData.GoodsListBean> goodsListBeans = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            goodsListBeans.add(new GoodsListEntity.GoodsData.GoodsListBean());
        }
        mAdapter.replaceAll(goodsListBeans);
        initLayout();

        showProgressDialog();
        netGoodsInfo();

    }

    private void initPhotoPager() {
        mPageAdapter = new MyPagerAdapter(images);
        view_banner.setAdapter(mPageAdapter);//第二步：设置viewpager适配器
    }

    private void initTab() {
        tabLayout.addTab(tabLayout.newTab().setText("商品"));
        tabLayout.addTab(tabLayout.newTab().setText("详情"));
        tabLayout.addTab(tabLayout.newTab().setText("评价"));
        LayoutUtils.reflex(tabLayout);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.MODE_SCROLLABLE);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.red));
        tabLayout.setTabTextColors(getResources().getColor(R.color.color_address_black), getResources().getColor(R.color.red));
        tabLayout.addOnTabSelectedListener(this);
    }

    /**
     * 规格布局
     */
    private void initLayout(){
        View view = LayoutInflater.from(this).inflate(R.layout.layout_guige_info, null);
        ImageView iv_close = view.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(this);
        iv_goods_img = view.findViewById(R.id.iv_goods_img);
        tv_goods_name = view.findViewById(R.id.tv_goods_name);
        tv_goods_num = view.findViewById(R.id.tv_goods_num);
        tv_price = view.findViewById(R.id.tv_price);

        ImageView iv_del_num = view.findViewById(R.id.iv_del_num);
        tv_num = view.findViewById(R.id.tv_num);
        ImageView iv_add_num = view.findViewById(R.id.iv_add_num);

        TextView tv_commit = view.findViewById(R.id.tv_commit);
        iv_del_num.setOnClickListener(this);
        iv_add_num.setOnClickListener(this);
        tv_commit.setOnClickListener(this);

        dialogImage = new AlertDialog.Builder(this, R.style.Dialog_FS).setView(view).create();
        dialogImage.setCancelable(true);
    }

    private void initListener() {
        layout_scroll_view.setOnSelectedIndicateChangedListener(new IdeaScrollView.OnSelectedIndicateChangedListener() {
            @Override
            public void onSelectedChanged(int position) {
                Log.d("params","position = "+position);
                isNeedScrollTo = false;
                if (itemPoin != 0 || position != 2){
                    tabLayout.getTabAt(position).select();
                }
                isNeedScrollTo = true;
                itemPoin = position;
            }
        });
        tabLayout.addOnTabSelectedListener(this);
    }

    public int getMeasureHeight(View view){
        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        return view.getMeasuredHeight();
    }

    /**
     * 显示活动套餐的弹框
     */
    private void showDialog() {
        dialogImage.show();

        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        WindowManager.LayoutParams params = dialogImage.getWindow().getAttributes();  //获取对话框当前的参数值、
        params.width = (int) (d.getWidth());    //宽度设置全屏宽度
        dialogImage.getWindow().setAttributes(params);     //设置生效
        dialogImage.getWindow().setGravity(Gravity.BOTTOM);
        dialogImage.getWindow().setWindowAnimations(R.style.dialogAnim);  //添加动画
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_close:
                dialogImage.dismiss();
                break;
            case R.id.iv_del_num:
                String numDel = tv_num.getText().toString().trim();
                if (TextUtils.equals(numDel,"1")){
                    showToast("不能再减了");
                    return;
                }
                tv_num.setText(String.valueOf(Integer.parseInt(numDel)-1));
                tv_goods_num.setText("数量："+String.valueOf(Integer.parseInt(numDel)-1)+guigeBz);
                break;
            case R.id.iv_add_num:
                String numAdd = tv_num.getText().toString().trim();
                if (TextUtils.equals(numAdd,stock)){
                    showToast("不能再加了");
                    return;
                }
                tv_num.setText(String.valueOf(Integer.parseInt(numAdd)+1));
                tv_goods_num.setText("数量："+String.valueOf(Integer.parseInt(numAdd)+1)+guigeBz);
                break;
            case R.id.tv_commit:
                LoginEntity.Login userInfo = UserService.getUserInfo();
                if (userInfo == null || TextUtils.isEmpty(userInfo.getToken())) {
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }
                if (TextUtils.equals(checkBuy,"1")){
                    addShopCar();
                    return;
                }
                netGoodsOrder();
                break;
        }
    }

    @OnClick({R.id.iv_shoucang,R.id.iv_fenxiang,R.id.iv_yuyin,R.id.iv_me_gwc,R.id.tv_add_gwc,R.id.tv_go_buy,R.id.tv_jifen_button})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_shoucang://收藏
                goodsFav();
                break;
            case R.id.iv_fenxiang://分享
                WxUtils.getInstance().showShare(this,detailsBean.getImage(),detailsBean.getLink(),detailsBean.getTitle(),TextUtils.isEmpty(detailsBean.getRemark())?"":detailsBean.getRemark());
                break;
            case R.id.iv_yuyin://语音

                break;
            case R.id.iv_me_gwc://购物车
                startActivity(new Intent(this,ShopCarActivity.class));
                break;
            case R.id.tv_add_gwc://加入购物车
                checkBuy = "1";
                showDialog();
                break;
            case R.id.tv_go_buy://立即购买
                checkBuy = "2";
                showDialog();
                break;
            case R.id.tv_jifen_button://积分兑换
                netGoodsOrder();
                break;
        }
    }

    /**
     * 商品收藏
     */
    private void goodsFav(){
        LoginEntity.Login userInfo = UserService.getUserInfo();
        if (userInfo == null || TextUtils.isEmpty(userInfo.getToken())){
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        Api.goodsFav(id,userInfo.getToken(),"2", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                hideProgressDialog();
                Log.d("params","goodsFav = "+responseString);
                IdentifyEntity detailsEntity = AbGsonUtil.json2Bean(responseString, IdentifyEntity.class);
                if (detailsEntity.isOk() && detailsEntity.data != null) {
                    showToast(TextUtils.equals(detailsEntity.data.status,"0")?"收藏成功":"取消收藏成功");
                    iv_shoucang.setImageResource(TextUtils.equals(detailsEntity.data.status,"0")?R.drawable.xwx:R.drawable.xxz);
                }else {
                    showToast(detailsEntity.msg);
                }
            }
        });
    }

    /**
     * 获取商品信息
     */
    private void netGoodsInfo(){
        LoginEntity.Login userInfo = UserService.getUserInfo();
        Api.getGoodsInfo(id, userInfo == null?"":userInfo.getToken(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                hideProgressDialog();
                Log.d("params","GoodsDetail = "+responseString);
                GoodsDetailsEntity detailsEntity = AbGsonUtil.json2Bean(responseString, GoodsDetailsEntity.class);
                if (detailsEntity.isOk() && detailsEntity.data != null) {
                    detailsBean = detailsEntity.data;
                    setUi(detailsEntity.data);
                    netCommentList();
                }
            }
        });
    }

    /**
     * 获取评论列表
     */
    private void netCommentList(){
        Api.getCommentList(id, page, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                hideProgressDialog();
                Log.d("params","commentList = "+responseString);
//                GoodsDetailsEntity detailsEntity = AbGsonUtil.json2Bean(responseString, GoodsDetailsEntity.class);
//                if (detailsEntity.isOk() && detailsEntity.data != null) {
//
//                }
            }
        });
    }

    /**
     * 加入购物车
     */
    private void addShopCar(){
        LoginEntity.Login userInfo = UserService.getUserInfo();
        if (userInfo == null) {
            return;
        }
        String numAdd = tv_num.getText().toString().trim();
        Api.addShopCar(userInfo.getToken(),goodsId, numAdd, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                hideProgressDialog();
                Log.d("params","addShopCar = "+responseString);
                BaseEntity detailsEntity = AbGsonUtil.json2Bean(responseString, BaseEntity.class);
                if (detailsEntity.isOk()) {
                    showToast("加入购物车成功");
                    dialogImage.dismiss();
                }
            }
        });
    }

    /**
     * 下订单
     */
    private void netGoodsOrder(){
        String goodsNum = tv_num.getText().toString().trim();
        LoginEntity.Login userInfo = UserService.getUserInfo();
        showProgressDialog();
        Api.orderSureOne(userInfo.getToken(), goodsId, goodsNum,new TextHttpResponseHandler() {
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
                    startActivity(new Intent(GoodsDetailActivity.this,GoodsBalanceActivity.class)
                            .putExtra("goodsOrder",detailsEntity.data));
                }else {
                    showToast(detailsEntity.msg);
                }
            }
        });
    }

    /**
     * 设置数据
     * @param details
     */
    private void setUi(final GoodsDetailsEntity.Details details){
        if (details != null) {
            //商品类型 1、普通商品 2、征订 3、认购 4、积分
            switch (details.getTypes()){
                case "1":

                    break;
                case "2":
                    break;
                case "3":
                    layout_subscribe.setVisibility(View.VISIBLE);
//剩余天数
                    long syTimeDate = Long.parseLong(details.getActivity_end()) - (System.currentTimeMillis()/1000);
                    String syTime = String.valueOf(syTimeDate/(24*60*60));
                    tv_date.setText("剩余"+syTime+"天" );
                    //开抢时间
                    if (System.currentTimeMillis()/1000 > Long.parseLong(details.getActivity_start())){
                        tv_start_time.setText("商品已开抢");
                    }else {
                        tv_start_time.setText(DateUtils.formatTimeToStringHourMinute(new Date(Long.parseLong(details.getActivity_start())*1000L))+"准时开抢");
                    }
                    //倒计时
                    long time =getTomorrowBegin()*1000L;
                    time = time - System.currentTimeMillis();
                    if (time > 0) {
                        CountDownTimer countDownTimer = new CountDownTimer(time, 1000) {
                            public void onTick(long millisUntilFinished) {
                                tv_hour.setText(TimeTools.getCountTimeByLong(millisUntilFinished));
                                Log.e("TAG", details.getTitle() + " :  " + millisUntilFinished);
                            }
                            public void onFinish() {
                                tv_hour.setText("00:00:00");
                            }
                        }.start();

                        countDownMap.put(tv_hour.hashCode(), countDownTimer);
                    } else {
                        tv_hour.setText("00:00:00");
                    }
                    break;
                case "4":
                    tv_jifen_button.setVisibility(View.VISIBLE);
                    layout_gwc.setVisibility(View.GONE);
                    break;
            }
            //图片
            images.clear();
            if (!TextUtils.isEmpty(details.getVideo_img())){
                images.add(details.getVideo_img());
            }
            images.addAll(details.getImages());
            initPhotoPager();
            //价格 标题等信息
            goodsId = details.getId();

            tv_goods_price.setVisibility(TextUtils.isEmpty(details.getPrice())?View.GONE:View.VISIBLE);
            tv_jifen.setVisibility(TextUtils.isEmpty(details.getIntegral())?View.GONE:View.VISIBLE);

            tv_goods_price.setText(details.getPrice());
            tv_jifen.setText(TextUtils.isEmpty(details.getPrice())?details.getIntegral()+"积分":"+"+details.getIntegral()+"积分");

            tv_sheng_price.setText(details.getPromotion1());
            tv_huiyuan_price.setText(details.getPromotion2());
            tv_huiyuan_price.setVisibility(TextUtils.isEmpty(details.getPromotion2())?View.GONE:View.VISIBLE);
            tv_goods_title.setText(details.getTitle());
            tv_goods_remark_1.setText(TextUtils.equals(details.getIs_free_ship(),"1")?"快递：免邮":"邮费："+details.getBuyer_shipmoney());
            tv_goods_remark_2.setText(details.getPromotion3());
            stock = details.getStock();
            tv_goods_remark_3.setText("库存："+details.getStock());
            //商品规格信息
            tv_guige_1.setText(details.getBrand());
            tv_guige_2.setText(details.getGuige());
            tv_guige_3.setText(details.getPackageX());
            tv_guige_4.setText(details.getRaw_material());
            tv_guige_5.setText(details.getProduct_ownership());
            tv_guige_6.setText(DateUtils.formatTimeToStringYear(new Date(Long.parseLong(details.getProduction_date())*1000L)));
            tv_guige_7.setText(details.getShelf_life());
            tv_guige_8.setText(details.getPlace_of_origin());
            tv_guige_9.setText(details.getNumber());
            tv_guige_10.setText(details.getStorage_conditions());
            //设置弹框数据
            Glide.with(mContext)
                    .load(details.getImage_small())
                    .into(iv_goods_img);
            tv_goods_name.setText(details.getTitle());
            guigeBz = details.getGuige();
            tv_goods_num.setText("数量：1"+guigeBz);
            tv_price.setText("¥ "+details.getPrice());
            //商品详情
            tv_goods_detail.setText(HtmlUtils.getHtml(getApplicationContext(), tv_goods_detail, details.getContent(), new URLImageParser.OnImageLoadSuccessImp() {
                @Override
                public void imgLoadSuccess() {
                    setHeightData();
                }
            }));
            if (!details.getContent().contains("img")){
                setHeightData();
            }
            //是否收藏
            iv_shoucang.setImageResource(TextUtils.equals(details.getIs_favorites(),"0")?R.drawable.xwx:R.drawable.xxz);
        }
    }

    private void setHeightData(){
        ArrayList<Integer> araryDistance = new ArrayList<>();
        araryDistance.add(0);
        araryDistance.add(getMeasureHeight(layout_top)+getMeasureHeight(layout_toolbar));
        araryDistance.add(getMeasureHeight(layout_top)+getMeasureHeight(layout_detail)+getMeasureHeight(layout_toolbar));
        layout_scroll_view.setArrayDistance(araryDistance);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        if(isNeedScrollTo){
            layout_scroll_view.setPosition(position);
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelAllTimers();
    }
}
