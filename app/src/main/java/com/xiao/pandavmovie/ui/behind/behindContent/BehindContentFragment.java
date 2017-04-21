package com.xiao.pandavmovie.ui.behind.behindContent;

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
import com.xiao.pandavmovie.bean.BehindContentBean;
import com.xiao.pandavmovie.config.Netconfig;
import com.xiao.pandavmovie.utils.image.ImageCache;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 幕后界面的内容区
 * Created by StarMushroom on 2017/4/17.
 */
public class BehindContentFragment extends Fragment implements AdapterView.OnItemClickListener {

    private PullToRefreshListView mListView;
    private BehindContentAdapter mAdapter;
    private ArrayList<BehindContentBean> mList = new ArrayList<>();
    private String mCatename;
    private String mCateid =2+"";
    private int pageIndex =1;
    private RequestQueue mQueue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQueue = Volley.newRequestQueue(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_behind_content, null);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCatename = bundle.getString("catename");
            mCateid = bundle.getString("cateid");
        }

        initListview(view);
        return view;
    }

    private void initListview(View view) {
        mListView = (PullToRefreshListView) view.findViewById(R.id.pulltoRefresh_fragment_behind_content);
        mAdapter = new BehindContentAdapter(getContext(), mList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

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
        loadData(mCateid);
    }

    private void loadData(String cateid) {
        String format = String.format(Netconfig.BEHIND_CONTENT, mCateid,pageIndex);
        StringRequest request = new StringRequest(format,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject object = new JSONObject(s);
                            JSONArray array = object.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);
                                BehindContentBean bean = new BehindContentBean();
                                bean.postid = obj.optString("postid");
                                bean.image = obj.optString("image");
                                bean.title = obj.optString("title");
                                bean.like_num = obj.optString("like_num");
                                bean.share_num = obj.optString("share_num");
                                bean.request_url = obj.optString("request_url");
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
    public void onDestroy() {
        super.onDestroy();
        ImageCache.getInstanse(getContext()).onDestroy();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(),BehindContentWebActivity.class);
        intent.putExtra("webUrl",mList.get(position-1).request_url);
        intent.putExtra("title",mList.get(position-1).title);
        startActivity(intent);
    }
}
