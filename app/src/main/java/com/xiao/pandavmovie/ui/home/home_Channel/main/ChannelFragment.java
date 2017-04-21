package com.xiao.pandavmovie.ui.home.home_Channel.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xiao.pandavmovie.R;
import com.xiao.pandavmovie.bean.HomeChannelBean;
import com.xiao.pandavmovie.config.Netconfig;
import com.xiao.pandavmovie.ui.home.home_Channel.detail.ChannelListActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * 主页频道
 * Created by StarMushroom on 2017/4/15.
 */

public class ChannelFragment extends Fragment implements AdapterView.OnItemClickListener {

    private GridView mListView;
    private ChannelAdapter mAdapter;
    private ArrayList<HomeChannelBean> mList = new ArrayList<>();
    private RequestQueue mQueue;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQueue = Volley.newRequestQueue(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_channel, null);
        initGridView(view);
        return view;
    }

    private void initGridView(View view) {
        mListView = (GridView) view.findViewById(R.id.gridview_fragment_channel);
        mAdapter = new ChannelAdapter(getContext(),mList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        loadaData();
    }

    private void loadaData() {
        StringRequest request = new StringRequest(Netconfig.HOME_CHANNEL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject object = new JSONObject(s);
                            JSONArray array = object.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);
                                HomeChannelBean bean = new HomeChannelBean();
                                bean.orderid = obj.optString("orderid");
                                bean.cateid = obj.optString("cateid");
                                bean.icon = obj.optString("icon");
                                bean.catename = obj.optString("catename");
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
                        Toast.makeText(getContext(), "连接超时..", Toast.LENGTH_SHORT).show();
                    }
                });
        mQueue.add(request);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(),ChannelListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("cateid",mList.get(position).cateid);
        bundle.putString("catename",mList.get(position).catename);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
