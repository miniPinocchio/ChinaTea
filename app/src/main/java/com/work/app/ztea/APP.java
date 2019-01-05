package com.work.app.ztea;

import android.app.Activity;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.dream.library.base.BaseLibApplication;
import com.dream.library.utils.AbAppUtils;
import com.dream.library.utils.AbGsonUtil;
import com.dream.library.utils.AbPreferencesUtils;
import com.loopj.android.http.TextHttpResponseHandler;
import com.mob.MobSDK;
import com.vondear.rxtool.RxTool;
import com.work.app.ztea.entity.VersionEntity;
import com.work.app.ztea.http.Api;

import java.util.HashSet;
import java.util.Set;

import cz.msebera.android.httpclient.Header;

/**
 * Created by qiyunfeng on 2017/11/27.
 */

public class APP extends BaseLibApplication {

    public static VersionEntity.Version mVersion;

    public static final String TOKEN_KEY = "user_token";
    public static final String USER_NAME = "USER_NAME";
    public static final String PASS_WORD = "PASS_WORD";

    private static APP INSTANCE;


    public static synchronized APP getContext() {
        return INSTANCE;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        MobSDK.init(this);
        RxTool.init(this);
        isUpdata();
    }


    public static void setToken(String token) {
        AbPreferencesUtils.putString(getInstance(), TOKEN_KEY, token);
    }

    public static String getToken() {
        return AbPreferencesUtils.getString(getInstance(), TOKEN_KEY);
    }

    //存neme
    public static void setUserName(String Name) {
        AbPreferencesUtils.putString(getInstance(), USER_NAME, Name);
    }

    //取neme
    public static String getUserName() {
        return AbPreferencesUtils.getString(getInstance(), USER_NAME);
    }

    //存密码
    public static void setPassWord(String Password) {
        AbPreferencesUtils.putString(getInstance(), PASS_WORD, Password);
    }

    //取密码
    public static String getPassWord() {
        return AbPreferencesUtils.getString(getInstance(), PASS_WORD);
    }


    /**
     * 退出登录
     */
    public static void logout() {
        AbPreferencesUtils.remove(APP.getInstance(), TOKEN_KEY);
    }

    /**
     * 修改密码退出
     */
    public static void editpwd() {
        AbPreferencesUtils.remove(APP.getInstance(), USER_NAME, PASS_WORD, TOKEN_KEY);
    }


    public void isUpdata() {
        String versionName = AbAppUtils.getVersionName(this);
        Api.register(versionName, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                VersionEntity version = AbGsonUtil.json2Bean(responseString, VersionEntity.class);
                if (null != version && version.isOk() && version.data != null) {
                    APP.mVersion = version.data;
                    //EventBus.getDefault().post(new EventCenter(Api.APP_UPDATA));
                }
            }
        });

    }

    private Set<Activity> mActivities;

    public void addActivity(Activity activity) {
        if (mActivities == null) {
            mActivities = new HashSet<>();
        }
        mActivities.add(activity);
    }

    public void removeActivity(Activity activity) {
        if (mActivities != null) {
            mActivities.remove(activity);
        }
    }

    /**
     * 清空所有页面
     */
    public void clearActivity() {
        if (mActivities != null) {
            for (Activity activity : mActivities) {
                activity.finish();
            }
            mActivities.clear();
        }
    }
}
