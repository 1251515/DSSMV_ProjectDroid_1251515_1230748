package com.example.soundcheck.model;
import java.util.List;

public class User {
    private final String userID;
    private final String username;
    private final String email;
    private final String userPfpURL;
    private final String userBio;
    private final List<String> favouriteArtists;
    private final List<String> favouriteAlbums;
    private final String countryOrigin;

    public User(String userID,
                String username,
                String email,
                String userPfpURL,
                String userBio,
                List<String> favouriteArtists,
                List<String> favouriteAlbums,
                String countryOrigin) {

        this.userID = userID;
        this.username = username;
        this.email = email;
        this.userPfpURL = userPfpURL;
        this.userBio = userBio;
        this.favouriteArtists = favouriteArtists;
        this.favouriteAlbums = favouriteAlbums;
        this.countryOrigin = countryOrigin;
    }

    public String getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getUserPfpURL() {
        return userPfpURL;
    }

    public String getUserBio() {
        return userBio;
    }

    public List<String> getFavouriteArtists() {
        return favouriteArtists;
    }

    public List<String> getFavouriteAlbums() {
        return favouriteAlbums;
    }

    public String getCountryOrigin() {
        return countryOrigin;
    }
}
