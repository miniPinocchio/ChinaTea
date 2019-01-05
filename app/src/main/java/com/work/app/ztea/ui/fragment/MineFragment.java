package com.work.app.ztea.ui.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import com.work.app.ztea.base.BaseFragment;
import com.work.app.ztea.entity.LoginEntity;
import com.work.app.ztea.entity.MineHomeEntity;
import com.work.app.ztea.entity.MineListBean;
import com.work.app.ztea.http.Api;
import com.work.app.ztea.ui.activity.mine.IdentityActivity;
import com.work.app.ztea.ui.activity.mine.InvoiceActivity;
import com.work.app.ztea.ui.activity.mine.MemberActivity;
import com.work.app.ztea.ui.activity.mine.MineAddressActivity;
import com.work.app.ztea.ui.activity.mine.MineCodeActivity;
import com.work.app.ztea.ui.activity.mine.MineOrderActivity;
import com.work.app.ztea.ui.activity.mine.OrderStatisActivity;
import com.work.app.ztea.ui.activity.mine.ProxyOrderActivity;
import com.work.app.ztea.ui.activity.mine.SettingActivity;
import com.work.app.ztea.ui.activity.mine.UserInfoActivity;
import com.work.app.ztea.utils.UserService;
import com.work.app.ztea.widget.SelfAdaptionRecycler;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment {

    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.tv_right)
    TextView tv_right;

    @ViewInject(R.id.mCircleImageView)
    CircleImageView mCircleImageView;//头像
    @ViewInject(R.id.tv_name)
    TextView tv_name;//姓名
    @ViewInject(R.id.tv_shiming)
    TextView tv_shiming;//实名
    @ViewInject(R.id.tv_huiyuan_level)
    TextView tv_huiyuan_level;//会员等级
    @ViewInject(R.id.tv_huiyuan_id)
    TextView tv_huiyuan_id;//会员id

    @ViewInject(R.id.tv_price)
    TextView tv_price;//账户余额
    @ViewInject(R.id.tv_jifen)
    TextView tv_jifen;//积分
    @ViewInject(R.id.tv_chaban)
    TextView tv_chaban;//茶伴

    @ViewInject(R.id.tv_send_vip)
    TextView tv_send_vip;//申请尊金会员

    @ViewInject(R.id.view_list)
    SelfAdaptionRecycler view_list;//列表

    private MineHomeEntity.Mine mine;

    public MineFragment() {
        // Required empty public constructor
    }

    private List<MineListBean> entityList = new ArrayList<>();

    private RecyclerAdapter mAdapter = new RecyclerAdapter<MineListBean>(APP.getInstance(), R.layout.item_mine_list_info) {
        @Override
        protected void convert(final RecyclerAdapterHelper helper, final MineListBean item) {
            helper.setText(R.id.tv_mine_name, item.getName());
            TextView textDrawable = helper.getView(R.id.tv_mine_name);
            Drawable drawableTop = getResources().getDrawable(
                    item.getResource());
            textDrawable.setCompoundDrawablesWithIntrinsicBounds(null,
                    drawableTop, null, null);
            textDrawable.setCompoundDrawablePadding(5);
            helper.getItemView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (item.getName()){
                        case "地址管理":
                            startActivity(new Intent(getContext(),MineAddressActivity.class));
                            break;
                        case "申请开票":
                            startActivity(new Intent(getContext(),InvoiceActivity.class));
                            break;
                        case "我的推广码":
                            startActivity(new Intent(getContext(),MineCodeActivity.class));
                            break;
                        case "我的收藏":
                            break;
                        case "代理订单":
                            startActivity(new Intent(getContext(),ProxyOrderActivity.class));
                            break;
                        case "订单统计":
                            startActivity(new Intent(getContext(),OrderStatisActivity.class));
                            break;
                        case "我的明细":
                            break;
                        case "产品图鉴":
                            break;
                        case "我的存茶":
                            break;
                        case "授权查询":
                            break;
                        case "防伪查询":
                            break;
                        case "我的评论":
                            break;
                        case "联系客服":
                            break;
                            default:
                                break;
                    }
                }
            });
        }
    };

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view, @Nullable Bundle savedInstanceState) {
        tv_title.setText("个人中心");
        tv_right.setVisibility(View.VISIBLE);
        Drawable drawableLeft = getResources().getDrawable(
                R.drawable.shezhi);
        tv_right.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,
                null, null, null);
    }

    @Override
    protected void initData(View view, @Nullable Bundle savedInstanceState) {
        view_list.setLayoutManager(new StaggeredGridLayoutManager(4,LinearLayoutManager.VERTICAL));
        view_list.setAdapter(mAdapter);
        setData();
        getUserInfo();
    }


    @OnClick({R.id.tv_right,R.id.iv_edit_user,R.id.tv_shiming,R.id.layout_zh,
            R.id.tv_send_vip,R.id.layout_all_order,
            R.id.tv_order_1,R.id.tv_order_2,R.id.tv_order_3,R.id.tv_order_4})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right://设置
                startActivity(new Intent(getContext(),SettingActivity.class));
                break;
            case R.id.iv_edit_user://编辑用户
                startActivity(new Intent(getContext(), UserInfoActivity.class));
                break;
            case R.id.tv_shiming://实名认证
                startActivity(new Intent(getContext(), IdentityActivity.class));
                break;
            case R.id.layout_zh://账户余额
                startActivity(new Intent(getContext(), MemberActivity.class).putExtra("bean",mine));
                break;
            case R.id.tv_send_vip://申请尊金会员

                break;
            case R.id.layout_all_order://全部订单
                startActivity(new Intent(getContext(), MineOrderActivity.class).putExtra("page",0));
                break;
            case R.id.tv_order_1://认购订单
                startActivity(new Intent(getContext(), MineOrderActivity.class).putExtra("page",1));
                break;
            case R.id.tv_order_2://待支付
                startActivity(new Intent(getContext(), MineOrderActivity.class).putExtra("page",2));
                break;
            case R.id.tv_order_3://待收货
                startActivity(new Intent(getContext(), MineOrderActivity.class).putExtra("page",4));
                break;
            case R.id.tv_order_4://待评价
                startActivity(new Intent(getContext(), MineOrderActivity.class).putExtra("page",5));
                break;
        }
    }

    /**
     * 个人中心首页
     */
    private void getUserInfo(){
        LoginEntity.Login userInfo = UserService.getUserInfo();
        showProgressDialog();
        Api.getMineInfo(userInfo.getToken(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                hideProgressDialog();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                hideProgressDialog();
                Log.d("params","UserInfo = "+responseString);
                MineHomeEntity detailsEntity = AbGsonUtil.json2Bean(responseString, MineHomeEntity.class);
                if (detailsEntity.isOk() && detailsEntity.data != null) {
                    mine = detailsEntity.data;
                    Glide.with(mContext)
                            .load(mine.getImage())
//                            .error(R.drawable.banner)
                            .into(mCircleImageView);
                    tv_name.setText(mine.getName());
                    tv_shiming.setText(TextUtils.isEmpty(mine.getIdcard())?"前往实名":"已实名认证");
                    if (!TextUtils.isEmpty(mine.getIdcard())){
                        Drawable sm=getResources().getDrawable(R.drawable.yishiming);
                        sm.setBounds(0, 0, sm.getMinimumWidth(), sm.getMinimumHeight());
                        tv_shiming.setCompoundDrawables(sm, null, null, null);
                    }
                    tv_huiyuan_level.setText(mine.getLevelname());
                    Drawable sm=getResources().getDrawable(R.drawable.zunjin);
                    switch (mine.getLevel()){
                        case "1":
                            sm=getResources().getDrawable(R.drawable.zunjin);
                            break;
                        case "2":
                            sm=getResources().getDrawable(R.drawable.zunxiang);
                            break;
                        case "3":
                            sm=getResources().getDrawable(R.drawable.zunzuan);
                            break;
                    }
                    sm.setBounds(0, 0, sm.getMinimumWidth(), sm.getMinimumHeight());
                    tv_huiyuan_level.setCompoundDrawables(sm, null, null, null);

                    tv_huiyuan_id.setText("ID："+ mine.getNumber());
                    tv_price.setText(mine.getAmount());
                    tv_jifen.setText(mine.getIntegral());
                    tv_chaban.setText(mine.getRecommend_num());
                }
            }
        });
    }

    private void setData(){
        MineListBean listEntity = new MineListBean();
        listEntity.setName("代理订单");
        listEntity.setResource(R.drawable.dldd);
        entityList.add(listEntity);
        listEntity = new MineListBean();
        listEntity.setName("订单统计");
        listEntity.setResource(R.drawable.ddtj);
        entityList.add(listEntity);
        listEntity = new MineListBean();
        listEntity.setName("我的明细");
        listEntity.setResource(R.drawable.wdmx);
        entityList.add(listEntity);
        listEntity = new MineListBean();
        listEntity.setName("产品图鉴");
        listEntity.setResource(R.drawable.cptj);
        entityList.add(listEntity);
        listEntity = new MineListBean();
        listEntity.setName("我的存茶");
        listEntity.setResource(R.drawable.wdcc);
        entityList.add(listEntity);
        listEntity = new MineListBean();
        listEntity.setName("我的推广码");
        listEntity.setResource(R.drawable.wdtgm);
        entityList.add(listEntity);
        listEntity = new MineListBean();
        listEntity.setName("授权查询");
        listEntity.setResource(R.drawable.sqcx);
        entityList.add(listEntity);
        listEntity = new MineListBean();
        listEntity.setName("防伪查询");
        listEntity.setResource(R.drawable.fwcx);
        entityList.add(listEntity);
        listEntity = new MineListBean();
        listEntity.setName("申请开票");
        listEntity.setResource(R.drawable.sqkp);
        entityList.add(listEntity);
        listEntity = new MineListBean();
        listEntity.setName("我的评论");
        listEntity.setResource(R.drawable.wdpl);
        entityList.add(listEntity);
        listEntity = new MineListBean();
        listEntity.setName("我的收藏");
        listEntity.setResource(R.drawable.wdsc);
        entityList.add(listEntity);
        listEntity = new MineListBean();
        listEntity.setName("地址管理");
        listEntity.setResource(R.drawable.dzgl);
        entityList.add(listEntity);
        listEntity = new MineListBean();
        listEntity.setName("联系客服");
        listEntity.setResource(R.drawable.lxkf);
        entityList.add(listEntity);
        mAdapter.replaceAll(entityList);
    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {
        super.onEventComing(eventCenter);
        if (eventCenter.getEventCode() == Api.RECHARGE_OK) {//充值成功
            getUserInfo();
        }else if (eventCenter.getEventCode() == Api.PAY_SUCCESS){
//            startActivity(new Intent(GoodsBalanceActivity.this,PayResultActivity.class));
        }
    }

    @Override
    protected boolean isBindEventBus() {
        return true;
    }
}
