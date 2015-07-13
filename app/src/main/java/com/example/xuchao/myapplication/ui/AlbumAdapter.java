package com.example.xuchao.myapplication.ui;

import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xuchao.myapplication.R;
import com.example.xuchao.myapplication.model.ImageModel;
import com.example.xuchao.myapplication.util.LogUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by xuchao on 15-6-15.
 */
public class AlbumAdapter extends BaseAdapter {

    private static final String TAG = LogUtils.makeLogTag(AlbumActitvity.class);
    private ArrayList<ImageModel> mImages;
    protected ArrayList<Boolean> selItems;
    private DisplayImageOptions options;

    public AlbumAdapter() {
        mImages = new ArrayList<>();
        mImages.add(null);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.album_empty)
                .showImageForEmptyUri(R.drawable.album_empty)
                .showImageOnFail(R.drawable.album_load_failed)
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .build();

        selItems = new ArrayList<>();
    }

    public void addData(Collection<ImageModel> data) {

        if (null != data) {
            mImages.addAll(data);
            for (int i = 0; i < data.size(); i++) {
                selItems.add(false);
            }
        }
        notifyDataSetChanged();

    }

    public void clearData() {
        mImages.clear();
        notifyDataSetChanged();


    }


    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = View.inflate(parent.getContext(), R.layout.item_ablum, null);
            holder = new ViewHolder(convertView);

        }


        holder = (ViewHolder) convertView.getTag();
        if (position == 0) {
            holder.ivPic.setImageResource(R.drawable.album_camera_normal);
            holder.tvSel.setVisibility(View.GONE);
            return convertView;
        }
        if (selItems.get(position)) {
            convertView.setAlpha(0.3f);
            holder.tvSel.setVisibility(View.VISIBLE);
        } else {
            convertView.setAlpha(1f);
            holder.tvSel.setVisibility(View.GONE);
        }
        LogUtils.LOGD("tag",mImages.get(position).preview);
        ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.FILE.wrap(mImages.get(position).preview), holder.ivPic, options);


        return convertView;
    }

    private static class ViewHolder {
        ImageView ivPic;
        TextView tvSel;

        public ViewHolder(View itemView) {
            itemView.setTag(this);
            ivPic = (ImageView) itemView.findViewById(R.id.iv_pic);
            tvSel = (TextView) itemView.findViewById(R.id.tv_sel);

        }
    }
}
