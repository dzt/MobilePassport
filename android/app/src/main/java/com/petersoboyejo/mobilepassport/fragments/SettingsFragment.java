package com.petersoboyejo.mobilepassport.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.petersoboyejo.mobilepassport.BaseActivity;
import com.petersoboyejo.mobilepassport.R;
import com.petersoboyejo.mobilepassport.activities.LoginActivity;
import com.petersoboyejo.mobilepassport.http.AsyncClient;
import com.petersoboyejo.mobilepassport.http.mJsonHttpResponseHandler;
import com.petersoboyejo.mobilepassport.utils.SharedPreferencesHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SettingsFragment extends Fragment {

    Button delAccount;
    Button editProfile;
    String name;
    String email;
    String usernmae;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        final EditText nameET = (EditText) rootView.findViewById(R.id.editText2);
        final EditText usernameET = (EditText) rootView.findViewById(R.id.userSearch);
        final EditText emailET = (EditText) rootView.findViewById(R.id.editText3);
        final EditText passwordET = (EditText) rootView.findViewById(R.id.editText5);
        final String pswdString = passwordET.getText().toString();



        AsyncClient.get("/user/profile", null, new mJsonHttpResponseHandler(getContext()) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (response != null) {
                    try {

                        name = response.getString("name");
                        email = response.getString("email");
                        usernmae = response.getString("username");

                        nameET.setText(name);
                        usernameET.setText(usernmae);
                        emailET.setText(email);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                    Toast.makeText(getContext(), "Toast: Sorry, couldn't Get your Information try again.", Toast.LENGTH_SHORT).show();

                }
            }
        });




        editProfile = (Button) rootView.findViewById(R.id.button);
        editProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                RequestParams updateParams = new RequestParams();
                updateParams.put("name", nameET.getText().toString());
                updateParams.put("username", usernameET.getText().toString());
                updateParams.put("email", emailET.getText().toString());
                if (pswdString.length() != 0) {

                    updateParams.put("password", emailET.getText().toString());

                }

                AsyncClient.post("/user/update", updateParams, new mJsonHttpResponseHandler(getContext()) {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                        if (response != null) {

                            Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();

                        } else  {
                            Toast.makeText(getContext(), "An Error has occured while trying to make changes to your profile", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }

        });

        


        delAccount = (Button) rootView.findViewById(R.id.delAccount);
        delAccount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Are you sure you want to delete your account?");
                builder.setMessage("You cannot undo this action.")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                AsyncClient.delete("/user/delete", null, getContext(), new mJsonHttpResponseHandler(getContext()) {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, String response) {
                                        super.onSuccess(statusCode, headers, response);
                                        if (response == "Deleted") {
                                            Toast.makeText(getContext(), "Your account has been successfully deleted.", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getContext(), LoginActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(getContext(), "An Error has occurred when trying to delete your account.", Toast.LENGTH_SHORT).show();
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