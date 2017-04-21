package com.xiao.pandavmovie.ui.behind.behindMain;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.xiao.pandavmovie.R;
import com.xiao.pandavmovie.bean.BehindTabBean;
import com.xiao.pandavmovie.config.Netconfig;
import com.xiao.pandavmovie.ui.behind.behindContent.BehindContentFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * 幕后
 * Created by StarMushroom on 2017/4/15.
 */

public class BehindFragment extends Fragment {

    public static final String TAG = BehindFragment.class.getName();

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private BehindFragmentPagerAdapter mPagerAdapter;
    private ArrayList<Fragment> mFragments = new ArrayList<>();   //viewpager碎片
    private ArrayList<BehindTabBean> titles = new ArrayList<>();  //标题导航
    private RequestQueue mQueue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQueue = Volley.newRequestQueue(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_behind, null);
        initViewpager(view);
        return view;
    }

    private void initViewpager(View view) {
        mTabLayout = (TabLayout) view.findViewById(R.id.tablayout_fragment_behind);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager_fragment_behind);
        mPagerAdapter = new BehindFragmentPagerAdapter(getChildFragmentManager(), mFragments, titles);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabTextColors(Color.GRAY, Color.BLACK);
        mTabLayout.setSelectedTabIndicatorColor(Color.BLACK);
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        loadTab();  //加载顶部导航
    }


    private void loadTab() {
        StringRequest request = new StringRequest(Netconfig.BEHIND_TAB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        try {
                            JSONObject object = new JSONObject(s);
                            JSONArray array = object.getJSONArray("data");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);
                                BehindTabBean tabBean = new BehindTabBean();
                                tabBean.cateid = obj.optString("cateid");
                                tabBean.catename = obj.optString("catename");
                                titles.add(tabBean);
                            }
                            mPagerAdapter.notifyDataSetChanged();
                            for (int i = 0; i < titles.size(); i++) {
                                BehindContentFragment fragment = new BehindContentFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("cateid", titles.get(i).cateid);
                                bundle.putString("catename", titles.get(i).catename);
                                fragment.setArguments(bundle);
                                mFragments.add(fragment);
                            }
                            mPagerAdapter.notifyDataSetChanged();
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

}
