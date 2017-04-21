package com.xiao.pandavmovie.ui.series.detail;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.xiao.pandavmovie.R;
import com.xiao.pandavmovie.bean.SeriesDetailBean;
import com.xiao.pandavmovie.config.Netconfig;
import com.xiao.pandavmovie.ui.series.detail.tab.SeriesDetailTabFragment;
import com.xiao.pandavmovie.widget.ExpandableTextView;

import java.util.ArrayList;


/**
 * 系列详情
 */
public class SeriesDetailActivity extends AppCompatActivity implements SeriesDetailTabFragment.CallBack {

    private VideoView mVideoView;
    private TextView tv_Title, tv_content, tv_update, tv_updata2, tv_type;
    private ExpandableTextView tv_title_content;
    private RequestQueue mQueue;
    private String mSeriesid;
    private ArrayList<String> titleTablayout = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private SeriesDetailTablayoutFragmentPagerAdapter mAdapter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_series_detail);
        mSeriesid = getIntent().getExtras().getString("seriesid");
        mQueue = Volley.newRequestQueue(this);
        initView();
        initVideoview();
        initViewpager();
        initData();
    }

    private void initVideoview() {
        mVideoView = (VideoView) findViewById(R.id.video_activity_series_detail);
        mVideoView.setVideoPath(Netconfig.url);
        mVideoView.start();
    }

    private void initViewpager() {
        mTabLayout = (TabLayout) findViewById(R.id.tablayout_activity_series_detail);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_activity_series_detail);
        mAdapter = new SeriesDetailTablayoutFragmentPagerAdapter(getSupportFragmentManager(), titleTablayout, mFragments);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabTextColors(Color.GRAY, Color.BLACK);
        mTabLayout.setSelectedTabIndicatorColor(Color.BLACK);
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    private void initData() {
        String format = String.format(Netconfig.SERIES_DETAIL, mSeriesid);
        StringRequest request = new StringRequest(
                format,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        SeriesDetailBean bean = new Gson().fromJson(s, SeriesDetailBean.class);
                        Log.d("_____", "onResponse: " + bean.toString());
                        tv_Title.setText(bean.getData().getTitle());
                        tv_update.setText("更新： " + bean.getData().getWeekly());
                        tv_updata2.setText("集数： " + bean.getData().getUpdate_to());
                        tv_type.setText("类型： " + bean.getData().getTag_name());
                        tv_title_content.setText(bean.getData().getContent());
                        tv_content.setText("第" + bean.getData().getPosts().get(0).getList().get(0).getNumber() +
                                "集：" + bean.getData().getPosts().get(0).getList().get(0).getTitle());
                        for (int i = 0; i < bean.getData().getPosts().size(); i++) {
                            titleTablayout.add(bean.getData().getPosts().get(i).getFrom_to()); //添加tablayout导航
                        }
                        mAdapter.notifyDataSetChanged();

                        //动态添加fragment
                        for (int i = 0; i < titleTablayout.size(); i++) {
                            SeriesDetailTabFragment fragment = new SeriesDetailTabFragment();
                            Bundle bundle = new Bundle();
                            bundle.putInt("index", i);
                            bundle.putString("tab", titleTablayout.get(i));
                            bundle.putString("json", s);
                            fragment.setArguments(bundle);
                            mFragments.add(fragment);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(SeriesDetailActivity.this, "连接超时", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        mQueue.add(request);
    }

    private void initView() {
        tv_Title = (TextView) findViewById(R.id.tv_activity_series_detail_title);
        tv_content = (TextView) findViewById(R.id.tv_activity_series_detail_content);
        tv_update = (TextView) findViewById(R.id.tv_activity_series_detail_update);
        tv_updata2 = (TextView) findViewById(R.id.tv_activity_series_detail_update_two);
        tv_type = (TextView) findViewById(R.id.tv_activity_series_detail_update_type);
        tv_title_content = (ExpandableTextView) findViewById(R.id.tv_activity_series_detail_tittle_content);
    }


    @Override
    public void sendInfo(String str) {
        tv_content.setText(str);
    }
}
