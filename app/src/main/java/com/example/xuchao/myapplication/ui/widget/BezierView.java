package com.example.xuchao.myapplication.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xuchao on 15-7-1.
 */
public class BezierView extends View {


    public BezierView(Context context) {
        super(context);
    }

    public BezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setColor(Color.RED);// 设置红色

        canvas.drawText("画贝塞尔曲线:", 10, 310, p);
        p.reset();
        p.setStyle(Paint.Style.STROKE);
        p.setColor(Color.GREEN);
        Path path2=new Path();
        path2.moveTo(100, 320);//设置Path的起点
        path2.quadTo(200, 370, 100, 420); //设置贝塞尔曲线的控制点坐标和终点坐标
        canvas.drawPath(path2, p);//画出贝塞尔曲线
        path2.moveTo(100, 320);//设置Path的起点
        path2.quadTo(0, 370, 100,420); //设置贝塞尔曲线的控制点坐标和终点坐标
        canvas.drawPath(path2, p);//画出贝塞尔曲线
    }
}
