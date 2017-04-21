package com.xiao.pandavmovie.ui.home.home_Channel.main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xiao.pandavmovie.R;
import com.xiao.pandavmovie.bean.HomeChannelBean;
import com.xiao.pandavmovie.utils.image.ImageCache;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by StarMushroom on 2017/4/17.
 */
public class ChannelAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<HomeChannelBean> mList;
    private ImageCache mImageCache;

    public ChannelAdapter(Context context, ArrayList<HomeChannelBean> list) {
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
        Viewholder vh;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_fragment_channel, null);
            vh = new Viewholder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (Viewholder) convertView.getTag();
        }

        Picasso.with(mContext).load(mList.get(position).icon).error(R.mipmap.default_error).into(vh.iv);
        vh.tv.setText("#" + mList.get(position).catename + "#");

        return convertView;
    }

    class Viewholder {
        @BindView(R.id.iv_item_fragment_channel)
        ImageView iv;

        @BindView(R.id.tv_item_fragment_channel)
        TextView tv;

        public Viewholder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
