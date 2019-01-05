package com.work.app.ztea.ui.activity.mine;

import android.os.Bundle;

import com.dream.library.utils.annotation.annotation.ViewInject;
import com.work.app.ztea.R;
import com.work.app.ztea.base.BaseActivity;
import com.work.app.ztea.widget.ZoomImageView;

/**
 * @author huiliu
 */
public class StatisticsActivity extends BaseActivity {

    @ViewInject(R.id.ziv_load)
    ZoomImageView ziv_load;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_statistics;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setVisibleLeft(true);
        setTopTitle("汇总表单");
        ziv_load.setImageResource(R.drawable.img_statistics);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
