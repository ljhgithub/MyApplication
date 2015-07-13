package com.example.xuchao.myapplication;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;

import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;

/**
 * Created by xuchao on 15-6-17.
 */
public class SsApp extends Application {

    @Override
    public void onCreate() {
        if (SsConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
        }


        super.onCreate();
        initImageLoader(getApplicationContext());


    }


    private void initImageLoader(Context context) {
        // 磁盘缓存
        FileNameGenerator fileNameGenerator = new Md5FileNameGenerator();
        DiskCache diskCache;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File firstDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + SsConfig.IMAGE_CACHE_DIR);
            File secondDir = new File(getCacheDir().getAbsolutePath() + SsConfig.IMAGE_CACHE_DIR);

            UnlimitedDiskCache unlimitedDiskCache = new UnlimitedDiskCache(firstDir, secondDir, fileNameGenerator);
            unlimitedDiskCache.setCompressFormat(SsConfig.IMAGE_CACHE_FORMAT);
            unlimitedDiskCache.setCompressQuality(SsConfig.IMAGE_CACHE_QUALITY);
            diskCache = unlimitedDiskCache;
        } else {
            File firstDir = new File(getCacheDir().getAbsolutePath() + SsConfig.IMAGE_CACHE_DIR);
            UnlimitedDiskCache unlimitedDiskCache = new UnlimitedDiskCache(firstDir, null, fileNameGenerator);
            unlimitedDiskCache.setCompressFormat(SsConfig.IMAGE_CACHE_FORMAT);
            unlimitedDiskCache.setCompressQuality(SsConfig.IMAGE_CACHE_QUALITY);
            diskCache = unlimitedDiskCache;
        }
        // 内存缓存
        long availableMemory = Runtime.getRuntime().maxMemory();
        int memoryCacheSize = (int) (availableMemory * (15 / 100f));
        MemoryCache memoryCache = new LruMemoryCache(memoryCacheSize);
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPoolSize(4);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.tasksProcessingOrder(QueueProcessingType.FIFO);
        config.memoryCache(memoryCache);
        config.diskCache(diskCache);
        if (SsConfig.DEBUG) {
            config.writeDebugLogs();
        }
        ImageLoader.getInstance().init(config.build());
    }
}
