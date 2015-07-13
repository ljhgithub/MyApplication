package com.example.xuchao.myapplication.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.example.xuchao.myapplication.R;
import com.example.xuchao.myapplication.model.ImageModel;
import com.example.xuchao.myapplication.util.LogUtils;

import java.util.ArrayList;

/**
 * Created by xuchao on 15-6-17.
 */
public class GalleryActivity extends FragmentActivity {
    private static final String TAG = LogUtils.makeLogTag(GalleryActivity.class);
    public static final String EXTRA_IMAGE = "com.example.xuchao.image";
    public static final String EXTRA_IMAGE_FROM = "com.example.xuchao.image.from";
    public static final int IMAGE_FROM_WEB = 1;
    public static final int IMAGE_FROM_LOCAL = 2;
    private GalleryAdapter adapter;
    private ArrayList<ImageModel> mImages;
    private int mImageFrom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ViewPager vpGallery = (ViewPager) findViewById(R.id.vp_image);
        mImages = getIntent().getParcelableArrayListExtra(EXTRA_IMAGE);
        mImageFrom = getIntent().getIntExtra(EXTRA_IMAGE_FROM, 0);

        if (null == mImages || mImages.size() < 1 || 0 == mImageFrom) {
            finish();
            return;
        }
        adapter = new GalleryAdapter();
        vpGallery.setAdapter(adapter);
        adapter.addData(mImages, mImageFrom);
    }
}
