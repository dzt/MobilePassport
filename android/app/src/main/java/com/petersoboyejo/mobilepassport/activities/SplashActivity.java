package com.petersoboyejo.mobilepassport.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.petersoboyejo.mobilepassport.BaseActivity;
import com.petersoboyejo.mobilepassport.R;
import com.petersoboyejo.mobilepassport.http.AsyncClient;
import com.petersoboyejo.mobilepassport.http.mJsonHttpResponseHandler;
import com.petersoboyejo.mobilepassport.utils.SharedPreferencesHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        context = this;

        final String userParam = SharedPreferencesHandler.getString(this, "username");
        final String passParam = SharedPreferencesHandler.getString(this, "password");

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                if (SharedPreferencesHandler.getBoolean(context, "rememberMe") == true) {

                    RequestParams params = new RequestParams();
                    params.put("username", userParam);
                    params.put("password", passParam);

                    AsyncClient.post("/login", params, new mJsonHttpResponseHandler(context) {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                if (response.getInt(context.getString(R.string.server_response)) == 1) {
                                    Toast.makeText(context, response.getString(context.getString(R.string.server_message)), Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(context, BaseActivity.class);
                                    startActivity(i);
                                    finish();
                                } else if (response.getInt(context.getString(R.string.server_response)) == 0) {
                                    Toast.makeText(context, response.getString(context.getString(R.string.server_message)), Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                                    startActivity(i);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } else {

                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);

                }

                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
