package com.work.app.ztea.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by 天上白玉京 on 2018/1/10.
 */

public class SelfAdaptionRecycler extends RecyclerView{

    public SelfAdaptionRecycler(Context context) {
        super(context);
    }

    public SelfAdaptionRecycler(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SelfAdaptionRecycler(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
