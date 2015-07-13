package com.example.xuchao.myapplication.common;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.xuchao.myapplication.R;
import com.example.xuchao.myapplication.SsConfig;

/**
 * Created by xuchao on 15-7-9.
 */
public class ImageLoader {

    public static void load(Context context, Photo photo, ImageView imageView) {
        Glide.with(context).using(new PhotoUrlLoader(context))
                .load(photo)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .fitCenter().placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }

    public static void loadFromDrawable(Context context, String name, ImageView imageView) {
        Uri mUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + SsConfig.PACKAGE_NAME + "drawable/" + name);
        Glide.with(context).loadFromMediaStore(mUri)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .fitCenter()
                .error(R.mipmap.ic_launcher)
                .into(imageView);
    }

    public static void loadFromLocal(Context context, String path, ImageView imageView) {
        Uri mUri = Uri.parse("file://" + path);
        Glide.with(context).loadFromMediaStore(mUri)
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .fitCenter()
                .into(imageView);
    }

//    Resources s = getResources();
//    Uri uri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
//                    s.getResourcePackageName(R.drawable.album_camera_normal) + "/" +
//                    s.getResourceTypeName(R.drawable.album_camera_normal) + "/"
//                    + s.getResourceEntryName(R.drawable.album_camera_normal
//            )
//    );
}
