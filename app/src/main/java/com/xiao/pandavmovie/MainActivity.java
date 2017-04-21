package com.xiao.pandavmovie;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xiao.pandavmovie.ui.behind.behindMain.BehindFragment;
import com.xiao.pandavmovie.ui.home.coverExtra.cache.DownloadActivity;
import com.xiao.pandavmovie.ui.home.coverExtra.like.LikeActivity;
import com.xiao.pandavmovie.ui.home.coverExtra.message.MessageActivity;
import com.xiao.pandavmovie.ui.home.coverExtra.setting.SettingActivity;
import com.xiao.pandavmovie.ui.home.main.HomeFragment;
import com.xiao.pandavmovie.ui.search.SearchActivity;
import com.xiao.pandavmovie.ui.series.main.SeriesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static com.xiao.pandavmovie.R.id.iv_avtivity_main_content_side;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.tv_activity_main_content_title)
    TextView tv_title;

    @BindView(R.id.tv_activity_main_content_firstpage_title_newset)
    TextView tv_firstpage_title_newest;

    @BindView(R.id.tv_activity_main_content_firstpage_title_channel)
    TextView tv_firstpage_title_channel;

    @BindView(R.id.iv_avtivity_main_content_side)
    ImageView mImageview_side;    //显示按钮

    @BindView(R.id.iv_main_corver_close)
    ImageView mImageView_close;   //隐藏按钮

    @BindView(R.id.iv_avtivity_main_content_search)
    ImageView mImageView_search;   //搜索按钮

    /*corver层的顶部四按钮*/
    @BindView(R.id.iv_main_corver_set)
    ImageView iv_set;

    @BindView(R.id.iv_main_corver_message)
    ImageView iv_message;

    @BindView(R.id.ll_main_corver_download)
    LinearLayout ll_download;

    @BindView(R.id.ll_main_corver_like)
    LinearLayout ll_like;

    /*RadioButton切换fragment*/
    @BindView(R.id.rg_main_corver)
    RadioGroup mRadioGroup;

    @BindView(R.id.rb_main_corver_home)
    RadioButton rb_home;

    @BindView(R.id.rb_main_corver_series)
    RadioButton rb_series;

    @BindView(R.id.rb_main_corver_behind)
    RadioButton rb_behind;

    @BindView(R.id.view_main_firstpage_)
    View mView;   //小滑块

    @BindView(R.id.main_cover)
    View mCover; //浮层布局

    @BindView(R.id.frame_activity_main_content_firstpage_Title)
    FrameLayout firsttitle;

    private long mExitTime;
    private Fragment currentFragent;  //正在显示的fragment

    //接口回调，点击title切换fragment
    private onTitleClickListener mListener;

    public void setListener(onTitleClickListener listener) {
        mListener = listener;
    }

    public interface onTitleClickListener {

        void onNewestClickListener();

        void onChannelClickListener();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        rb_home.setChecked(true);
        changeFragment(HomeFragment.TAG);
        mRadioGroup.setOnCheckedChangeListener(this);
    }

    @OnClick({R.id.iv_avtivity_main_content_side,
            R.id.iv_main_corver_close,
            R.id.iv_avtivity_main_content_search,
            R.id.tv_activity_main_content_firstpage_title_newset,
            R.id.tv_activity_main_content_firstpage_title_channel,
            R.id.iv_main_corver_set,
            R.id.iv_main_corver_message,
            R.id.ll_main_corver_download,
            R.id.ll_main_corver_like})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.iv_avtivity_main_content_side:   //点击弹出corver层
                mCover.setVisibility(View.VISIBLE);
                ObjectAnimator scalX = ObjectAnimator.ofFloat(mImageView_close, "scalX", 0, 1.2f, 1);
                ObjectAnimator scalY = ObjectAnimator.ofFloat(mImageView_close, "scalY", 0, 1.2f, 1);
                AnimatorSet set = new AnimatorSet();
                set.setDuration(1000);
                set.play(scalX).with(scalY);
                set.start();
                break;
            case R.id.iv_main_corver_close:     //点击隐藏corver层
                mCover.setVisibility(View.GONE);
                break;
            case R.id.iv_avtivity_main_content_search:    //跳转搜索页面
                startActivity(new Intent(this, SearchActivity.class));
                break;
            case R.id.tv_activity_main_content_firstpage_title_newset:
                mListener.onNewestClickListener();
                break;
            case R.id.tv_activity_main_content_firstpage_title_channel:
                mListener.onChannelClickListener();
                break;

            case R.id.iv_main_corver_set:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.iv_main_corver_message:
                startActivity(new Intent(this, MessageActivity.class));
                break;
            case R.id.ll_main_corver_download:
                startActivity(new Intent(this, DownloadActivity.class));
                break;
            case R.id.ll_main_corver_like:
                startActivity(new Intent(this, LikeActivity.class));
                break;
        }
    }


    @OnClick(R.id.rg_main_corver)
    public void onClick(View view){

    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_main_corver_home:    //首页
                changeFragment(HomeFragment.TAG);
                tv_title.setVisibility(View.GONE);
                firsttitle.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_main_corver_series:  //系列
                changeFragment(SeriesFragment.TAG);
                firsttitle.setVisibility(View.GONE);
                tv_title.setVisibility(View.VISIBLE);
                tv_title.setText("系列");
                break;
            case R.id.rb_main_corver_behind:  //幕后
                changeFragment(BehindFragment.TAG);
                firsttitle.setVisibility(View.GONE);
                tv_title.setVisibility(View.VISIBLE);
                tv_title.setText("幕后");
                break;
        }
        mCover.setVisibility(View.GONE);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Object mHelperUtils;
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 切换碎片
     */
    private void changeFragment(String flag) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        if (currentFragent != null) {
            transaction.hide(currentFragent);
        }
        currentFragent = manager.findFragmentByTag(flag);
        if (currentFragent != null) {
            transaction.show(currentFragent);
        } else {
            try {
                currentFragent = (Fragment) Class.forName(flag).newInstance();
                transaction.add(R.id.main_container, currentFragent, flag);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        transaction.commit();
    }

    public void changeTitle(float onffect) {
        int width = firsttitle.getWidth();
        mView.setTranslationX(onffect * width / 2);
    }

}
