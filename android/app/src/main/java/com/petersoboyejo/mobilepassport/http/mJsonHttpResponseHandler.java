package com.petersoboyejo.mobilepassport.http;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.petersoboyejo.mobilepassport.R;
import com.petersoboyejo.mobilepassport.activities.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class mJsonHttpResponseHandler extends JsonHttpResponseHandler {

    private Context context;
    private View view;

    public mJsonHttpResponseHandler(Context context) {
        this.context = context;
    }
    public mJsonHttpResponseHandler(Context context, View v) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);
        try {
            if (response.getInt(context.getString(R.string.server_response)) == 2) {
                context.startActivity(new Intent(context, LoginActivity.class));
                Toast.makeText(context, R.string.e_session_expired, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        AsyncClient.doOnFailure(context, statusCode);
        if (this.view != null) view.setEnabled(true);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        AsyncClient.doOnFailure(context, statusCode);
        if (this.view != null) view.setEnabled(true);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        AsyncClient.doOnFailure(context, statusCode);
        if (this.view != null) view.setEnabled(true);
    }
}