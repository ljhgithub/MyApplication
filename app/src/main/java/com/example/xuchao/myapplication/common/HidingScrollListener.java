package com.example.xuchao.myapplication.common;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.xuchao.myapplication.model.ImageFloder;
import com.example.xuchao.myapplication.util.LogUtils;

/**
 * Created by xuchao on 15-7-10.
 */
public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {

    private static final int SHOW_DISTANCE = 120;
    private static final int HIDE_DISTANCE = 1000;

    private int mScrolledDistance = 0;
    private boolean mControlsVisible = true;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
      int firstVisibleItem=((LinearLayoutManager)  recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

        if (mControlsVisible||(!mControlsVisible&&dy<0)){
            mScrolledDistance+=dy;
            if (mScrolledDistance<=0){
                mScrolledDistance=-1;
            }
        }

        if (firstVisibleItem==0){
            if (!mControlsVisible){
                onShow();
                mControlsVisible=true;
            }

        }else {
            if (mScrolledDistance>HIDE_DISTANCE&&mControlsVisible){
                onHide();
                mControlsVisible=false;
                mScrolledDistance=HIDE_DISTANCE;
            }else if (!mControlsVisible&&(HIDE_DISTANCE-mScrolledDistance)>SHOW_DISTANCE){
                onShow();
                mControlsVisible=true;


            }
        }

    }

    public abstract void onHide();
    public abstract void onShow();
}
