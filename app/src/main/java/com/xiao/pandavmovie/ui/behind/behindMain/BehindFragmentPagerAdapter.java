package com.xiao.pandavmovie.ui.behind.behindMain;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xiao.pandavmovie.bean.BehindTabBean;

import java.util.ArrayList;

/**
 * Created by StarMushroom on 2017/4/17.
 */
public class BehindFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragments;
    private ArrayList<BehindTabBean> mTitles;

    public BehindFragmentPagerAdapter(FragmentManager childFragmentManager, ArrayList<Fragment> fragments, ArrayList<BehindTabBean> titles) {
        super(childFragmentManager);
        mFragments = fragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments!=null?mFragments.size():0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
       return mTitles.get(position).catename;
    }
}
