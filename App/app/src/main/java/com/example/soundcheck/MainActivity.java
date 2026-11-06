package com.example.soundcheck;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.soundcheck.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSearch.setOnClickListener(view -> {      //leitura botão

            String query = binding.edtSearch.getText().toString().trim();

            if (query.isEmpty()) {
                binding.edtSearch.setError("Introduce an artist name");
                return;
            }
        });
    }
}