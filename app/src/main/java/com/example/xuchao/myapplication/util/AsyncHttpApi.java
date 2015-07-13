package com.example.xuchao.myapplication.util;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.RequestParams;

import org.apache.http.conn.ssl.SSLSocketFactory;

import java.security.KeyStore;

/**
 * Created by xuchao on 15-7-3.
 */
public class AsyncHttpApi {
    private  static final String BASE_URL="https://api2.shou65.com";
    private static AsyncHttpClient client;
    static {
        client=new AsyncHttpClient();
        certificate(client);


    }
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);

    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);


    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    private static void certificate(AsyncHttpClient client){
        KeyStore trustStore = null;
        try {
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            MySSLSocketFactory sf = new MySSLSocketFactory(trustStore);
            //允许所有主机的验证
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client.setSSLSocketFactory(sf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
