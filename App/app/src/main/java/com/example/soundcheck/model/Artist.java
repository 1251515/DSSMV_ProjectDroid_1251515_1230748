package com.example.soundcheck.model;
import java.util.List;

public class Artist {

    //MusicBrainz
    private final String artistID;
    private final String artistName;
    private final String artistType;
    private final List<String> artistGenres;

    //TheAudioDB
    private List<Track> topTracks;
    private String artistDescription;
    private String artistPfpURL;



    public Artist (String id,
                   String name,
                   String artistType,
                   List<String> artistGenres) {

        this.artistID = id;
        this.artistName = name;
        this.artistType = artistType;
        this.artistGenres = artistGenres;

    }

    //getters
    public String getId() {
        return artistID;
    }

    public String getName() {
        return artistName;
    }

    public List<Track> getTopTracks() {
        return topTracks;
    }

    public String getArtistDescription() {
        return artistDescription;
    }

    public String getArtistPfpURL() {
        return artistPfpURL;
    }

    public List<String> getArtistGenres() {
        return artistGenres;
    }

    public String getArtistType() {
        return artistType;
    }

    //setters
    public void setTopTracks(List<Track> topTracks) {
        this.topTracks = topTracks;
    }

    public void setArtistDescription(String artistDescription) {
        this.artistDescription = artistDescription;
    }

    public void setArtistPfpURL(String PfpURL) {
        this.artistPfpURL = PfpURL;
    }
}