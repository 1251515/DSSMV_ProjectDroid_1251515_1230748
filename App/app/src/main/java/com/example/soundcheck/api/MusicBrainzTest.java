package com.example.soundcheck.api;

import android.util.Log;
import com.example.soundcheck.model.Album;
import com.example.soundcheck.model.Artist;
import com.example.soundcheck.model.Track;

import java.util.List;

public class MusicBrainzTest {

    public static void runTests() {

        new Thread(() -> {

            try {

                MusicBrainzAPI api = new MusicBrainzAPI();

                // TESTE ARTIST SEARCH
                Log.d("MusicBrainzTest", "TESTE ARTIST SEARCH");

                List<Artist> artists =
                        api.artistSearch("Daft Punk");

                for (Artist artist : artists) {
                    Log.d("MusicBrainzTest",
                            artist.getName() + " | " + artist.getId());
                }


                // TESTE ALBUM SEARCH
                Log.d("MusicBrainzTest", "TESTE ALBUM SEARCH");

                List<Album> albums =
                        api.albumSearch("Discovery");

                for (Album album : albums) {
                    Log.d("MusicBrainzTest",
                            album.getAlbumName() + " | " + album.getAlbumID());
                }


                // TESTE TRACK SEARCH
                Log.d("MusicBrainzTest", "TESTE TRACK SEARCH");

                List<Track> tracks =
                        api.trackSearch("One More Time");

                Log.d("MusicBrainzTest",
                        "Número de músicas encontradas: " + tracks.size());

                if (tracks.isEmpty()) {
                    Log.e("MusicBrainzTest",
                            "A pesquisa não devolveu nenhuma música.");
                }

                for (Track track : tracks) {
                    Log.d("MusicBrainzTest", "Nome: " + track.getTrackName());
                    Log.d("MusicBrainzTest", "ID: " + track.getTrackID());
                    Log.d("MusicBrainzTest", "Álbum: " + track.getAlbumName());
                    Log.d("MusicBrainzTest", "Duração: " + track.getTrackDuration());
                }

                Log.d("MusicBrainzTest", "TESTES TERMINADOS");

            } catch (Exception e) {

                Log.e("MusicBrainzTest",
                        "Erro durante os testes", e);

            }

        }).start();
    }
}