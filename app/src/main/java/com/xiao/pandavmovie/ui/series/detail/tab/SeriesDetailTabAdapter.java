package com.xiao.pandavmovie.ui.series.detail.tab;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xiao.pandavmovie.R;
import com.xiao.pandavmovie.bean.SeriesDetailBean;

import java.util.ArrayList;

/**
 * Created by StarMushroom on 2017/4/19.
 */

public class SeriesDetailTabAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<SeriesDetailBean.DataBean.PostsBean.ListBean> mList;

    public SeriesDetailTabAdapter(Context context, ArrayList<SeriesDetailBean.DataBean.PostsBean.ListBean> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_fragment_series_detail_tab, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        SeriesDetailBean.DataBean.PostsBean.ListBean bean = mList.get(position);
        Picasso.with(mContext).load(bean.getThumbnail()).error(R.mipmap.default_error).into(vh.iv);
        vh.tv_info.setText("第"+bean.getNumber()+"集："+bean.getTitle());
        vh.tv_date.setText(bean.getAddtime());

        //TODO  时间格式化
//        vh.tv_durtation.setText(bean.getDuration());

        int duration = bean.getDuration();
        int i = duration / 60;
        int i1 = duration % 60;
        vh.tv_durtation.setText(i +"'"+i1);

        return convertView;
    }

    class ViewHolder {

        private ImageView iv;
        private TextView tv_info, tv_date, tv_durtation, tv_showPlay;

        public ViewHolder(View view) {
            iv = (ImageView) view.findViewById(R.id.iv_item_fragment_series_detail_tab);
            tv_info = (TextView) view.findViewById(R.id.tv_item_fragment_series_detail_tab_info);
            tv_date = (TextView) view.findViewById(R.id.tv_item_fragment_series_detail_tab_date);
            tv_durtation = (TextView) view.findViewById(R.id.tv_item_fragment_series_detail_tab_durtation);
            tv_showPlay = (TextView) view.findViewById(R.id.tv_item_fragment_series_detail_tab_showPlaying);
        }
    }
}
