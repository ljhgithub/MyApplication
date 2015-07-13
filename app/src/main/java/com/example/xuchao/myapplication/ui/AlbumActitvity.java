package com.example.xuchao.myapplication.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.xuchao.myapplication.R;
import com.example.xuchao.myapplication.model.ImageFloder;
import com.example.xuchao.myapplication.model.ImageModel;
import com.example.xuchao.myapplication.util.LogUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashSet;


/**
 * Created by xuchao on 15-6-15.
 */
public class AlbumActitvity extends FragmentActivity implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener {
    private int imagesMax = 0;
    private int totalImages = 0;
    private File maxFolder = null;
    private HashSet<String> mDirPaths = new HashSet<>();
    private ArrayList<ImageFloder> mImageFloders = new ArrayList<>();
    private ArrayList<ImageModel> mImages = new ArrayList<>();
    private AlbumAdapter adapter;
    private static final String TAG = LogUtils.makeLogTag(AlbumActitvity.class);

    // 载入器获取的属性
    private static final String[] CONTENT_IMAGES_PROJECTION = {
            MediaStore.Images.Media._ID,        // ID
            MediaStore.Images.Media.DATA,       // 路径
            MediaStore.Images.Media.SIZE,       // 大小
            MediaStore.Images.Media.DATE_ADDED, // 增加时间
    };

    // 载入器选取规则
    private static final String CONTENT_IMAGES_SELECTION = MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE
            + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?";

    // 载入器选取规则参数
    private static final String[] CONTENT_IMAGES_SELECTION_ARGS = {"image/jpg", "image/jpeg", "image/png"};

    // 载入器排序规则
    private static final String CONTENT_IMAGES_SORT_ORDER = MediaStore.Images.Media.DATE_ADDED + " DESC";

    private GridView gvAblum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ablum);
        getSupportLoaderManager().initLoader(0, null, this);
        gvAblum = (GridView) findViewById(R.id.gv_images);
        adapter = new AlbumAdapter();
        gvAblum.setAdapter(adapter);
        gvAblum.setOnItemClickListener(this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, CONTENT_IMAGES_PROJECTION,
                CONTENT_IMAGES_SELECTION, CONTENT_IMAGES_SELECTION_ARGS, CONTENT_IMAGES_SORT_ORDER);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        int cIdIndex = data.getColumnIndex(MediaStore.Images.Media._ID);
        int cDataIndex = data.getColumnIndex(MediaStore.Images.Media.DATA);
        int cSizeIndex = data.getColumnIndex(MediaStore.Images.Media.SIZE);


        String firstImage = null;
        File parentFile;
        String dirPath;
        ImageFloder imageFloder;
        int imagesSize;
        while (data.moveToNext()) {
            long id = data.getLong(cIdIndex);
            String path = data.getString(cDataIndex);
            long size = data.getLong(cSizeIndex);


            if (firstImage == null) {
                firstImage = path;
            }
            parentFile = new File(path).getParentFile();
            if (parentFile == null) {
                continue;
            }

            dirPath = parentFile.getAbsolutePath();
            if (mDirPaths.contains(dirPath)) {
                continue;
            } else {
                mDirPaths.add(dirPath);
                imageFloder = new ImageFloder();
                imageFloder.setDir(dirPath);
                imageFloder.setFirstImagePath(path);
                imageFloder.setName(parentFile.getName());
            }

            if (parentFile.list() == null) {
                continue;
            }
            String[] imagePaths = parentFile.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    if (filename.endsWith(".jpg") || filename.endsWith(".png") || filename.endsWith(".jpeg")) {
                        return true;
                    }
                    return false;
                }
            });


            ImageModel imageModel;
            imagesSize = 0;
            if (null != imagePaths) {
                ArrayList<ImageModel> images = new ArrayList<>(imagePaths.length);
                imagesSize = imagePaths.length;
                for (int i = 0; i < imagesSize; i++) {
                    imageModel = new ImageModel();
                    imageModel.preview = dirPath + "/" + imagePaths[i];
                    images.add(imageModel);
                }
                mImages.addAll(images);
                imageFloder.setCount(imagesSize);
                imageFloder.setImages(images);
            }


            mImageFloders.add(imageFloder);
            if (imagesSize > imagesMax) {
                imagesMax = imagesSize;
                maxFolder = parentFile;
            }
            totalImages += imagesSize;
        }


        adapter.addData(mImages);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0) {
            Intent intent = new Intent(this, GalleryActivity.class);
            intent.putParcelableArrayListExtra(GalleryActivity.EXTRA_IMAGE, mImages);
            intent.putExtra(GalleryActivity.EXTRA_IMAGE_FROM, GalleryActivity.IMAGE_FROM_LOCAL);
            startActivity(intent);



            return;
        }
        adapter.selItems.set(position, !adapter.selItems.get(position));
        adapter.notifyDataSetChanged();
    }
}
