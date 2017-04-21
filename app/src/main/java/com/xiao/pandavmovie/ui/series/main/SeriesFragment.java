package com.xiao.pandavmovie.ui.series.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xiao.pandavmovie.R;
import com.xiao.pandavmovie.bean.SeriesBean;
import com.xiao.pandavmovie.config.Netconfig;
import com.xiao.pandavmovie.ui.series.detail.SeriesDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * 系列
 * Created by StarMushroom on 2017/4/15.
 */

public class SeriesFragment extends Fragment implements AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2 {

    public static final String TAG = SeriesFragment.class.getName();
    private PullToRefreshListView mListView;
    private SeriesAdapter mAdapter;
    private ArrayList<SeriesBean> mList = new ArrayList<>();
    private RequestQueue mQueue;
    private int pageIndex = 1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQueue = Volley.newRequestQueue(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_series, null);
        initPullToRefresh(view);
        return view;
    }

    private void initPullToRefresh(View view) {
        mListView = (PullToRefreshListView) view.findViewById(R.id.pull_fragment_series);
        mAdapter = new SeriesAdapter(getContext(), mList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        mListView.setOnRefreshListener(this);

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
        loadData(1);
    }

    private void loadData(int pageIndex) {
        StringRequest request = new StringRequest(Netconfig.SERIES + pageIndex,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject object = new JSONObject(s);
                            JSONArray array = object.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);
                                SeriesBean bean = new SeriesBean();
                                bean.title = obj.optString("title");
                                bean.image = obj.optString("image");
                                bean.seriesid = obj.optString("seriesid");
                                bean.update_to = obj.optString("update_to");
                                bean.follower_num = obj.optString("follower_num");
                                bean.content = obj.optString("content");
                                mList.add(bean);
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
                        Toast.makeText(getContext(), "连接超时", Toast.LENGTH_SHORT).show();
                    }
                });
        mQueue.add(request);
    }

    /**
     * 点击跳转详情界面
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), SeriesDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("seriesid", mList.get(position-1).seriesid);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 下拉刷新加载
     *
     * @param refreshView
     */
    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        mList.clear();
        loadData(1);
    }

    /**
     * 上拉加载更多
     *
     * @param refreshView
     */
    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
         pageIndex++;
        loadData(pageIndex);
    }
}
