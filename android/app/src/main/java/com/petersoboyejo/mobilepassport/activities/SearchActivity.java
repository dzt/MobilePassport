package com.petersoboyejo.mobilepassport.activities;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.petersoboyejo.mobilepassport.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.tkeunebr.gravatar.Gravatar;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_18dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final TextView nameTV = (TextView) findViewById(R.id.name_search);
        final TextView emailTV = (TextView) findViewById(R.id.email_search);
        final TextView usernameTV = (TextView) findViewById(R.id.user_search);

        Intent intent = getIntent();
        String name_search = intent.getExtras().getString("name_search");
        String email_search = intent.getExtras().getString("email_search");
        String usernmae_search = intent.getExtras().getString("usernmae_search");

        setTitle(usernmae_search);

        CircleImageView mCircleImageView = (CircleImageView) findViewById(R.id.profile_image_search);
        String gravatarUrl = Gravatar.init().with(email_search).build();

        Picasso.with(this)
                .load(gravatarUrl)
                .resize(500, 500)
                .centerCrop()
                .into(mCircleImageView);

        nameTV.setText(name_search);
        emailTV.setText(email_search);
        usernameTV.setText(usernmae_search);

    }
}
