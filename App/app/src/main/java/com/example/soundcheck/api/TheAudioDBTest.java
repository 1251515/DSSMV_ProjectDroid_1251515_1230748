
package com.example.soundcheck.api;

import android.util.Log;

import java.util.List;

import com.example.soundcheck.model.Artist;
import com.example.soundcheck.model.Album;
import com.example.soundcheck.model.Track;
import com.example.soundcheck.api.TheAudioDBAPI;

public class TheAudioDBTest {

    private static final String TAG = "TheAudioDBTest";

    public static void runTests(TheAudioDBAPI api,
                                Artist artist,
                                Album album) {

        Log.d(TAG, "runTests foi chamado!");

        try {

            // TESTE 1 - ARTIST METADATA
            Log.d(TAG, "===== TESTE ARTIST METADATA =====");

            api.obtainArtistMetaData(artist);

            Log.d(TAG, "Artist ID: " + artist.getId());
            Log.d(TAG, "Description: " +
                    artist.getArtistDescription());
            Log.d(TAG, "Photo: " +
                    artist.getArtistPfpURL());


            // TESTE 2 - ALBUM METADATA
            Log.d(TAG, "===== TESTE ALBUM METADATA =====");

            api.obtainAlbumMetaData(album);

            Log.d(TAG, "Album ID: " + album.getAlbumID());
            Log.d(TAG, "Description: " +
                    album.getAlbumDescription());
            Log.d(TAG, "Cover: " +
                    album.getCoverURL());


            // TESTE 3 - TOP TRACKS
            Log.d(TAG, "===== TESTE TOP TRACKS =====");

            api.obtainTopTracks(artist);

            List<Track> topTracks = artist.getTopTracks();

            if (topTracks != null && !topTracks.isEmpty()) {

                for (Track track : topTracks) {

                    Log.d(TAG, "--------------------");

                    Log.d(TAG, "Track: " +
                            track.getTrackName());

                    Log.d(TAG, "Track ID: " +
                            track.getTrackID());

                    Log.d(TAG, "Album: " +
                            track.getAlbumName());

                    Log.d(TAG, "Album ID: " +
                            track.getAlbumID());

                    Log.d(TAG, "Artist: " +
                            track.getArtistName());

                    Log.d(TAG, "Artist ID: " +
                            track.getArtistID());
                }

            } else {
                Log.d(TAG, "Não foram encontradas Top Tracks.");
            }

            Log.d(TAG, "===== TESTES TERMINADOS =====");

        } catch (Exception e) {
            Log.e(TAG, "Erro durante os testes", e);
        }
    }
}
