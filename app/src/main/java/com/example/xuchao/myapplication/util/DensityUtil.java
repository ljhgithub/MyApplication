package com.example.xuchao.myapplication.util;

public class DensityUtil {
    private DensityUtil() {
    }

    public static int dip2px(android.content.Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + .5f);
    }

    public static int px2dip(android.content.Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + .5f);
    }

    public static int sp2px(android.content.Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + .5f);

    }

    public static int px2sp(android.content.Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + .5f);
    }
}