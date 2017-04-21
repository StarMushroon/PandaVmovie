package com.xiao.pandavmovie.ui.series.detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by StarMushroom on 2017/4/18.
 */
public class SeriesDetailTablayoutFragmentPagerAdapter extends FragmentPagerAdapter{

    private ArrayList<String> mTitles;
    private ArrayList<Fragment> mFragments;

    public SeriesDetailTablayoutFragmentPagerAdapter(FragmentManager fm, ArrayList<String> titles, ArrayList<Fragment> fragments) {
        super(fm);
        mTitles = titles;
        mFragments = fragments;
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
        return mTitles.get(position);
    }
}
