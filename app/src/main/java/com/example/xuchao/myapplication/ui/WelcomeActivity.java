package com.example.xuchao.myapplication.ui;

import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.xuchao.myapplication.R;
import com.example.xuchao.myapplication.common.HidingScrollListener;
import com.example.xuchao.myapplication.common.Photo;
import com.example.xuchao.myapplication.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuchao on 15-7-2.
 */
public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = LogUtils.makeLogTag(WelcomeActivity.class);
    private static final int RESULT_LOAD_IMAGE = 1;
    //  String url = "https://api2.shou65.com/user/profile/show";
    private Toolbar mToolbar;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private WelcomeAdapter mAdapter;
    private LinearLayoutManager llm;
    private ImageView mFabButton;
    private int actionBarHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mRecyclerView = (RecyclerView) mSwipeRefreshLayout.findViewById(R.id.recycle_view);
        llm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(llm);
        mAdapter = new WelcomeAdapter(this);
        mFabButton = (ImageButton) findViewById(R.id.fabButton);
        initToolbar();

    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        mToolbar.setNavigationIcon(R.drawable.ic_up);
        setSupportActionBar(mToolbar);
        TypedArray actionbarSizeTypedArray = this.obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        actionBarHeight = (int) actionbarSizeTypedArray.getDimension(0, 0);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mSwipeRefreshLayout.setProgressViewOffset(true, actionBarHeight / 2, actionBarHeight * 2);
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.swipe_color_1, R.color.swipe_color_2,
                R.color.swipe_color_3, R.color.swipe_color_4);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                initiateRefresh();
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        initiateRefresh();
        mSwipeRefreshLayout.setRefreshing(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    initiateRefresh();
                }
            }
        });
        mRecyclerView.addOnScrollListener(new HidingScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                    if (!mAdapter.hasFooter && llm.findLastVisibleItemPosition() == mAdapter.photos.size() - 1) {
                        mAdapter.setMoreView();
                        new DummyBackgroundTask().execute();
                    }
                }
            }

            @Override
            public void onHide() {
                hideViews();
            }

            @Override
            public void onShow() {
                showViews();
            }
        });
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.btn_pic:
//                Intent i = new Intent(
//                        Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(i, RESULT_LOAD_IMAGE);
//                break;
//            case R.id.btn_camera:
//                break;
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView imageView = (ImageView) findViewById(R.id.iv_pic);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }

    }

    private void hideViews() {
        mToolbar.animate().translationY(-mToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFabButton.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        mFabButton.animate().translationY(mFabButton.getHeight() + fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    private void showViews() {
        mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        mFabButton.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    private class DummyBackgroundTask extends AsyncTask<Void, Void, List<Photo>> {

        static final int TASK_DURATION = 3 * 1000; // 3 seconds

        @Override
        protected List<Photo> doInBackground(Void... params) {
            List<Photo> photos = new ArrayList<>();
            List<String> data = new ArrayList<String>();
            data.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3042410010,1849246464&fm=116&gp=0.jpg");
            data.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1005212286,2432746147&fm=116&gp=0.jpg");
            data.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1070902365,2619384777&fm=116&gp=0.jpg");
            data.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3658159165,4286511134&fm=116&gp=0.jpg");
            data.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3371032114,892333757&fm=116&gp=0.jpg");
            data.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2651666712,3423349209&fm=116&gp=0.jpg");
            data.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=298400068,822827541&fm=116&gp=0.jpg");
            data.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3229727480,642457655&fm=116&gp=0.jpg");
            data.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=806135425,801140138&fm=116&gp=0.jpg");
            data.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3591818604,1991042111&fm=116&gp=0.jpg");
            Photo photo;
            for (String path : data) {
                photo = new Photo();
                photo.origin = path;
                photos.add(photo);
            }
            try {
                Thread.sleep(TASK_DURATION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return photos;
        }

        @Override
        protected void onPostExecute(List<Photo> result) {
            super.onPostExecute(result);
            onRefreshComplete(result);
        }


    }


    private void initiateRefresh() {
        mAdapter.isRefresh=true;
        new DummyBackgroundTask().execute();
    }

    private void onRefreshComplete(List<Photo> result) {
        mAdapter.addData(result);
        mSwipeRefreshLayout.setRefreshing(false);


    }


}
