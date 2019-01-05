package com.work.app.ztea.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.work.app.ztea.APP;
import com.work.app.ztea.entity.LoginEntity;


/**
 * 用户信息
 *
 * @author Sun.bl
 * @version [1.0, 2016/6/24]
 */
public class UserService {


    private static final String USER_INFO = "userInfo";

    private static final String INIT_INFO = "initConfig";

    private static final String AUTO_LOGIN = "autoLogin";

    private static final String LOGIN_TYPE = "loginType";

    private static final String AUTO_PUSH = "autoPush";

    private static final String TASK_CURRENT = "taskCurrent";

    private static final String OEPN_LAUNCH = "openLaunch";

    private static final String ORDER_NO = "autoLogin";

    private static final String OLD_CAR = "oldCarInfo";

    private static final String APPOINTMENT_DATA = "appointmentData";

    public static final String sp_name = "pipe";

    /**
     * 存储初始化设置
     *
     * @param initBean
     */
//    public static void saveInitConfig(InitBean initBean) {
//
//        if (initBean == null) {
//            return;
//        }
//
//        Gson gson = new Gson();
//        String initJson = gson.toJson(initBean);
//        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(BaseApplication.APP_CONTEXT, CustomConstant.sp_name);
//        sharedPreferencesUtil.setData(INIT_INFO, initJson);
//    }

    /***
     * 获取初始化信息
     *
     * @return
     */
//    public static InitBean getInitConfig() {
//
//        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(BaseApplication.APP_CONTEXT, CustomConstant.sp_name);
//        String initJson = (String) sharedPreferencesUtil.getData(INIT_INFO);
//
//        if (TextUtils.isEmpty(initJson)) {
//            return null;
//        }
//
//        Gson gson = new Gson();
//        InitBean initBean = gson.fromJson(initJson, InitBean.class);
//
//        return initBean;
//    }

    /**
     * 存储用户信息
     *
     * @param user
     */
    public static void saveUserInfo(LoginEntity.Login user) {
        if (user == null) {
            return;
        }

        //CustomConstant.TOKEN = user.token;

        Gson gson = new Gson();
        String userJson = gson.toJson(user);

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(APP.getInstance(), sp_name);
        sharedPreferencesUtil.setData(USER_INFO, userJson);

    }


    /**
     * 获取用户信息
     *
     * @return
     */
    public static LoginEntity.Login getUserInfo() {

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(APP.getInstance(), sp_name);
        String userJson = (String) sharedPreferencesUtil.getData(USER_INFO);

        if (TextUtils.isEmpty(userJson)) {
            return null;
        }

        Gson gson = new Gson();
        LoginEntity.Login user = gson.fromJson(userJson, LoginEntity.Login.class);

        return user;
    }

    /***
     * 设置是否推送消息
     */
    public static void setAutoPush(String push) {

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(APP.getInstance(), sp_name);
        sharedPreferencesUtil.setData(AUTO_PUSH, push);

    }

    /**
     * 判断是否要自动登录
     *
     * @return
     */
    public static boolean isAutoPush() {

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(APP.getInstance(), sp_name);
        String autoLoginJson = (String) sharedPreferencesUtil.getData(AUTO_PUSH);

        return TextUtils.equals("1", autoLoginJson);
    }

    /***
     * 设置自动登录
     */
    public static void setAutoLogin(String autoLogin) {

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(APP.getInstance(), sp_name);
        sharedPreferencesUtil.setData(AUTO_LOGIN, autoLogin);

    }

    /***
     * 设置是微信登录
     */
    public static void setWxLogin(String loginType) {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(APP.getInstance(), sp_name);
        sharedPreferencesUtil.setData(LOGIN_TYPE, loginType);
    }

    /**
     * 判断是否是微信登录
     *
     * @return
     */
    public static boolean isWxLogin() {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(APP.getInstance(), sp_name);
        String autoLoginJson = (String) sharedPreferencesUtil.getData(LOGIN_TYPE);

        return TextUtils.equals("1", autoLoginJson);
    }

    /**
     * 判断是否打开欢迎导航页
     *
     * @return
     */
    public static boolean isOpenLaunch() {

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(APP.getInstance(), sp_name);
        String autoLoginJson = (String) sharedPreferencesUtil.getData(OEPN_LAUNCH);

        return TextUtils.equals("1", autoLoginJson);
    }

    /***
     * 设置是否打开欢迎导航页
     */
    public static void setOpenLaunch(String open) {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(APP.getInstance(), sp_name);
        sharedPreferencesUtil.setData(OEPN_LAUNCH, open);

    }

    /**
     * 判断是否要自动登录
     *
     * @return
     */
    public static boolean isAutoLogin() {

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(APP.getInstance(), sp_name);
        String autoLoginJson = (String) sharedPreferencesUtil.getData(AUTO_LOGIN);

        return TextUtils.equals("1", autoLoginJson);

    }


    /**
     * 可选更新登录一次提醒一次，在使用过程中只要不退出就不提醒。如果强制更新则在使用过程中是提示出来的 设置更新提醒
     */
    public static void setCheckAppUpdate() {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(APP.getInstance(), sp_name);
        sharedPreferencesUtil.setData("checkAppUpdate", "1");
    }

    /**
     * 得到更新提醒标识
     */
    public static String getCheckAppUpdate() {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(APP.getInstance(), sp_name);
        return (String) sharedPreferencesUtil.getData("checkAppUpdate");
    }

    public static String getSkipUpdateTime() {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(APP.getInstance(), sp_name);

        return (String) sharedPreferencesUtil.getData("skipUpdateTime");
    }

    public static void setSkipUpdateTime(long skipTime) {

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(APP.getInstance(), sp_name);

        sharedPreferencesUtil.setData("skipUpdateTime", String.valueOf(skipTime));

    }


}
