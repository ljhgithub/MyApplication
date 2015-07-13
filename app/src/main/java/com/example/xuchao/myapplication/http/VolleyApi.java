package com.example.xuchao.myapplication.http;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.xuchao.myapplication.util.HttpsTrustManager;
import com.example.xuchao.myapplication.util.LogUtils;
import com.example.xuchao.myapplication.util.SingleRequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.ErrorListener;


/**
 * Created by xuchao on 15-7-2.
 */
public class VolleyApi {

    public static void apiGet(Context ctx, String url, Map<String, String> params, String tag) {
        RequestQueue queue = SingleRequestQueue.getInstance(ctx).getRequestQueue();
        HttpsTrustManager.allowAllSSL();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url + encodeParameters(params, "utf-8"), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtils.LOGD("tag", response.toString());

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtils.LOGD("tag", error.toString());
            }
        });
        jsonObjectRequest.setTag(tag);
        SingleRequestQueue.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
        queue.start();


    }

    public static void apiPost(Context ctx, String url, final Map<String, String> params, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, String tag) {
        RequestQueue queue = SingleRequestQueue.getInstance(ctx).getRequestQueue();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("token", "42uBtDBnfj3013j2uE17EawS-b5ZsSeA_RlfIHwiJ082Sma5FdE3gp6d2G8-A8I_14ClwZWqCxVDbudMkPYxOTBkMWVjM2RlOA");
            jsonObject.put("tags", "world");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HttpsTrustManager.allowAllSSL();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, listener, errorListener) {

//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                LogUtils.LOGD("tag", params.size() + "");
//                Map<String, String> params1 = new HashMap<>();
//                params1.put("token", "42uBtDBnfj3013j2uE17EawS-b5ZsSeA_RlfIHwiJ082Sma5FdE3gp6d2G8-A8I_14ClwZWqCxVDbudMkPYxOTBkMWVjM2RlOA");
//                params1.put("tags", "world");
//                return params1;
//            }

//            @Override
//            public Map<String, String> getHeaders() {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Accept", "application/json");
//                headers.put("Content-Type", "application/json; charset=UTF-8");
//
//                return headers;
//            }
        };



        jsonObjectRequest.setTag(tag);
        SingleRequestQueue.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
        queue.start();

    }

    private static String encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();

        try {
            encodedParams.append("?");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            return encodedParams.toString();
        } catch (UnsupportedEncodingException uee) {
//            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
            return "";
        }
    }
}
