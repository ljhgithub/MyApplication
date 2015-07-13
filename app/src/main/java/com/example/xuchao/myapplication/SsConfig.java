package com.example.xuchao.myapplication;

import android.graphics.Bitmap;

/**
 * Created by xuchao on 15-6-17.
 */
public class SsConfig {

    public static final boolean DEBUG = BuildConfig.DEBUG;
    public static final String IMAGE_CACHE_DIR = "/ss/cache/image";
    public static final Bitmap.CompressFormat IMAGE_CACHE_FORMAT = Bitmap.CompressFormat.PNG;
    // 图片缓存合成质量
    public static final int IMAGE_CACHE_QUALITY = 100;
    public static final String PACKAGE_NAME = BuildConfig.APPLICATION_ID;
    //图片缓存路径
    public static final String CACHE_IMAGE_PATH = "/image";

}
