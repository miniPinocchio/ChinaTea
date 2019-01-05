package com.work.app.ztea.entity;

import com.dream.library.eventbus.EventCenter;
import com.work.app.ztea.http.Api;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by qiyunfeng on 2017/4/24.
 */

public class BaseEntity<T> {

    public String code_key;
    public String msg;
    public int code;
    public T data;

    public boolean isOk() {
        if (code == 1) {
            return true;
        } else if (code == 12) {
            EventBus.getDefault().post(new EventCenter(Api.TOKEN_TIMEOUT, msg));
            return false;
        } else if (code == 99999) {
            EventBus.getDefault().post(new EventCenter(Api.TOKEN_TIMEOUT, msg));
            return false;
        }
        return false;
    }

}
