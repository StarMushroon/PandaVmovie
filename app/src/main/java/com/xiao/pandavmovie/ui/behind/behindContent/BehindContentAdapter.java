package com.xiao.pandavmovie.ui.behind.behindContent;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xiao.pandavmovie.R;
import com.xiao.pandavmovie.bean.BehindContentBean;
import com.xiao.pandavmovie.utils.image.ImageCache;

import java.util.ArrayList;

/**
 * Created by StarMushroom on 2017/4/17.
 */
public class BehindContentAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<BehindContentBean> mList;
  private ImageCache mImageCache;

    public BehindContentAdapter(Context context, ArrayList<BehindContentBean> list) {
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
            convertView = View.inflate(mContext,R.layout.item_fragment_behind_content,null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }

        BehindContentBean bean = mList.get(position);
      /*  vh.mImage.setTag(bean.image);
        mImageCache.loadImages(vh.mImage,bean.image,false);*/
        Picasso.with(mContext).load(bean.image).error(R.mipmap.default_error).into(vh.mImage);
        vh.tv_title.setText(bean.title);
        vh.tv_like.setText(bean.like_num);
        vh.tv_share.setText(bean.share_num);

        return convertView;
    }

    class ViewHolder {
        private ImageView mImage;
        private TextView tv_title, tv_like, tv_share;

        public ViewHolder(View v) {
            mImage = (ImageView) v.findViewById(R.id.iv_item_fragment_behind_content);
            tv_title = (TextView) v.findViewById(R.id.tv_item_fragment_behind_content_title);
            tv_like = (TextView) v.findViewById(R.id.tv_item_fragment_behind_content_like);
            tv_share = (TextView) v.findViewById(R.id.tv_item_fragment_behind_content_share);
        }
    }
}
