package com.example.soundcheck.api;

import com.example.soundcheck.model.Artist;
import com.example.soundcheck.model.Track;
import com.example.soundcheck.model.Album;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TheAudioDBAPI {

    private static final String API_Key = "123";
    private static final String URL_BASE =
            "https://www.theaudiodb.com/api/v1/json/";

    MusicBrainzAPI api = new MusicBrainzAPI();

    public void obtainArtistMetaData (Artist artist) throws IOException {

        String artistID = artist.getId();

        String resultURL = URL_BASE + API_Key + "/artist-mb.php?s=" +
                URLEncoder.encode(artistID, "UTF-8");

        URL url = new URL(resultURL);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        StringBuilder response = new StringBuilder();

        try {

            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Pedido realizado com sucesso.");


                InputStream inputStream = connection.getInputStream();


                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream, StandardCharsets.UTF_8));

                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

            } else { System.out.println("HTTP Error: " + responseCode); }
        } finally { connection.disconnect(); }

        if (response.length() > 0) {
            try {

                JSONObject jsonResponse = new JSONObject(response.toString());

                JSONArray results = jsonResponse.optJSONArray("artists");

                if (results != null) {
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject artistJSON = results.optJSONObject(i);

                        if (artistJSON == null) continue;

                        String mbid = artistJSON.optString("strMusicBrainzID");

                        if (mbid.equals(artistID)) {

                            String description =
                                    artistJSON.optString("strBiographyEN");

                            String thumbnail =
                                    artistJSON.optString("strArtistThumb");

                            artist.setArtistDescription(description);
                            artist.setArtistPfpURL(thumbnail);

                            break;
                        }
                    }
                }

            } catch (JSONException e) { e.printStackTrace(); }
        }
    }


    public void obtainTopTracks (Artist artist) throws IOException {    //limitado a 1 faixa devido à versão premium

        String artistID = artist.getId();

        String resultURL = URL_BASE + API_Key + "/track-top10-mb.php?s=" +
                URLEncoder.encode(artistID, "UTF-8");

        URL url = new URL(resultURL);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        StringBuilder response = new StringBuilder();

        try {
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Pedido realizado com sucesso.");


                InputStream inputStream = connection.getInputStream();


                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream, StandardCharsets.UTF_8));

                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

            } else { System.out.println("HTTP Error: " + responseCode); }
        } finally { connection.disconnect(); }

        List<Track> topTracks = new ArrayList<>();

        if (response.length() > 0) {
            try {

                JSONObject jsonResponse = new JSONObject(response.toString());

                JSONArray results = jsonResponse.optJSONArray("track");

                if (results != null) {
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject trackJSON = results.optJSONObject(i);

                        if (trackJSON == null) continue;

                        String mbid = trackJSON.optString("strMusicBrainzArtistID");

                        if (mbid.equals(artistID)) {

                            String trackID = trackJSON.optString("strMusicBrainzID");
                            String trackName = trackJSON.optString("strTrack");

                            String albumID = trackJSON.optString("strMusicBrainzAlbumID");
                            String albumName = trackJSON.optString("strAlbum");

                            String artistName = trackJSON.optString("strArtist");

                            Track track = new Track(
                                    trackID,
                                    trackName,
                                    albumID,
                                    albumName,
                                    artistID,
                                    artistName);

                            topTracks.add(track);
                        }

                    }
                }
            } catch (JSONException e) { e.printStackTrace(); }
        }
        artist.setTopTracks(topTracks);
    }

    public void obtainAlbumMetaData (Album album) throws IOException {

        String albumID = album.getAlbumID();

        String resultURL = URL_BASE + API_Key + "/album-mb.php?i=" +
                URLEncoder.encode(albumID, "UTF-8");

        URL url = new URL(resultURL);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        StringBuilder response = new StringBuilder();

        try {

            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Pedido realizado com sucesso.");


                InputStream inputStream = connection.getInputStream();


                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream, StandardCharsets.UTF_8));

                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

            } else { System.out.println("HTTP Error: " + responseCode); }
        } finally { connection.disconnect(); }

        if (response.length() > 0) {
            try {

                JSONObject jsonResponse = new JSONObject(response.toString());

                JSONArray results = jsonResponse.optJSONArray("album");

                if (results != null) {
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject artistJSON = results.optJSONObject(i);

                        if (artistJSON == null) continue;

                        String mbid = artistJSON.optString("strMusicBrainzID");

                        if (mbid.equals(albumID)) {

                            String description =
                                    artistJSON.optString("strDescriptionEN");

                            String cover =
                                    artistJSON.optString("strAlbumThumb");

                            album.setAlbumDescription(description);
                            album.setCoverURL(cover);

                            break;
                        }
                    }
                }

            } catch (JSONException e) { e.printStackTrace(); }
        }
    }

}
