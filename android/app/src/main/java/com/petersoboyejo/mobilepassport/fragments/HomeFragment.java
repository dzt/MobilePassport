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
import android.widget.TextView;
import android.widget.Toast;

import com.petersoboyejo.mobilepassport.R;
import com.petersoboyejo.mobilepassport.activities.SearchActivity;
import com.petersoboyejo.mobilepassport.http.AsyncClient;
import com.petersoboyejo.mobilepassport.http.mJsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class HomeFragment extends Fragment {

    String name;
    String email;
    String usernmae;
    String name_search;
    String email_search;
    String usernmae_search;

    EditText userSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        LayoutInflater dViewinflater = getActivity().getLayoutInflater();
        final View dView = dViewinflater.inflate(R.layout.dialoglayout, null);



        FloatingActionButton orderButton = (FloatingActionButton) rootView.findViewById(R.id.search);
        orderButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater inflater = LayoutInflater.from(getContext());
                final View dialogview = inflater.inflate(R.layout.dialoglayout, null);
                builder.setView(dialogview);
                builder.setCancelable(false)
                        .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                userSearch = (EditText) dialogview.findViewById(R.id.userSearch);
                                String userInput = userSearch.getText().toString();
                                String toSearch = "/user/search/username/" + userInput;

                                AsyncClient.get(toSearch, null, new mJsonHttpResponseHandler(getContext()) {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                        try {
                                            if (response != null) {

                                                name_search = response.getString("name");
                                                email_search = response.getString("email");
                                                usernmae_search = "@" + response.getString("username");
                                                Intent i = new Intent(getContext(), SearchActivity.class);
                                                i.putExtra("name_search", name_search);
                                                i.putExtra("email_search", email_search);
                                                i.putExtra("usernmae_search", usernmae_search);
                                                startActivity(i);
                                            } else {
                                                Toast.makeText(getContext(), "An Error has occured", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                        super.onFailure(statusCode, headers, responseString, throwable);

                                        Toast.makeText(getContext(), "Could not find user", Toast.LENGTH_SHORT).show();

                                    }
                                });

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
        final TextView usernameTV = (TextView) rootView.findViewById(R.id.user_search);


        AsyncClient.get("/user/profile", null, new mJsonHttpResponseHandler(getContext()) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (response != null) {
                    try {

                        name = response.getString("name");
                        email = response.getString("email");
                        usernmae = "@" + response.getString("username");

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