package com.work.app.ztea.wxapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.work.app.ztea.APP;
import com.work.app.ztea.R;
import com.work.app.ztea.entity.OrderDataEntity;
import com.work.app.ztea.utils.CustomUtils;
import com.work.app.ztea.utils.MD5;
import com.work.app.ztea.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Administrator on 2017/2/23.
 */

public class WxUtils {

    private static final String TAG = "WxUtils";
    public final static String WXAppId = "wx8ca5e4be3f8e4d76";
    private final static int WX_CIRCLE_OF_FRENDS = 0;
    private final static int WX_FRENDS = 1;
    private static IWXAPI api = null;
    private static WxUtils wxUtils = null;
    public static String appName = "";

    private WxUtils() {
        api = WXAPIFactory.createWXAPI(APP.getInstance().getApplicationContext(), WXAppId, true);
        api.registerApp(WXAppId);
    }

    public static WxUtils getInstance() {
        if (wxUtils == null) {
            wxUtils = new WxUtils();
        }
        return wxUtils;
    }

    /**
     * 判断微信有没有被安装
     */
    public boolean isWXAppInstalledAndSupported() {
        boolean sIsWXAppInstalledAndSupported = api.isWXAppInstalled();
        return sIsWXAppInstalledAndSupported;
    }

    /**
     * 调用微信分享朋友圈
     */
    public void showShareFrends(String url, String appName, String descripe) {
        if (isWXAppInstalledAndSupported()) {
            wxShare(WX_FRENDS, url, appName, descripe);
        } else {
            ToastUtils.showMessage("您还没有安装微信");
        }
    }

    /**
     * 调用微信分享好友
     */
    public void showShareCircleOfFrends(String url, String appName, String descripe) {
        if (isWXAppInstalledAndSupported()) {
            wxShare(WX_CIRCLE_OF_FRENDS, url, appName, descripe);
        } else {
            ToastUtils.showMessage("您还没有安装微信");
        }
    }

    /**
     * 微信分享
     */
    private void wxShare(int flag, String url, String appName, String descripe) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = appName;
        msg.description = descripe;
//      这里替换一张自己工程里的图片资源
        Bitmap thumb = BitmapFactory.decodeResource(APP.getInstance().getResources() , R.mipmap.ic_launcher);
        msg.setThumbImage(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }

    public void showShare(Context context,String imgUrl,String url, String title, String content) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，微信、QQ和QQ空间等平台使用
        oks.setTitle(TextUtils.isEmpty(title)?"标题信息":title);
        // titleUrl QQ和QQ空间跳转链接
//        oks.setTitleUrl(TextUtils.isEmpty(qrCodeBean.getShare())?"":qrCodeBean.getShare());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(TextUtils.isEmpty(content)?"请输入要分享的内容":content);
//        oks.setText("请输入要分享的内容");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        if (!TextUtils.isEmpty(qrCodeBean.getImage())){
//            oks.setImageUrl(qrCodeBean.getImage());//确保SDcard下面存在此张图片
//        }else {
//            oks.setImageUrl("");
//        }
        oks.setImageUrl(imgUrl);
//        oks.setUrl(TextUtils.isEmpty(qrCodeBean.getShare())?"":qrCodeBean.getShare());
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网使用
//        oks.setComment(qrCodeBean.getContent());
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                ToastUtils.showMessage("分享成功！");
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                ToastUtils.showMessage("分享失败！");
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Log.d("params","取消分享");
            }
        });
        // 启动分享GUI
        oks.show(context);
    }

    /**
     * 微信支付数据 content从后台获取
     */
    public void ActiveWXPay(OrderDataEntity.Order content) {
        Logger.i("==========content微信支付返回的数据:" + new Gson().toJson(content));
        PayReq req = new PayReq();
        req.appId = content.getAppid();
        req.partnerId = content.getMch_id();//少返回这个
        req.prepayId = content.getPrepay_id();
        req.nonceStr = content.getNonce_str();
        req.timeStamp = String.valueOf(Calendar.getInstance().getTimeInMillis()/1000L);
        req.packageValue = "Sign=WXPay";
        req.sign = signNum(req);
        req.extData = "app data";

        ToastUtils.showMessage("正在调起支付");
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        boolean b = api.sendReq(req);
        if (!b){
            ToastUtils.showMessage("调起支付失败");
        }

    }

    /**
     * 微信登录
     */
    public void WXLogin() {
        if (isWXAppInstalledAndSupported()) {
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";//固定填写这个
            req.state = "getuserinfo";//自己生成的校验标记
            api.sendReq(req);
        } else {
            ToastUtils.showMessage("您还没有安装微信");
        }
    }

    private String signNum(PayReq req) {
        String stringA =
                "appid=" + req.appId
                        + "&noncestr=" + req.nonceStr
                        + "&package=" + req.packageValue
                        + "&partnerid=" + req.partnerId
                        + "&prepayid=" + req.prepayId
                        + "&timestamp=" + req.timeStamp;


        String stringSignTemp = stringA + "&key=WDzxh668899CN55Chinatea1988666aA";
        String sign = MD5.getMessageDigest(stringSignTemp.getBytes()).toUpperCase();
        return sign;
    }

//    /**
//     * 生成签名
//     */
//    private String genAppSign(List<WXModel> list) {
//
//        List<WXModel> list = new LinkedList<>();
//        list.add(new WXModel("appid", payReq.appId));
//        list.add(new WXModel("noncestr", payReq.nonceStr));
//        list.add(new WXModel("package", payReq.packageValue));
//        list.add(new WXModel("partnerid", payReq.partnerId));
//        list.add(new WXModel("prepayid", payReq.prepayId));
//        list.add(new WXModel("timestamp", payReq.timeStamp));
//        payReq.sign = genAppSign(list);
//
//
//
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < list.size(); i++) {
//            sb.append(list.get(i).key);
//            sb.append('=');
//            sb.append(list.get(i).value);
//            sb.append('&');
//        }
//        sb.append("key=");
//        sb.append(Constant.WX_APP_KEY);
//        String appSign = MD5Utils.getMessageDigest(sb.toString().getBytes()).toUpperCase();
//        return appSign;
//    }

}
