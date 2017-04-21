package com.xiao.pandavmovie.ui.home.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiao.pandavmovie.MainActivity;
import com.xiao.pandavmovie.R;
import com.xiao.pandavmovie.ui.home.home_Channel.main.ChannelFragment;
import com.xiao.pandavmovie.ui.home.home_Newest.main.NewestFragment;

import java.util.ArrayList;


/**
 * 主页
 * Created by StarMushroom on 2017/4/15.
 */

public class HomeFragment extends Fragment implements ViewPager.OnPageChangeListener, MainActivity.onTitleClickListener {

    public static final String TAG = HomeFragment.class.getName();
    private ViewPager mViewPager;
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, null);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager_fragment_home);

        mFragments.add(new NewestFragment());
        mFragments.add(new ChannelFragment());
        HomeFragmentPagerAdapter mAdapter = new HomeFragmentPagerAdapter(getChildFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);
        return view;
    }


    private MainActivity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mActivity = (MainActivity) context;
            mActivity.setListener(this);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mActivity != null) {
            mActivity.changeTitle(position + positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

   /* //接口回调，点击title切换fragment
    private onTitleClickListener mListener;

    public void setListener(onTitleClickListener listener) {
        mListener = listener;
    }*/

    @Override
    public void onNewestClickListener() {
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void onChannelClickListener() {
        mViewPager.setCurrentItem(1);
    }

 /*   public interface onTitleClickListener {
        void onNewestClickListener();

        void onChannelClickListener();
    }*/
}
