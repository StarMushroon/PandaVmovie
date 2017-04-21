package com.xiao.pandavmovie.ui.home.home_Newest.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xiao.pandavmovie.R;
import com.xiao.pandavmovie.bean.HomeNewestBannerBean;
import com.xiao.pandavmovie.bean.HomeNewestListBean;
import com.xiao.pandavmovie.config.Netconfig;
import com.xiao.pandavmovie.ui.home.home_Newest.detail.NewestDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.studyou.library.view.BannerLayout;


/**
 * 主页  最新
 * Created by StarMushroom on 2017/4/15.
 */

public class NewestFragment extends Fragment implements PullToRefreshBase.OnRefreshListener2, BannerLayout.OnBannerItemClickListener, AdapterView.OnItemClickListener, AbsListView.OnScrollListener {

    private BannerLayout mBanner;
    private RequestQueue mQueue;
    private ArrayList<String> mUrl = new ArrayList<>();
    private PullToRefreshListView mListView;
    private ArrayList<HomeNewestListBean> mListBeen = new ArrayList<>();
    private BaseAdapter mAdapter;
    private int pageIndex = 1;
    private ArrayList<String> mBannerUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQueue = Volley.newRequestQueue(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newest, null);

        /*下面的listview列表*/
        initListview(view);

        /*顶部轮播图 为listview添加头布局*/
        initBanner();

        return view;
    }

    private void initListview(View view) {
        mListView = (PullToRefreshListView) view.findViewById(R.id.listview_fragment_newest);
        mAdapter = new NewestAdapter(getContext(), mListBeen);
        mListView.setAdapter(mAdapter);
        mListView.setOnRefreshListener(this);
        mListView.setOnItemClickListener(this);
        mListView.setOnScrollListener(this);

        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        //设置刷新时显示的文本
        ILoadingLayout startLayout = mListView.getLoadingLayoutProxy(true,false);
        startLayout.setPullLabel("正在下拉刷新...");
        startLayout.setRefreshingLabel("正在玩命加载中...");
        startLayout.setReleaseLabel("放开以刷新");
        ILoadingLayout endLayout = mListView.getLoadingLayoutProxy(false,true);
        endLayout.setPullLabel("正在上拉刷新...");
        endLayout.setRefreshingLabel("正在玩命加载中...");
        endLayout.setReleaseLabel("放开以刷新");
        loadListData(1);
    }

    private void loadListData(int pageIndex) {
        StringRequest request = new StringRequest(Netconfig.HOME_NEWEST_LIST + pageIndex,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject object = new JSONObject(s);
                            JSONArray array = object.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);
                                HomeNewestListBean listbean = new HomeNewestListBean();
                                listbean.postid = obj.optString("postid");
                                listbean.title = obj.optString("title");
                                listbean.duration = obj.optInt("duration");
                                listbean.image = obj.optString("image");
                                listbean.request_url = obj.optString("request_url");
                                listbean.publish_time = obj.optString("publish_time");
                                JSONArray job = obj.getJSONArray("cates");
                                JSONObject oo = job.getJSONObject(0);
                                listbean.catename = oo.optString("catename");
                                mListBeen.add(listbean);
                            }
                            mAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getContext(), "连接超时..", Toast.LENGTH_SHORT).show();
                    }
                });
        mQueue.add(request);
    }

    /**
     * 添加头布局q
     */
    private void initBanner() {
        ListView listView = mListView.getRefreshableView();
        View header = View.inflate(getContext(), R.layout.fragment_newest_header, null);
        mBanner = (BannerLayout) header.findViewById(R.id.viepager_fragment_newest);
        mBanner.setOnBannerItemClickListener(this);  //轮播图的点击
        loadBannerData(); //解析加载数据
        listView.addHeaderView(header);
    }

    private void loadBannerData() {
        StringRequest request = new StringRequest(Netconfig.HOME_NEWEST_BANNER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            mBannerUrl = new ArrayList<>();
                            JSONObject object = new JSONObject(s);
                            JSONArray array = object.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);
                                HomeNewestBannerBean bean = new HomeNewestBannerBean();
                                bean.bannerid = obj.optString("bannerid");
                                bean.image = obj.optString("image");
                                JSONObject jObj = obj.optJSONObject("extra_data");
                                bean.app_banner_param = jObj.optString("app_banner_param");
                                mUrl.add(bean.image);
                                mBannerUrl.add(bean.app_banner_param);
                            }
                            mBanner.setViewUrls(mUrl);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(getContext(), "连接超时..", Toast.LENGTH_SHORT).show();
                    }
                });
        mQueue.add(request);
    }

    /**
     * 顶部轮播图的点击事件 跳转详情
     * mBannerUrl.get(position)   很蛋疼
     *
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getContext(), NewestDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("request_url", mBannerUrl.get(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 下拉刷新
     *
     * @param refreshView
     */
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
            mListBeen.clear();
    }

    //上拉加载更多
    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        pageIndex++;
        loadListData(pageIndex);
    }

    /**
     * listview列表点击跳转详情
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(getContext(), NewestDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("request_url", mListBeen.get(position - 2).request_url);
        intent.putExtras(bundle);
        startActivity(intent);

        //TODO position是从2开始的
        Toast.makeText(getContext(), "" + (position - 2), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
