package com.petersoboyejo.mobilepassport.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button loginButton;
    Button registerButton;
    Context context;
    CheckBox cb_remember_me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Login");

        username = (EditText) findViewById(R.id.user_search);
        password = (EditText) findViewById(R.id.pswd);
        loginButton = (Button) findViewById(R.id.loginBtn);
        registerButton = (Button) findViewById(R.id.registerBtn);
        cb_remember_me = (CheckBox) findViewById(R.id.cb_remember);
        context = this.getApplicationContext();

        username.setText(SharedPreferencesHandler.getString(context, "username"));
        password.setText(SharedPreferencesHandler.getString(context, "password"));
    }

    public void login(final View v) {

        RequestParams params = new RequestParams();
        params.put("username", username.getText().toString());
        params.put("password", password.getText().toString());

        AsyncClient.post("/login", params, new mJsonHttpResponseHandler(this) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getInt(context.getString(R.string.server_response)) == 1) {

                        if (cb_remember_me.isChecked()) {

                            SharedPreferencesHandler.writeString(context, "username", username.getText().toString());
                            SharedPreferencesHandler.writeString(context, "password", password.getText().toString());
                            SharedPreferencesHandler.writeBoolean(context, "rememberMe", true);

                        }

                        Toast.makeText(context, response.getString(context.getString(R.string.server_message)), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(context, BaseActivity.class);
                        startActivity(i);
                        finish();
                    } else if (response.getInt(context.getString(R.string.server_response)) == 0) {
                        Toast.makeText(context, response.getString(context.getString(R.string.server_message)), Toast.LENGTH_SHORT).show();
                        v.setEnabled(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void register(View v) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

}
