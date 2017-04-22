package com.xiao.pandavmovie.ui.splash;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiao.pandavmovie.MainActivity;
import com.xiao.pandavmovie.R;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity  implements ViewPager.OnPageChangeListener, View.OnClickListener  {

    private ViewPager mViewPager;
    private ArrayList<ImageView> mPoints;   //存放底部三个小圆点
    private ArrayList<ImageView> mViews;
    private TextView mTextView;  //点击体验

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guide);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initData();
        initViewPager();
        initTextview();
    }

    private void initTextview() {

        //点击跳转
        mTextView = (TextView) findViewById(R.id.tv_hello);
        mTextView.setOnClickListener(this);
    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.vp_hello);
        SplashPagerAdapter mAdapter = new SplashPagerAdapter(mViews);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);
    }

    private void initData() {
        mPoints = new ArrayList<>();
        mViews = new ArrayList<>();

        //存放小圆点
        mPoints.add((ImageView) findViewById(R.id.iv_hello_1));
        mPoints.add((ImageView) findViewById(R.id.iv_hello_2));
        mPoints.add((ImageView) findViewById(R.id.iv_hello_3));
        mPoints.get(0).setSelected(true);

        //存放三张引导图片
        for (int i = 0; i < 3; i++) {
            ImageView image = new ImageView(this);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            int mipmap = getResources().getIdentifier("guide_bg" + (1 + i), "mipmap", getPackageName());
            image.setImageResource(mipmap);
            mViews.add(image);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        //小圆点跟随滑动变换
        for (int i = 0; i < mViews.size(); i++) {
            mPoints.get(i).setSelected(i == position);
        }
        if (position == mViews.size() - 1) {
            mTextView.setVisibility(View.VISIBLE);
        } else {
            mTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(GuideActivity.this, MainActivity.class));
        this.finish();
    }
}
