package com.example.xuchao.myapplication.ui;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xuchao.myapplication.R;
import com.example.xuchao.myapplication.model.ImageModel;
import com.example.xuchao.myapplication.util.LogUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoView;

/**
 * Created by xuchao on 15-6-18.
 */
public class GalleryAdapter extends PagerAdapter {

    private static final String TAG = LogUtils.makeLogTag(GalleryActivity.class);
    private ArrayList<ImageModel> mImages;
    private DisplayImageOptions localOptions, webOptions;
    private int mImageFrom;

    public void addData(ArrayList<ImageModel> mImages, int imageFrom) {
        this.mImages.addAll(mImages);
        this.mImageFrom = imageFrom;
        notifyDataSetChanged();
    }

    public void clearData() {
        this.mImages.clear();
        this.mImageFrom = 0;
        notifyDataSetChanged();
    }


    GalleryAdapter() {
        mImages = new ArrayList<>();
        localOptions = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true)
                .showImageOnLoading(R.drawable.album_empty)
                .showImageForEmptyUri(R.drawable.album_empty)
                .showImageOnFail(R.drawable.album_load_failed)
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .build();
        webOptions = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(true)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .build();
    }


    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        PhotoView photoView;
        switch (mImageFrom) {
            case GalleryActivity.IMAGE_FROM_LOCAL: {
                photoView = new PhotoView(container.getContext());
                ImageLoader.getInstance().displayImage(ImageDownloader.Scheme.FILE.wrap(mImages.get(position).preview), photoView, localOptions);
                container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                return photoView;
            }
            case GalleryActivity.IMAGE_FROM_WEB: {
                View view = View.inflate(container.getContext(), R.layout.item_gallery, null);
                photoView = (PhotoView) view.findViewById(R.id.pv_pic);
                final TextView tvProgress = (TextView) view.findViewById(R.id.tv_progress);
                ImageLoader.getInstance().displayImage(mImages.get(position).preview, photoView, webOptions, new SimpleImageLoadingListener(),
                        new ImageLoadingProgressListener() {
                            @Override
                            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                                tvProgress.setText(current / total + "%");
                            }
                        });
                container.addView(view);
                return view;
            }
            default:
                photoView = new PhotoView(container.getContext());
                photoView.setImageResource(R.drawable.album_empty);
                return photoView;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
