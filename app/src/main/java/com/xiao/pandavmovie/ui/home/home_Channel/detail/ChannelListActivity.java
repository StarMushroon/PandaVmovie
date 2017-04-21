package com.xiao.pandavmovie.ui.home.home_Channel.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.xiao.pandavmovie.bean.HomeNewestListBean;
import com.xiao.pandavmovie.config.Netconfig;
import com.xiao.pandavmovie.ui.home.home_Newest.detail.NewestDetailActivity;
import com.xiao.pandavmovie.ui.home.home_Newest.main.NewestAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 频道点进去的列表页
 * 和最新的列表页一样，复用其NewestAdapter、ArrayList<HomeNewestListBean>
 */
public class ChannelListActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private String mCateid;
    private String mCatename;
    private PullToRefreshListView mListView;
    private NewestAdapter mAdapter;
    private ArrayList<HomeNewestListBean> mListBeen = new ArrayList<>();
    private RequestQueue mQueue;
    private ImageView ivBack;    //返回按钮
    private TextView tvTitle;      //顶部标题

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_channel_list);
        mCateid = getIntent().getExtras().getString("cateid");
        mCatename = getIntent().getExtras().getString("catename");
        ivBack = (ImageView) findViewById(R.id.iv_back_activity_chanel_list);
        tvTitle = (TextView) findViewById(R.id.tv_activtity_channel_list_title);
        tvTitle.setText(mCatename);
        ivBack.setOnClickListener(this);
        mQueue = Volley.newRequestQueue(this);
        initListview();
    }

    private void initListview() {
        mListView = (PullToRefreshListView) findViewById(R.id.listview_activity_channel);
        mAdapter = new NewestAdapter(this, mListBeen);
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
        loaddata(mCateid);
        Toast.makeText(this, ""+mCateid, Toast.LENGTH_SHORT).show();
    }

    private void loaddata(String cateid) {
        StringRequest request = new StringRequest(
                Netconfig.HOME_CHANNEL_DETAIL + cateid,
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
                        Toast.makeText(ChannelListActivity.this, "连接超时..", Toast.LENGTH_SHORT).show();
                    }
                });
        mQueue.add(request);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    /**
     * 点击去详情页 和最新界面的详情页一样 复用
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,NewestDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("request_url",mListBeen.get(position).request_url);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
