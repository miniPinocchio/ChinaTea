package com.work.app.ztea.base;

import com.dream.library.base.BaseLibApplication;

/**
 * Author:      SuSong
 * Email:       751971697@qq.com | susong0618@163.com
 * GitHub:      https://github.com/susong0618
 * Date:        16/1/5 上午11:02
 * Description: BaoTan
 */
public class BaseApplication extends BaseLibApplication {

    private static BaseApplication mBaseApplication;

    public static BaseApplication getInstance() {
        return mBaseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBaseApplication = this;
    }


}
