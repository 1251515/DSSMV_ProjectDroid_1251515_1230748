package com.example.soundcheck.model;
import java.util.List;

public class Album {

    //MusicBrainz
    private final String albumID;
    private final String albumName;
    private final String artistID;
    private final String artistName;
    private final List<String> albumGenre;
    private final String albumRelease;
    private final String albumType;
    private final String albumLength;
    private final String albumDuration;

    //TheAudioDB
    private String albumDescription;
    private String coverURL;



    public Album(String albumID,
                 String albumName,
                 String artistID,
                 String artistName,
                 List<String> albumGenre,
                 String albumReleaseDate,
                 String albumType,
                 String albumLength,
                 String albumDuration) {

        this.albumID = albumID;
        this.albumName = albumName;
        this.artistID = artistID;
        this.artistName = artistName;
        this.albumLength = albumLength;
        this.albumDuration = albumDuration;
        this.albumRelease = albumReleaseDate;
        this.albumGenre = albumGenre;
        this.albumType = albumType;
    }


    public String getAlbumID() {
        return albumID;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getArtistID() {
        return artistID;
    }

    //getters

    public String getAlbumLength() {
        return albumLength;
    }

    public String getAlbumDuration() {
        return albumDuration;
    }

    public String getAlbumRelease() {
        return albumRelease;
    }

    public String getAlbumDescription() {
        return albumDescription;
    }

    public String getCoverURL() {
        return coverURL;
    }

    public List<String> getAlbumGenre() {
        return albumGenre;
    }

    public String getAlbumType() {
        return albumType;
    }

    public String getArtistName() {
        return artistName;
    }

    //setters

    public void setAlbumDescription(String albumDescription) {
        this.albumDescription = albumDescription;
    }

    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }

}
