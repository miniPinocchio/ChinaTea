package com.work.app.ztea.http;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by qiyunfeng on 2017/12/4.
 */

public class Api {
    public static final String PRIVATE_KEY = "ACZXH*&fTA8!fKKsDS#@!$G@#$!#SC!";//私钥

    public static final int UA = 1;  //1：安卓，2：ios【客户端登录类型标志，所有接口统一需要传此字段】

    //    public static final String BASE_URL = "http://bbjh.hqdemo.cn/"; //测试
    //    public static final String BASE_URL = "http://www.baobaojh8.com/"; //正式
//    public static final String BASE_URL = "https://www.baobaojh8.com/"; //正式
    //    public static final String BASE_URL2 = "https://data.baobaojh8.com/"; //正式获取计划地址
    public static final String BASE_URL = "http://zhongcha.hqdemo.cn/"; //正式
    public static final String BASE_URL2 = "https://data.mofashijihua.com/"; //正式获取计划地址

    public static final int REFERSH_DATA = 1; //刷新
    public static final int REFERSH_RANKINGS = 21; //刷新
    public static final int TOKEN_TIMEOUT = 2;
    public static final int REFERSH_FRAGMENT = 3;
    public static final int SKU_FRAGMENT = 4;

    public static final int NUMBER = 5;//传递人数到首页
    public static final int VALUESBEAN = 6;//刷新计划
    public static final int BANNER = 7;//刷新BANNER
    public static final int MESSAGE = 8;//MESSAGE
    public static final int USER = 9;//刷新user
    public static final int UNDERLINE = 10;//UNDERLINE
    public static final int DEL_USER = 11;//删除用户
    public static final int SITE_CONFIG = 12;//消息滚动
    public static final int TOKEN_OUT = 13;//退出
    public static final int VALUESBEAN_POSITION = 14;//刷新计划-传递参数位置
    public static final int VALUESBEAN_REFERSH = 15;//刷新计划-传递参数位置
    public static final int ORDER_PAY_OK = 16;//支付成功
    public static final int TIMING_TRANSMIS = 17;//定时请求
    public static final int INVOICE_REFRESH = 18;//发票id已删除

    public static final int OPEN_TRANSMIS = 18;//开启定时请求
    public static final int CLOSE_TRANSMIS = 19;//关闭定时请求
    public static final int PAY_SUCCESS = 20;//微信支付成功
    public static final int PAY_FAIL = 22;//微信支付失败
    public static final int OPEN_SHOP = 23;//打开商城
    public static final int RECHARGE_OK = 24;//刷新个人中心

    /**
     * 登录
     */
    public static void loginForPwd(String username, String password, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("username", username);
        params.put("password", password);
//        params.put("UA", UA);
        ApiHttpClient.post("index.php?m=api&c=user&a=login", params, handler);
    }

    /**
     * 手机验证码登录
     */
    public static void codeLogin(String mobile, String code, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("mobile", mobile);
        params.put("code", code);
        ApiHttpClient.post("index.php?m=api&c=user&a=smslogin", params, handler);
    }

    /**
     * 找回密码
     */
    public static void forgotPwd(String mobile, String code, String password,String repassword, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("mobile", mobile);
        params.put("code", code);
        params.put("password", password);
        params.put("repassword", repassword);
        ApiHttpClient.post("index.php?m=api&c=user&a=forgetpassword", params, handler);
    }

    /**
     * 获取登录界面是否开启隐藏 注册入口的接口
     */
    public static void getAppStatus(AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        ApiHttpClient.post("index.php?m=Api&c=Public&a=getAppStatus", params, handler);
    }

    /**
     * 注册协议
     */
    public static void getRegisterSite(AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        ApiHttpClient.post("index.php?m=api&c=user&a=registerdeal", params, handler);
    }

    /**
     * 用户协议
     */
    public static void getUserSite(AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        ApiHttpClient.post("index.php?m=api&c=member&a=userrule", params, handler);
    }


