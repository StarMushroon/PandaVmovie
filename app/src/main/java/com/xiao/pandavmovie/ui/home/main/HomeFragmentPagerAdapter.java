package com.xiao.pandavmovie.ui.home.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by StarMushroom on 2017/4/15.
 */

public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mList;

    public HomeFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList!=null?mList.size():0;
    }


}
