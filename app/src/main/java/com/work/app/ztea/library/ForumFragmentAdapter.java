package com.work.app.ztea.library;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */

public class ForumFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private List<String> mStrings;

    public ForumFragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> stringList) {
        super(fm);
        this.mFragments = fragments;
        this.mStrings = stringList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        int ret = 0;
        if(mFragments!=null){
            ret = mFragments.size();
        }
        return ret;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mStrings.get(position);
    }

}
