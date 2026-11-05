package com.example.soundcheck.repository;

import com.example.soundcheck.api.MusicBrainzAPI;
import com.example.soundcheck.api.TheAudioDBAPI;
import com.example.soundcheck.model.Album;
import com.example.soundcheck.model.Artist;
import com.example.soundcheck.model.Track;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class MusicRepository {

    private final MusicBrainzAPI musicBrainz;
    private final TheAudioDBAPI audioDB;

    public MusicRepository () {
        musicBrainz = new MusicBrainzAPI();
        audioDB = new TheAudioDBAPI();
    }

    public List<Track> searchTracks (String query) throws IOException {
        return musicBrainz.trackSearch(query);
    }

    public List<Artist> searchArtists (String query) throws IOException {
        return musicBrainz.artistSearch(query);
    }

    public void getArtistDetails (Artist artist) throws IOException {
        audioDB.obtainArtistMetaData(artist);
        audioDB.obtainTopTracks(artist);
    }

    public List<Album> searchAlbum (String query) throws IOException {
        return musicBrainz.albumSearch(query);
    }

    public void getAlbumDetails (Album album) throws IOException {
        audioDB.obtainAlbumMetaData(album);
    }

}
