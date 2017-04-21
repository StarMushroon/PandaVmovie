package com.xiao.pandavmovie.ui.series.detail.tab;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.xiao.pandavmovie.R;
import com.xiao.pandavmovie.bean.SeriesDetailBean;

import java.util.ArrayList;

/**
 * Created by StarMushroom on 2017/4/18.
 */
public class SeriesDetailTabFragment extends Fragment implements AdapterView.OnItemClickListener {

    private String mTab, json;
    private int mIndex;
    private ArrayList<SeriesDetailBean.DataBean.PostsBean.ListBean> mList = new ArrayList<>();
    private SeriesDetailTabAdapter mAdapter;
    private ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_series_detail_tab, null);
        TextView textView = (TextView) view.findViewById(R.id.tv_);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTab = bundle.getString("tab");
            mIndex = bundle.getInt("index");
            json = bundle.getString("json");
        }
        textView.setText("mTab: " + mTab + " mIndex: " + mIndex);

        initListview(view);
        return view;
    }

    private void initListview(View view) {
        mListView = (ListView) view.findViewById(R.id.lv_fragment_series_detail_tab);
        mAdapter = new SeriesDetailTabAdapter(getContext(), mList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        loadData();
    }

    private void loadData() {
        SeriesDetailBean bean = new Gson().fromJson(json, SeriesDetailBean.class);
        mList.addAll(bean.getData().getPosts().get(mIndex).getList());
        mAdapter.notifyDataSetChanged();
    }

    //TODO  回调给SeriesDetailActivity传递数据
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SeriesDetailBean.DataBean.PostsBean.ListBean listBean = mList.get(position);
        mCallBack.sendInfo("第"+listBean.getNumber()+"集："+listBean.getTitle());
    }

    private CallBack mCallBack;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallBack = (CallBack) activity;
    }

    public interface CallBack{
        void sendInfo(String str);
    }
}
