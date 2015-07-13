package com.example.xuchao.myapplication.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
/**
 * Created by xuchao on 15-7-2.
 */
public class SolarSystemLayout extends ViewGroup {
    float[] distance;
    float rate = 1;

    public SolarSystemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SolarSystemLayout(Context context) {
        super(context);
        init();
    }

    public SolarSystemLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        distance = new float[]{58, 108, 150, 228, 778, 1427, 2870, 4497, 5914};
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        rate = sizeHeight / distance[8];


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        58,108,150,228,778,1427,2870,4497,5914,000000
//        4878,12103.6,12756.3,6794,142984,120536,51118,49528;
        int count = getChildCount();

        int start = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            start += (int) (distance[i] * rate);
            child.layout(0, start, 50, start + 10);
        }


    }


}
