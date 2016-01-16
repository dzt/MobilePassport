package com.petersoboyejo.mobilepassport.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.petersoboyejo.mobilepassport.BaseActivity;
import com.petersoboyejo.mobilepassport.R;
import com.petersoboyejo.mobilepassport.http.AsyncClient;
import com.petersoboyejo.mobilepassport.http.mJsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button loginButton;
    Button registerButton;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.pswd);
        loginButton = (Button) findViewById(R.id.loginBtn);
        registerButton = (Button) findViewById(R.id.registerBtn);
        context = this.getApplicationContext();
    }

    public void login(final View v) {

        RequestParams params = new RequestParams();
        params.put("username", username.getText().toString());
        params.put("password", password.getText().toString());

        AsyncClient.post("/login", params, new mJsonHttpResponseHandler(this) {
            //@Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getInt(context.getString(R.string.server_response)) == 1) {
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
