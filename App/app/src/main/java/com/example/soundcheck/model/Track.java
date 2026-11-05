package com.example.soundcheck.model;

public class Track {

    private final String trackID;
    private final String trackName;
    private final String albumID;
    private final String albumName;
    private final String artistID;
    private final String artistName;
    private String trackDuration;
    private String trackRelease;

    public Track (String trackID,
                  String trackName,
                  String albumID,
                  String albumName,
                  String artistID,
                  String artistName,
                  String trackDuration,
                  String trackRelease) {

        this.trackID = trackID;
        this.trackName = trackName;
        this.albumID = albumID;
        this.albumName = albumName;
        this.artistID = artistID;
        this.artistName = artistName;
        this.trackDuration = trackDuration;
        this.trackRelease = trackRelease;
    }

    public Track (String trackID,
                  String trackName,
                  String albumID,
                  String albumName,
                  String artistID,
                  String artistName) {

        this.trackID = trackID;
        this.trackName = trackName;
        this.albumID = albumID;
        this.albumName = albumName;
        this.artistID = artistID;
        this.artistName = artistName;
    }

    public String getTrackID() {
        return trackID;
    }
    public String getTrackName() {
        return trackName;
    }
    public String getAlbumID() {
        return albumID;
    }
    public String getArtistID() {
        return artistID;
    }

    public String getTrackDuration() {
        return trackDuration;
    }

    public String getTrackRelease(){
        return trackRelease;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getArtistName() {
        return artistName;
    }
}
