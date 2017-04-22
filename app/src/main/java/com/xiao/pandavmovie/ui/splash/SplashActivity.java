package com.xiao.pandavmovie.ui.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.xiao.pandavmovie.MainActivity;
import com.xiao.pandavmovie.R;
import com.xiao.pandavmovie.utils.SharedParams;
import com.xiao.pandavmovie.utils.SharedUtil;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener {

    private ImageView mImageView;
    private ScaleAnimation mAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initView();
        initAnimation();
        initListen();
    }

    private void initListen() {
        mAnimation.setAnimationListener(this);
    }

    private void initAnimation() {
        mAnimation = new ScaleAnimation(1, 1.05f, 1, 1.05f);
        mAnimation.setFillAfter(true);
        mAnimation.setDuration(2000);
        mImageView.startAnimation(mAnimation);
    }

    private void initView() {
        mImageView = (ImageView) findViewById(R.id.iv_activity_splash);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {



        if (SharedUtil.getFlag(SharedParams.USE_FLAG)) {
            // 去主页面
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }else{
            // 缓存打开过的标记
            SharedUtil.putFlag(SharedParams.USE_FLAG,true);
            // 去导航页面
            startActivity(new Intent(this, GuideActivity.class));
            finish();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
