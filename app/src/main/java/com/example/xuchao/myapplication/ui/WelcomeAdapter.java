package com.example.xuchao.myapplication.ui;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.xuchao.myapplication.R;
import com.example.xuchao.myapplication.common.ImageLoader;
import com.example.xuchao.myapplication.common.Photo;
import com.example.xuchao.myapplication.common.RecycleMoreFooterHolder;
import com.example.xuchao.myapplication.common.RecycleSpaceHeaderHolder;
import com.example.xuchao.myapplication.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuchao on 15-7-10.
 */
public class WelcomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_FOOTER = 1;
    public static final int TYPE_CONTENT = 2;
    private Activity mActivity;
    protected boolean hasHeader;
    protected boolean hasFooter;
    protected boolean isRefresh;
    private int start;
    private int count;
    public List<Photo> photos;
    private Photo emptyPhoto;

    public WelcomeAdapter(Activity activity) {
        photos = new ArrayList<>();
        mActivity = activity;
        emptyPhoto = new Photo();
    }

    public void clearData() {
        photos.clear();
        hasHeader = true;
        photos.add(emptyPhoto);
        count=photos.size();
        notifyDataSetChanged();
    }

    public void addData(List<Photo> data) {
        if (null != data && data.size() > 0) {
            if (isRefresh) {
                clearData();
            }
            isRefresh = false;
            start = count;
            photos.addAll(data);
            count=photos.size();
            hasFooter = false;
            notifyItemRangeChanged(start, data.size());


        }

    }

    public void setMoreView() {
        hasFooter = true;
        photos.add(emptyPhoto);
        count=photos.size();
        notifyItemChanged(count - 1);


    }

    @Override
    public int getItemViewType(int position) {
        if (hasHeader && 0 == position) {
            return TYPE_HEADER;
        } else if (hasFooter && position == count - 1) {

            return TYPE_FOOTER;
        } else {
            return TYPE_CONTENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder;
        switch (viewType) {
            default:
            case TYPE_HEADER:
                View headerView = View.inflate(parent.getContext(), R.layout.recycle_space_header, null);
                holder = new RecycleSpaceHeaderHolder(headerView);
                break;
            case TYPE_CONTENT:
                View imgView = View.inflate(parent.getContext(), R.layout.item_image, null);
                holder = new ImageHolder(imgView);
                break;
            case TYPE_FOOTER:
                View footerView = View.inflate(parent.getContext(), R.layout.recycle_more_footer, null);
                holder = new RecycleMoreFooterHolder(footerView);
                break;
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            default:
            case TYPE_HEADER:
                break;
            case TYPE_CONTENT:
                ImageHolder imageHolder = (ImageHolder) holder;
                ImageLoader.load(mActivity, photos.get(position), imageHolder.imageView);
                break;
            case TYPE_FOOTER:
                break;
        }


    }

    @Override
    public int getItemCount() {
        return count;
    }

    static class ImageHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ImageHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }

}
