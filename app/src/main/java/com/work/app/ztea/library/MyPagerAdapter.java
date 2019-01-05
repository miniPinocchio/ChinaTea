package com.work.app.ztea.library;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.work.app.ztea.R;

import java.util.List;

public class MyPagerAdapter extends PagerAdapter {

    private List<String> imageList;

    public MyPagerAdapter(List<String> imageList) {
        this.imageList = imageList;
    }

    /**
     * 返回的int的值, 会作为ViewPager的总长度来使用.
     */
    @Override
    public int getCount() {
        int ret=0;
        if (imageList != null) {
//            原始的真实个数
            ret=imageList.size();
//            如果需要循环，设置 Integer.MAX_VALUE
//            ret=Integer.MAX_VALUE;
        }
        return ret;
    }

    /**
     * 判断是否使用缓存, 如果返回的是true, 使用缓存. 不去调用instantiateItem方法创建一个新的对象
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 初始化一个条目
     * position 就是当前需要加载条目的索引
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //实现步骤：
        //1、创建或者加载布局；
        //2、设置布局的内容
        //3、手动将View添加到container中
        //4、返回创建的布局
        if (imageList.size() == 0){
            return null;
        }
        View ret=null;
        //凡是在代码中需要手动创建UI控件的，都可以调用一个参数的构造方法；例如ImageView(context)
        ImageView imageView =
                new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(container.getContext())
                .load(imageList.get(position%imageList.size()))
//                            .error(R.drawable.banner)
                .into(imageView);
//        ImageLoaderUtil.displayImage(container.getContext(),imageView,imageList.get(position%imageList.size()));
        //将View添加到ViewGroup
        //每一个ViewGroup类型都可以调用addView()方法来添加此控件
        container.addView(imageView);

        ret=imageView;//需要返回
        // 把当前添加ImageView返回回去.
        return ret;
    }

    /**
     * 销毁一个条目
     * position 就是当前需要被销毁的条目的索引
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 把ImageView从ViewPager中移除掉
        container.removeView((View) object);
    }
}
