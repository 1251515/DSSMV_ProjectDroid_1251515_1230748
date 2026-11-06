package com.example.soundcheck;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.soundcheck.UI.adapter.artistAdapter;
import com.example.soundcheck.databinding.ActivityMainBinding;
import com.example.soundcheck.model.Artist;
import com.example.soundcheck.repository.MusicRepository;
import com.example.soundcheck.UI.ArtistsDetailsActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private RecyclerView recyclerArtists;
    private artistAdapter artistAdapter;

    private MusicRepository musicRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        musicRepository = new MusicRepository();

        recyclerArtists = findViewById(R.id.recyclerArtists);

        recyclerArtists.setLayoutManager(
                new LinearLayoutManager(this)
        );


        artistAdapter = new artistAdapter(new ArrayList<>(), artist -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    ArtistsDetailsActivity.class
            );

            intent.putExtra("artistID", artist.getId());
            intent.putExtra("artistName", artist.getName());

            startActivity(intent);

        });
        recyclerArtists.setAdapter(artistAdapter);

        binding.btnSearch.setOnClickListener(view -> {      //leitura botão
            String query = binding.edtSearch.getText().toString().trim();

            if (query.isEmpty()) {
                binding.edtSearch.setError("Introduce an artist name");
                return;
            }

            new Thread(() -> {

                try {
                    List<Artist> results = musicRepository.searchArtists(query);

                    runOnUiThread(() -> {
                        artistAdapter.updateArtists(results);
                    });

                } catch (IOException e) {

                    runOnUiThread(() -> {
                        Toast.makeText(
                                this,
                                "Error searching for artists",
                                Toast.LENGTH_SHORT
                        ).show();
                    });
                }
            }).start();
        });
    }
}