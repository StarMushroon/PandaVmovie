package com.xiao.pandavmovie.ui.home.home_Newest.main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xiao.pandavmovie.R;
import com.xiao.pandavmovie.bean.HomeNewestListBean;
import com.xiao.pandavmovie.utils.TimeUtil;
import com.xiao.pandavmovie.utils.image.ImageCache;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by StarMushroom on 2017/4/17.
 */

public class NewestAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<HomeNewestListBean> mList;
    private ImageCache mImageCache;

    public NewestAdapter(Context context, ArrayList<HomeNewestListBean> list) {
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
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_fragment_newest, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tv_title.setText(mList.get(position).title);
        vh.tv_type.setText(mList.get(position).catename);
        int duration = mList.get(position).duration;
        int i = duration / 60;
        int i1 = duration % 60;
        vh.tv_duration.setText(i + "'" + i1);
        Picasso.with(mContext).load(mList.get(position).image).error(R.mipmap.default_error).into(vh.mImageView);


     /*   vh.tv_date.setText(s);*/

        //TODO  首页 日期的滑动改变
     /*   String publish_time = mList.get(position).publish_time;
        if (publish_time != null) {
          //  SimpleDateFormat format = new SimpleDateFormat("MM-dd");
            long time = Long.parseLong(publish_time + "000");
            String formatTime = TimeUtil.getFormatTime(time, "MM-dd");
            //  String s = format.format(new Date(time));
            vh.tv_date.setText(formatTime);
        }else {

        }*/
        return convertView;
    }

    class ViewHolder {
        private ImageView mImageView;
        private TextView tv_title, tv_type, tv_duration, tv_date;

        public ViewHolder(View view) {
            mImageView = (ImageView) view.findViewById(R.id.imageview_item_fragment_newest);
            tv_title = (TextView) view.findViewById(R.id.tv_item_fragment_newest_title);
            tv_type = (TextView) view.findViewById(R.id.tv_item_fragment_newest_type);
            tv_duration = (TextView) view.findViewById(R.id.tv_item_fragment_newest_durtaion);
           // tv_date = (TextView) view.findViewById(R.id.tv_date_newest);
        }
    }
}
