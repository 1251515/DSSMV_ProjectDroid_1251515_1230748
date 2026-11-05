package com.example.soundcheck;

import android.os.Bundle;
import android.util.Log;
import com.example.soundcheck.api.MusicBrainzAPI;
import com.example.soundcheck.api.MusicBrainzTest;
import com.example.soundcheck.api.TheAudioDBAPI;
import com.example.soundcheck.api.TheAudioDBTest;
import com.example.soundcheck.model.Album;
import com.example.soundcheck.model.Artist;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.soundcheck.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.d("MainActivity", "MAIN ACTIVITY INICIADA");
        Log.d("MainActivity", "VOU INICIAR A THREAD");

        new Thread(() -> {

            Log.d("MainActivity", "THREAD INICIADA");

            MusicBrainzAPI musicBrainz = new MusicBrainzAPI();

        try {

            Log.d("MainActivity", "A pesquisar artista...");

            List<Artist> artists = musicBrainz.artistSearch("Daft Punk");

            Log.d("MainActivity", "Artistas encontrados: " + artists.size());

            Log.d("MainActivity", "A pesquisar album...");

            List<Album> albums = musicBrainz.albumSearch("Discovery");

            Log.d("MainActivity", "Álbuns encontrados: " + albums.size());

            Log.d("MainActivity", "Cheguei ao IF");
            if (!artists.isEmpty() && !albums.isEmpty()) {

                Artist artist = artists.get(0);
                Album album = albums.get(0);

                Log.d("MainActivity", "Vou iniciar os testes do TheAudioDB");

                TheAudioDBAPI audioDB = new TheAudioDBAPI();

                TheAudioDBTest.runTests(audioDB, artist, album);
            } else { Log.d("MainActivity", "Uma das pesquisas não devolveu resultados."); }

        } catch (Exception e) { Log.e("MainActivity", "Erro nos testes", e); }
}).start();



        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}