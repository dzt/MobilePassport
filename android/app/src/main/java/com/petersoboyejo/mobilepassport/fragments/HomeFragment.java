package com.petersoboyejo.mobilepassport.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.petersoboyejo.mobilepassport.R;
import com.petersoboyejo.mobilepassport.activities.SearchActivity;
import com.petersoboyejo.mobilepassport.http.AsyncClient;
import com.petersoboyejo.mobilepassport.http.mJsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class HomeFragment extends Fragment {

    String name;
    String email;
    String usernmae;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        FloatingActionButton orderButton = (FloatingActionButton) rootView.findViewById(R.id.search);
        orderButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View dialogview = inflater.inflate(R.layout.dialoglayout, null);
                builder.setView(dialogview);
                builder.setTitle("Type in friends username")
                        .setCancelable(false)
                        .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();

                // Intent intent = new Intent(getContext(), SearchActivity.class);
                // startActivity(intent);

            }

        });

        final TextView nameTV = (TextView) rootView.findViewById(R.id.name);
        final TextView emailTV = (TextView) rootView.findViewById(R.id.email);
        final TextView usernameTV = (TextView) rootView.findViewById(R.id.user);


        AsyncClient.get("/user/profile", null, new mJsonHttpResponseHandler(getContext()) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (response != null) {
                    try {

                        name = response.getString("name");
                        email = response.getString("email");
                        usernmae = response.getString("username");

                        nameTV.setText(name);
                        emailTV.setText(email);
                        usernameTV.setText(usernmae);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                    Toast.makeText(getContext(), "Toast: Sorry, couldn't Get your Information try again.", Toast.LENGTH_SHORT).show();

                }
            }
        });

        return rootView;
    }

}