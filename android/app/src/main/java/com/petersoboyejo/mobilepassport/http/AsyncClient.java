package com.petersoboyejo.mobilepassport.http;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.petersoboyejo.mobilepassport.R;
import com.petersoboyejo.mobilepassport.activities.LoginActivity;
import cz.msebera.android.httpclient.Header;

public class AsyncClient {
    private static final String BASE_URL = "https://f505e24d.ngrok.io/";
    PersistentCookieStore cookieStore;

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.setTimeout(10000);
        client.get(getAbsoluteUrl(url), params, responseHandler);
        Log.v("AsyncClient - GET", getAbsoluteUrl(url));
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
        Log.v("AsyncClient - POST", getAbsoluteUrl(url));
    }

    public static void put(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.put(getAbsoluteUrl(url), params, responseHandler);
        Log.v("AsyncClient - PUT", getAbsoluteUrl(url));
    }

    public static void delete(String url, Header[] headers, Context context, AsyncHttpResponseHandler responseHandler) {
        client.delete(context, getAbsoluteUrl(url), headers, responseHandler);
        Log.v("AsyncClient - DELETE", getAbsoluteUrl(url));
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static void doOnFailure(Context context, int errorCode) {
        switch (errorCode) {
            case 0:
                Toast.makeText(context, R.string.code_0, Toast.LENGTH_SHORT).show();
                break;
            case 404:
                Toast.makeText(context, R.string.code_404, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public static void redirectToLogin(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
        Toast.makeText(context, R.string.e_session_expired, Toast.LENGTH_SHORT).show();

    }
}