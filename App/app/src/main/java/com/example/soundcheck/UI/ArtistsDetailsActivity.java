package com.example.soundcheck.UI;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.soundcheck.R;

public class ArtistsDetailsActivity extends AppCompatActivity {

    private String artistID;
    private String artistName;

    public void initializeViews() {




    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_artist_details);

        Intent intent = getIntent();

        artistID = intent.getStringExtra("artistID");
        artistName = intent.getStringExtra("artistName");

        initializeViews();

    }
}
