package com.xiao.pandavmovie.ui.series.main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xiao.pandavmovie.R;
import com.xiao.pandavmovie.bean.SeriesBean;
import com.xiao.pandavmovie.utils.image.ImageCache;

import java.util.ArrayList;

/**
 * Created by StarMushroom on 2017/4/17.
 */
public class SeriesAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<SeriesBean> mList;
    private ImageCache mImageCache;

    public SeriesAdapter(Context context, ArrayList<SeriesBean> list) {
        mContext = context;
        mList = list;
        mImageCache = ImageCache.getInstanse(context);
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
        if (convertView==null){
            convertView = View.inflate(mContext,R.layout.item_fragment_series,null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }

        SeriesBean bean = mList.get(position);
     /*  vh.mImageView.setTag(bean.image);
        mImageCache.loadImages(vh.mImageView,bean.image,false);*/
        Picasso.with(mContext).load(bean.image).error(R.mipmap.default_error).into(vh.mImageView);
        vh.tv_title.setText(bean.title);
        vh.tv_update.setText("已更新至"+bean.update_to+"集");
        vh.tv_sub.setText(bean.follower_num+"人已订阅");
        vh.tv_info.setText(bean.content);
        return convertView;
    }

    class ViewHolder {
        private ImageView mImageView;
        private TextView tv_title, tv_update, tv_sub, tv_info;

        public ViewHolder(View view) {
            mImageView = (ImageView) view.findViewById(R.id.iv_item_fragment_series);
            tv_title = (TextView) view.findViewById(R.id.tv_item_fragment_series_title);
            tv_update = (TextView) view.findViewById(R.id.tv_item_fragment_series_update);
            tv_sub = (TextView) view.findViewById(R.id.tv_item_fragment_series_sub);
            tv_info = (TextView) view.findViewById(R.id.tv_item_fragment_series_info);
        }
    }
}
