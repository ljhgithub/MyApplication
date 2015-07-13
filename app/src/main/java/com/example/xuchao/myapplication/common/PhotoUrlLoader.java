package com.example.xuchao.myapplication.common;

import android.content.Context;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;
import com.example.xuchao.myapplication.util.LogUtils;

import java.io.InputStream;

/**
 * Created by xuchao on 15-7-9.
 */
public class PhotoUrlLoader extends BaseGlideUrlLoader<PhotoUrlModel> {

    public PhotoUrlLoader(Context context) {
        super(context);
    }


    @Override
    protected String getUrl(PhotoUrlModel model, int width, int height) {
        return model.buildUrl(width, height);
    }



}

