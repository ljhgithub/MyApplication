package com.example.xuchao.myapplication.util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by xuchao on 15-7-3.
 */
public class SingleRequestQueue {
    private static SingleRequestQueue mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;
    private RequestQueue requestQueue;

    private SingleRequestQueue(Context ctx) {
        mCtx = ctx;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized SingleRequestQueue getInstance(Context context) {
        if (null == mInstance) {
            mInstance = new SingleRequestQueue(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {

        if (null == requestQueue) {
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
