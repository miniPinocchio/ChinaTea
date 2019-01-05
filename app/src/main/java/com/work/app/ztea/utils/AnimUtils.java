package com.work.app.ztea.utils;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.work.app.ztea.R;


/**
 * Created by qiyunfeng on 2017/2/22.
 */

public class AnimUtils {

    public static void startShake(Context context, View v) {
        Animation shake = AnimationUtils.loadAnimation(context, R.anim.anim_shake);
        v.startAnimation(shake);
    }

}
