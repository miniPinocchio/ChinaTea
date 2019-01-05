package com.work.app.ztea.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;

import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseActivity;
import com.work.app.ztea.ui.MainActivity;

public class SplashActivity extends BaseActivity {


    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        if (!this.isTaskRoot()) {
            Intent mainIntent = getIntent();
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER)) {
                finish();
                return;
            }
        } else {
            mHandler.postDelayed(removeCallbacks, 2000);
        }
    }

    Handler mHandler = new Handler();

    Runnable removeCallbacks = new Runnable() {
        @Override
        public void run() {
            readyGo(MainActivity.class);
            finish();
        }
    };
}
