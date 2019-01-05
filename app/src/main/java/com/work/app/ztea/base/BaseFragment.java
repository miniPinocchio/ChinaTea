package com.work.app.ztea.base;

import android.view.View;
import android.widget.ImageView;

import com.dream.library.base.BaseLibFragment;
import com.dream.library.eventbus.EventCenter;

/**
 * Author:      qiyunfeng
 * GitHub:      https://github.com/susong0618
 * Date:        16/7/3 上午1:28
 * Description: TimelyRain
 */
public abstract class BaseFragment extends BaseLibFragment {

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    public void showImageByGlideVertical(String url, ImageView im) {
        if (mContext instanceof BaseActivity) {
            ((BaseActivity) mContext).showImageByGlideVertical(url, im);
        }
    }

    public void showImageByGlideHorizontal(String url, ImageView im) {
        if (mContext instanceof BaseActivity) {
            ((BaseActivity) mContext).showImageByGlideHorizontal(url, im);
        }
    }


//    @Override
//    public void onResume() {
//        super.onResume();
//        MobclickAgent.onPageStart(getClass().getName());
//        MobclickAgent.onResume(mContext);
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        MobclickAgent.onPageEnd(getClass().getName());
//        MobclickAgent.onPause(mContext);
//    }

    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 可见
     */
    protected void onVisible() {

    }


    /**
     * 不可见
     */
    protected void onInvisible() {

    }

}
