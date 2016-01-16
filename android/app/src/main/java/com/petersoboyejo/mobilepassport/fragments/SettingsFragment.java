package com.petersoboyejo.mobilepassport.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.petersoboyejo.mobilepassport.R;
import com.petersoboyejo.mobilepassport.http.AsyncClient;
import com.petersoboyejo.mobilepassport.http.mJsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SettingsFragment extends Fragment {

    Button delAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        delAccount = (Button) rootView.findViewById(R.id.delAccount);
        delAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Are you sure you want to delete your account?");
                builder.setMessage("fasdfasd")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {   

                                String del = "/user/" + ;

                                AsyncClient.delete(del, null, getContext(), new mJsonHttpResponseHandler(getContext()) {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                        super.onSuccess(statusCode, headers, response);
                                        try {
                                            if (response.getInt(getContext().getString(R.string.server_response)) == 1) {
                                                Toast.makeText(getContext(), "Account Removed", Toast.LENGTH_SHORT).show();
                                                AsyncClient.redirectToLogin(getContext());
                                            } else AsyncClient.redirectToLogin(getContext());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();
            }
        });

        // TODO: Edit Profile Stuff...

        return rootView;
    }
}