package com.example.soundcheck.model;
import java.time.LocalDate;

public class Review {

    private final String reviewID;
    private final String userID;
    private final String albumID;
    private final String trackID;
    private final float rating;
    private final String reviewText;
    private final LocalDate reviewDate;

    public Review(String reviewID,
                  String userID,
                  String albumID,
                  String trackID,
                  float rating,
                  String reviewText,
                  LocalDate reviewDate) {

        int count = 0;

        if (albumID != null) count++;
        if (trackID != null) count++;

        if (count != 1) {
            throw new IllegalArgumentException(
                    "A review must be associated with exactly one entity.");
        }

        if (rating < 0 || rating > 10) {
            throw new IllegalArgumentException("Review Rating is invalid");
        }
        this.reviewID = reviewID;
        this.userID = userID;
        this.albumID = albumID;
        this.trackID = trackID;
        this.rating = rating;
        this.reviewText = reviewText;
        this.reviewDate = reviewDate;
    }

    public String getReviewID() {
        return reviewID;
    }

    public String getUserID() {
        return userID;
    }

    public String getAlbumID() {
        return albumID;
    }

    public String getTrackID() {
        return trackID;
    }

    public float getRating() {
        return rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }
}