    /**
     * 发送手机验证码
     */
    public static void sendCode(String type, String mobile, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("type", type);//操作类型1注册 2忘记密码 3手机号验证码登录 4修改手机号
        params.put("mobile", mobile);
        ApiHttpClient.post("index.php?m=api&c=user&a=sendsmscode", params, handler);
    }

    /**
     * 注册
     */
    public static void register(String invite_code, String password, String repassword, String mobile, String name, String code, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("invite_code", invite_code);
        params.put("password", password);
        params.put("repassword", repassword);
        params.put("mobile", mobile);
        params.put("name", name);
        params.put("code", code);
        ApiHttpClient.post("index.php?m=api&c=user&a=register", params, handler);
    }

    /**
     * 个人中心首页
     */
    public static void getMineInfo(String token, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        ApiHttpClient.post("index.php?m=api&c=member&a=getinfo", params, handler);
    }

    /**
     * 基础资料
     */
    public static void getBaseInfo(String token, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        ApiHttpClient.post("index.php?m=api&c=member&a=baseInfo", params, handler);
    }

    /**
     * 编辑基础资料
     */
    public static void editBaseInfo(String token, String name, String age, String gender,
                                    String birthday, String province, String city, String area,
                                    String store_address, File image, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        params.put("name", name);
        params.put("age", age);
        params.put("gender", gender);
        params.put("birthday", birthday);
        params.put("province", province);
        params.put("city", city);
        params.put("area", area);
        params.put("store_address", store_address);
        try {
            params.put("image", image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ApiHttpClient.post("index.php?m=api&c=member&a=editinfo", params, handler);
    }

    /**
     * 商城首页
     */
    public static void getHomeGoods(AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        ApiHttpClient.post("index.php?m=api&c=goods&a=index", params, handler);
    }

    /**
     * 积分商城列表
     */
    public static void getIntegralList(int page, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("page", page);
        ApiHttpClient.post("index.php?m=api&c=goods&a=integralgoods", params, handler);
    }

    /**
     * 认购专区列表
     */
    public static void getSubscribeList(int page, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("page", page);
        ApiHttpClient.post("index.php?m=api&c=goods&a=subscribe", params, handler);
    }

    /**
     * 商城列表
     */
    public static void getGoodsList(String id,int page,int production_date, int price, int crdate, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("id", id);
        params.put("page", page);
        params.put("production_date", production_date);
        params.put("price", price);
        params.put("crdate", crdate);
        ApiHttpClient.post("index.php?m=api&c=goods&a=goodslist", params, handler);
    }

    /**
     * 商品信息
     */
    public static void getGoodsInfo(String id,String token, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("id", id);
        params.put("token", token);
        ApiHttpClient.post("index.php?m=api&c=goods&a=info", params, handler);
    }

    /**
     * 商品收藏
     */
    public static void goodsFav(String id,String token,String type, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("id", id);
        params.put("token", token);
        params.put("type", type);
        ApiHttpClient.post("index.php?m=api&c=goods&a=goodsfav", params, handler);
    }

    /**
     * 商品评论
     */
    public static void getCommentList(String goods_id,int page, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("goods_id", goods_id);
        params.put("page", page);
        ApiHttpClient.post("index.php?m=api&c=goods&a=goodscmt", params, handler);
    }

    /**
     * 加入购物车
     */
    public static void addShopCar(String token, String id,String num, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        params.put("id", id);
        params.put("num", num);
        ApiHttpClient.post("index.php?m=api&c=cart&a=cartadd", params, handler);
    }

    /**
     * 购物车列表
     */
    public static void shopCarList(String token, int page,AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        params.put("page", page);
        ApiHttpClient.post("index.php?m=api&c=cart&a=cartlist", params, handler);
    }

    /**
     * 购物车改变数量
     */
    public static void shopCarNum(String token, String cart_id, int num,AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        params.put("cart_id", cart_id);
        params.put("num", num);
        ApiHttpClient.post("index.php?m=api&c=cart&a=cartnum", params, handler);
    }

    /**
     * 购物车-删除商品
     */
    public static void delShopCarGoods(String token, String cart_id, String option,AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        params.put("cart_id", cart_id);
        params.put("option", option);
        ApiHttpClient.post("index.php?m=api&c=cart&a=cartedit", params, handler);
    }

    /**
     * 商品详情=》确定订单
     */
    public static void orderSureOne(String token, String goods_id, String num, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        params.put("goods_id", goods_id);
        params.put("num", num);
        ApiHttpClient.post("index.php?m=api&c=cart&a=ordernow", params, handler);
    }

    /**
     * 购物车=》确定订单
     */
    public static void orderSureTwo(String token, String cart_id, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        params.put("cart_id", cart_id);
        ApiHttpClient.post("index.php?m=api&c=cart&a=order", params, handler);
    }

    /**
     * 在线支付
     */
    public static void onlinePay(String token, String orders_id,  String vouchers_id, String tdvouchers,String money,String type, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        params.put("orders_id", orders_id);
        params.put("is_pay", "1");
        params.put("vouchers_id", vouchers_id);
        params.put("tdvouchers", tdvouchers);
        params.put("money", money);
        params.put("type", type);
        ApiHttpClient.post("index.php?m=api&c=cart&a=paying", params, handler);
    }

    /**
     * 提交订单
     */
    public static void commintOrder(String token, String order_type,float total_money,
                                    String total_integral,String money,String vouchers_id,
                                    String tdvouchers,String address_id,String is_receipt,
                                    String remark,String is_store, String type,String offlinepays,String form,
                                    String cart_id,String goods_id,String goods_num, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        params.put("order_type", order_type);
        params.put("total_money", total_money);
        params.put("total_integral", total_integral);
        params.put("money", money);
        params.put("vouchers_id", vouchers_id);
        params.put("tdvouchers", tdvouchers);
        params.put("address_id", address_id);
        params.put("is_receipt", is_receipt);
        params.put("remark", remark);
        params.put("is_store", is_store);
        params.put("type", type);
        params.put("offlinepays", offlinepays);
        params.put("form", form);
        params.put("cart_id", cart_id);
        params.put("goods_id", goods_id);
        params.put("goods_num", goods_num);
        ApiHttpClient.post("index.php?m=api&c=cart&a=ordersubmit", params, handler);
    }

    /**
     * 订单详情
     */
    public static void orderDetail(String token, String id, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        params.put("id", id);
        ApiHttpClient.post("index.php?m=api&c=member&a=myordersinfo", params, handler);
    }

    /**
     * 默认地址
     */
    public static void getDefaultAddress(String token, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        ApiHttpClient.post("index.php?m=api&c=cart&a=address", params, handler);
    }

    /**
     * 我的收货地址
     */
    public static void myAddress(String token,AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        ApiHttpClient.post("index.php?m=api&c=member&a=myaddress", params, handler);
    }

    /**
     * 管理收货地址
     */
    public static void editAddr(String token, String type, String a_id,  String name,
                                String mobile,String is_default,
                                String province, String city,  String area, String address, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        params.put("type", type);
        params.put("a_id", a_id);
        params.put("name", name);
        params.put("mobile", mobile);
        params.put("is_default", is_default);
        params.put("province", province);
        params.put("city", city);
        params.put("area", area);
        params.put("address", address);
        ApiHttpClient.post("index.php?m=api&c=member&a=editaddr", params, handler);
    }

    /**
     * 设置默认收货地址
     */
    public static void defaultAddr(String token,String a_id, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        params.put("a_id", a_id);
        ApiHttpClient.post("index.php?m=api&c=member&a=default_addr", params, handler);
    }

    /**
     * 我的发票
     */
    public static void myInvoice(String token, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        ApiHttpClient.post("index.php?m=api&c=member&a=myinvoice", params, handler);
    }

    /**
     * 管理我的发票信息
     */
    public static void editInvoice(String token, String type,String a_id, String types, String title,
                                      String tax_number,String address, String bank_name, String bank_card,
                                      String tel, String mobile,String email, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        params.put("type", type);
        params.put("a_id", a_id);
        params.put("types", types);
        params.put("title", title);
        params.put("tax_number", tax_number);
        params.put("address", address);
        params.put("bank_name", bank_name);
        params.put("bank_card", bank_card);
        params.put("tel", tel);
        params.put("mobile", mobile);
        params.put("email", email);
        ApiHttpClient.post("index.php?m=api&c=member&a=editinvoice", params, handler);
    }


    /**
     * 设置默认发票
     */
    public static void defaultInvoice(String token, String a_id, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        params.put("a_id", a_id);
        ApiHttpClient.post("index.php?m=api&c=member&a=default_invoice", params, handler);
    }

    /**
     * 意见反馈
     */
    public static void feedBack(String token,String title, String content, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        params.put("title", title);
        params.put("content", content);
        ApiHttpClient.post("index.php?m=api&c=member&a=suggest", params, handler);
    }

    /**
     * 修改密码
     */
    public static void editpwd(String token, String oldpassword, String password, String repassword, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        params.put("oldpassword", oldpassword);
        params.put("password", password);
        params.put("repassword", repassword);
        ApiHttpClient.post("index.php?m=api&c=member&a=editPassword", params, handler);
    }

    /**
     * 修改手机号
     */
    public static void editMobile(String token, String mobile,String code, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        params.put("mobile", mobile);
        params.put("code", code);
        ApiHttpClient.post("index.php?m=api&c=member&a=bind", params, handler);
    }

    /**
     * 实名认证
     */
    public static void shiming(String token, String idcard,String name, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        params.put("idcard", idcard);
        params.put("name", name);
        ApiHttpClient.post("index.php?m=api&c=member&a=identify", params, handler);
    }

    /**
     * 银行卡
     */
    public static void bankList( AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        ApiHttpClient.post("index.php?m=api&c=member&a=banks", params, handler);
    }

    /**
     * 管理我的银行卡
     */
    public static void editCards(String token, String type,String cardholder,String cardno,String holdermobile,String bank, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        params.put("type", type);
        params.put("cardholder", cardholder);
        params.put("cardno", cardno);
        params.put("holdermobile", holdermobile);
        params.put("bank", bank);
        ApiHttpClient.post("index.php?m=api&c=member&a=editcards", params, handler);
    }

    /**
     * 我的订单
     */
    public static void myOrder(String token, String type, String page,String num, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        params.put("type", type);
        params.put("page", page);
        params.put("num", num);
        ApiHttpClient.post("index.php?m=api&c=member&a=myorders", params, handler);
    }

    /**
     * 订单状态变更
     */
    public static void orderStatus(String token, String id, String state, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        params.put("id", id);
        params.put("state", state);
        ApiHttpClient.post("index.php?m=api&c=member&a=orderstatus", params, handler);
    }

    /**
     * 提醒发货
     */
    public static void remindOrder(String token, String id, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        params.put("id", id);
        ApiHttpClient.post("index.php?m=api&c=member&a=remind", params, handler);
    }

    /**
     * 退出登录
     */
    public static void logout(String token, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        params.put("UA", UA);
        ApiHttpClient.post("index.php?m=Api&c=User&a=logout", params, handler);
    }

    /**
     * 我的二维码
     */
    public static void myQrCode(String token, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        ApiHttpClient.post("index.php?m=api&c=member&a=qrcode", params, handler);
    }

    /**
     * 会员充值
     */
    public static void rechargeMember(String token, String type, String gold, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        params.put("type", type);
        params.put("gold", gold);
        ApiHttpClient.post("index.php?m=api&c=member&a=recharge", params, handler);
    }

    /**
     * 升级尊金会员
     */
    public static void updateLevel(String token, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        ApiHttpClient.post("index.php?m=api&c=member&a=updateLevel", params, handler);
    }

    /**
     * 群组员批量删除
     */
    public static void groupdel(String token, String ids, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        params.put("UA", UA);
        params.put("ids", ids);
        ApiHttpClient.post("index.php?m=Api&c=User&a=groupdel", params, handler);
    }

    /**
     * 设置QQ
     */
    public static void setqq(String token, String qq, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        params.put("UA", UA);
        params.put("qq", qq);
        ApiHttpClient.post("index.php?m=Api&c=User&a=setqq", params, handler);
    }

    /**
     * 获取客户端信息页面
     */
    public static void system_info(String token, AsyncHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        params.put("UA", UA);
        ApiHttpClient2.post("systemInfo.php", params, handler);
    }


    /**
     * 版本检测
     */
    public static void register(String version, TextHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("version", version);
        params.put("platform", "1");
        ApiHttpClient.post("index.php?m=Api&c=Public&a=version", params, handler);
    }


    /**
     * 客户端后台切回前端调用此接口判断当前用户账号是否可用
     */
    public static void checkuseronline(String token, TextHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        params.put("UA", UA);
        ApiHttpClient.post("index.php?m=Api&c=System&a=checkUserOnline", params, handler);
    }

    /**
     * 重庆时时彩开奖号
     */
    public static void cqkaijiang(TextHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        ApiHttpClient.get("index.php?m=Api&c=Public&a=cQKaiJiang", params, handler);
    }


    //    public static final String BASE_URL3 = "http://47.93.175.68:4346/"; //获取定时的计划内容
    public static final String BASE_URL3 = "http://"; //获取定时的计划内容

    /**
     * 获取定时的计划内容
     */

    public static void timing(int type, String lottery, String template, TextHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("action", "getRGTemplet");
        params.put("Lottery", lottery);
        params.put("Template", template);
        params.put("Mkey", "rusoUwz3OM5NaI5rMsgJlQ==");
//        getRandomNumInTwoIntNum(0, 3, params, handler);
        if (type == 1) {
//            Log.e("sdsds1", type + "");
            ApiHttpClient3.get("59.110.161.224:4346/HZJH.aspx", params, handler);
        } else if (type == 2) {
//            Log.e("sdsds2", type + "");
            ApiHttpClient3.get("39.105.150.81:4346/HZJH.aspx", params, handler);
        }
    }

//    //生成随机调取接口
//    private static void getRandomNumInTwoIntNum(int x, int y, MyRequestParams params, TextHttpResponseHandler handler) {
//        Random random = new Random();
//        int cha = Math.abs(x - y);
//        int randomCha = random.nextInt(cha) + 1;
//        if (randomCha >= cha) {
//            randomCha = cha - 1;
//        }
//        if (randomCha == 1) {
//            Log.e("sdsds1", randomCha + "");
//            ApiHttpClient3.get("47.93.177.253:4346/HZJH.aspx", params, handler);
//        } else if (randomCha == 2) {
//            Log.e("sdsds2", randomCha + "");
//            ApiHttpClient3.get("47.93.83.107:4346/HZJH.aspx", params, handler);
//        }
//    }

    /**
     * 判断登录状态
     * https://data.baobaojh8.com/checkUserStatus.php?UA=1&token=OTM4MDAtNzdkYjIyMzA1ZmE1Zjk4M2UzYWEzOGFmY2ExNzMyYTYtMTUzMzYwODA5MQ==
     */
    public static void loginState(String token, TextHttpResponseHandler handler) {
        MyRequestParams params = new MyRequestParams();
        params.put("token", token);
        params.put("UA", "1");
        ApiHttpClient2.post("checkUserStatus.php", params, handler);

    }

}
