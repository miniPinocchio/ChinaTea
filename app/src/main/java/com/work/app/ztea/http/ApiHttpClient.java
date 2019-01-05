package com.work.app.ztea.http;

import android.content.Context;
import android.util.Log;

import com.dream.library.utils.AbLog;
import com.dream.library.utils.AbMd5;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Author:      qiyunfeng
 * Date:        16/4/27 下午6:44
 * Description: ApplianceServer
 */
@SuppressWarnings("unused")
public class ApiHttpClient {
    private static AsyncHttpClient client;

    static {
        //client = new AsyncHttpClient();//请求https的方式
        client = new AsyncHttpClient(true, 80, 443);//请求https的方式
        //client.addHeader("Accept-Language", Locale.getDefault().toString());
        //client.addHeader("Connection", "Keep-Alive");
        //client.addHeader("Content-Type", "text/xml; charset=utf-8");
        client.setTimeout(20 * 1000);
        //        client.setMaxRetriesAndTimeout(0, 1000);
    }

    public static void setUserAgent(String userAgent) {
        client.setUserAgent(userAgent);
    }

    public static void setCookie(String cookie) {
        client.addHeader("Cookie", cookie);
    }

    private ApiHttpClient() {
    }

    public static AsyncHttpClient getHttpClient() {
        return client;
    }

    public static void get(String partUrl, AsyncHttpResponseHandler handler) {
        client.get(getAbsoluteApiUrl(partUrl), handler);
        log("GET " + getAbsoluteApiUrl(partUrl));
    }

    public static void get(final String partUrl, RequestParams params, final AsyncHttpResponseHandler handler) {
        client.get(getAbsoluteApiUrl(partUrl), params, handler);
        //        client.get(getAbsoluteApiUrl(partUrl), params, new TextHttpResponseHandler() {
        //            @Override
        //            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        //                AbLog.il("onFailure：" + partUrl, responseString);
        //                handler.onFailure(statusCode, headers, responseString.getBytes(), throwable);
        //            }
        //
        //            @Override
        //            public void onSuccess(int statusCode, Header[] headers, String responseString) {
        //                AbLog.il("onSuccess:" + partUrl, responseString);
        //                handler.onSuccess(statusCode, headers, responseString.getBytes());
        //            }
        //        });


        log("GET " + getAbsoluteApiUrl(partUrl) + "?" + params);
    }

    public static void getDirect(String url, AsyncHttpResponseHandler handler) {
        client.get(url, handler);
        log("GET " + url);
    }

    public static void getDirect(String url, RequestParams params, AsyncHttpResponseHandler handler) {
        client.get(url, params, handler);
        log("GET " + url + "&" + params);
    }

    public static void post(String partUrl, AsyncHttpResponseHandler handler) {
        client.post(getAbsoluteApiUrl(partUrl), handler);
        log("POST " + getAbsoluteApiUrl(partUrl));
    }

    public static void post(String partUrl, MyRequestParams params, AsyncHttpResponseHandler handler) {
        params.put("signature", (AbMd5.MD5(params.toString() + "|" + Api.PRIVATE_KEY)).toUpperCase());
        client.post(getAbsoluteApiUrl(partUrl), params, handler);
//        log("POST " + getAbsoluteApiUrl(partUrl) + "?" + params);
        Log.d("params","request = "+"POST " + getAbsoluteApiUrl(partUrl) + "?" + params);
    }

    public static void postDirect(String url, RequestParams params, AsyncHttpResponseHandler handler) {
        client.post(url, params, handler);
        log("POST " + url + "?" + params);
    }

    public static void put(String partUrl, AsyncHttpResponseHandler handler) {
        client.put(getAbsoluteApiUrl(partUrl), handler);
        log("PUT " + getAbsoluteApiUrl(partUrl));
    }

    public static void put(String partUrl, RequestParams params, AsyncHttpResponseHandler handler) {
        client.put(getAbsoluteApiUrl(partUrl), params, handler);
        log("PUT " + getAbsoluteApiUrl(partUrl) + "&" + params);
    }

    public static void delete(String partUrl, AsyncHttpResponseHandler handler) {
        client.delete(getAbsoluteApiUrl(partUrl), handler);
        log("DELETE " + getAbsoluteApiUrl(partUrl));
    }

    public static void cancelAll(Context context) {
        client.cancelRequests(context, true);
    }

    public static String getAbsoluteApiUrl(String partUrl) {
        return Api.BASE_URL + partUrl;
    }

    private static void log(String log) {
        AbLog.d("请求：" + log);
    }

}
