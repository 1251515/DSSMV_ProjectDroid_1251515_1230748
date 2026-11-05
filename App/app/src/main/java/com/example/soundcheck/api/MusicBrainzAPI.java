package com.example.soundcheck.api;

import android.renderscript.ScriptGroup;
import android.util.Log;
import com.example.soundcheck.model.Album;
import com.example.soundcheck.model.Artist;
import com.example.soundcheck.model.Track;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class MusicBrainzAPI {

    private static final String URL_base = "https://musicbrainz.org/ws/2/";

    public List<String> getGenres (String entityType, String entityID)
            throws IOException {

        String resultURL = URL_base + entityType + "/" + entityID
                + "?inc=genres&fmt=json";

        URL url = new URL(resultURL);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent",
                "Soundcheck/1.0 (contact: 1251515@isep.ipp.pt)");

        StringBuilder response = new StringBuilder();

        try {
            int responseCode = connection.getResponseCode();

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

            } else {
                System.out.println("HTTP Error: " + responseCode);
            }
        } finally {
            connection.disconnect();
        }

        List<String> genreList = new ArrayList<>();

        if (response.length() > 0) {

            try {
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray genres = jsonResponse.optJSONArray("genres");

                if (genres != null) {
                    for (int i = 0; i < genres.length() && i < 2; i++) {

                        JSONObject genreJSON = genres.getJSONObject(i);

                        String genreName = genreJSON.getString("name");

                        genreList.add(genreName);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return genreList;
    }

    public JSONObject getDetails (String entityType, String entityID)
        throws IOException {

        try {
            String resultURL = URL_base + entityType + "/" + entityID;

            if (entityType.equals("release")) {
                resultURL += "?inc=media+recordings&fmt=json";
            } else if (entityType.equals("release-group")) {
                resultURL += "?inc=releases&fmt=json";
            } else if (entityType.equals("recording")){
                resultURL += "?inc=releases+release-groups&fmt=json";
            } else {
                resultURL += "?fmt=json";
            }

            URL url = new URL(resultURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent",
                    "Soundcheck/1.0 (contact: 1251515@isep.ipp.pt)");

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

                    return new JSONObject(response.toString());

                } else {
                    System.out.println("HTTP Error: " + responseCode);
                    return null;
                }
            } finally { connection.disconnect(); }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getLength (JSONObject details) {

        if (details == null) { return null; }

        int length = 0;

        JSONArray media = details.optJSONArray("media");

        if (media == null) { return null; }

        for (int i = 0; i < media.length(); i++) {

            JSONObject medium = media.optJSONObject(i);

            if (medium != null) {
                length += medium.optInt("track-count", 0); //nº faixas por disco
            }
        }
        return length + "faixas";
    }

    public String getDuration(JSONObject details, String entityType) {

        if (entityType.equals("track")) {
            int totalSeconds = details.optInt("length", 0)  / 1000;
            int minutes = totalSeconds / 60;
            int seconds = totalSeconds % 60;
            return minutes + "min" + seconds + "seg";
        }

        if (entityType.equals("album")) {
            int duration = 0;

            JSONArray media = details.optJSONArray("media"); //discos

            if (media != null) {
                for (int i = 0; i < media.length(); i++) {
                    JSONObject medium = media.optJSONObject(i); //disco atual

                    if (medium == null) { continue; }

                    JSONArray tracks = medium.optJSONArray("tracks");  //faixas do disco

                    if (tracks != null) {
                        for (int j = 0; j < tracks.length(); j++) {
                            JSONObject track = tracks.optJSONObject(j); //faixa
                            JSONObject recording = track.optJSONObject("recording"); //gravação

                            if (recording != null) {
                                duration += recording.optInt("length", 0);
                            }
                        }
                    }
                }
            }
            int totalSeconds = duration / 1000;
            int hours = totalSeconds / 3600;
            int minutes = (totalSeconds % 3600) / 60;
            return hours + 'h' + minutes + "min";
        }
        return null;
    }

    public List<Artist> artistSearch(String query)
            throws IOException {

        String resultURL = URL_base + "artist/?query=" +
                URLEncoder.encode(query, "UTF-8") + "&limit=3&fmt=json";

        URL url = new URL(resultURL);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent",
                "Soundcheck/1.0 (contact: 1251515@isep.ipp.pt)");

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

        List<Artist> artistList = new ArrayList<>();

        if (response.length() > 0) {

            try {
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray artists = jsonResponse.optJSONArray("artists");


                for (int i = 0; i < artists.length(); i++) {

                    JSONObject artistJSON = artists.getJSONObject(i);

                    String id = artistJSON.getString("id");
                    String name = artistJSON.getString("name");
                    String type = artistJSON.optString("type", null);

                    List<String> genres = getGenres("artist", id);

                    Artist artist = new Artist(id, name, type, genres);

                    artistList.add(artist);
                }
            } catch (JSONException e) { e.printStackTrace(); }

        }
        return artistList;
    }


    public List<Album> albumSearch (String query)
            throws IOException {

        String resultURL = URL_base + "release-group/?query=" +
                URLEncoder.encode(query, "UTF-8") + "&limit=3&fmt=json";

        URL url = new URL(resultURL);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent",
                "SoundCheck/1.0 (contact: 1251515@isep.ipp.pt");

        int responseCode = connection.getResponseCode();

        StringBuilder response = new StringBuilder();

        try {

            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Pedido realizado com sucesso.");

                InputStream inputStream = connection.getInputStream();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream, "UTF-8"));

                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
            } else { System.out.println("HTTP Error: " + responseCode); }
        } finally { connection.disconnect(); }

        List<Album> albumList = new ArrayList<>();

        if (response.length() > 0) {

            try {
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray albums = jsonResponse.getJSONArray("release-groups");

                for (int i = 0; i < albums.length(); i++) {

                    JSONObject albumJSON = albums.getJSONObject(i);

                    JSONArray artistCredit = albumJSON.getJSONArray("artist-credit");
                    JSONObject artistObject = artistCredit.getJSONObject(0).getJSONObject
                            ("artist");

                    String id = albumJSON.getString("id");
                    String name = albumJSON.getString("title");
                    String albumReleaseDate = albumJSON.getString("first-release-date");


                    String artistID = artistObject.getString("id");
                    String artistName = artistObject.getString("name");

                    List<String> genres = getGenres("album", id);
                    String albumType = albumJSON.getString("primary-type");

                    JSONObject albumDetails = getDetails("release-group", id);

                    String albumLength = null;
                    String albumDuration = null;

                    if (albumDetails != null) {

                        JSONArray albumReleases = albumDetails.optJSONArray("releases");

                        if (albumReleases != null && albumReleases.length() > 0) {

                            JSONObject release = albumReleases.getJSONObject(0);

                            if (release != null) {
                                String releaseID = release.optString("id", null);

                                if (!releaseID.isEmpty()) {

                                    JSONObject releaseDetails = getDetails("release", releaseID);

                                    if (releaseDetails != null) {
                                        albumLength = getLength(releaseDetails);
                                        albumDuration = getDuration(releaseDetails, "album");
                                    }
                                }
                            }
                        }
                    }
                    Album album = new Album(id, name, artistID, artistName, genres,
                            albumReleaseDate, albumType, albumLength, albumDuration);

                    albumList.add(album);

                }

                } catch (JSONException e) { e.printStackTrace(); }
            }
        return albumList;
        }

    public List<Track> trackSearch(String query)
            throws IOException {

        String resultURL = URL_base + "recording/?query=" +
                URLEncoder.encode(query, "UTF-8") + "&limit=3&fmt=json";

        URL url = new URL(resultURL);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent",
                "Soundcheck/1.0 (contact: 1251515@isep.ipp.pt)");

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

        List<Track> trackList = new ArrayList<>();

        if (response.length() > 0) {

            try {
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray tracks = jsonResponse.getJSONArray("recordings");


                for (int i = 0; i < tracks.length(); i++) {

                    JSONObject trackJSON = tracks.getJSONObject(i);

                    JSONArray artistCredit = trackJSON.getJSONArray("artist-credit");
                    JSONObject artistObject = artistCredit.getJSONObject(0).
                            getJSONObject("artist");

                    String id = trackJSON.getString("id");
                    String name = trackJSON.getString("title");

                    String artistID = artistObject.getString("id");
                    String artistName = artistObject.getString("name");

                    JSONObject trackDetails = getDetails("recording", id);

                    String albumID = null;
                    String albumName = null;
                    String releaseDate = null;
                    String trackDuration = null;

                    if (trackDetails != null) {

                        JSONArray releases = trackDetails.optJSONArray("releases");

                        trackDuration = getDuration(trackDetails, "track");

                        if (releases != null && releases.length() > 0) {

                            JSONObject release = releases.optJSONObject(0);

                            if (release != null) {
                                JSONObject releaseGroup = release.optJSONObject("release-group");

                                if (releaseGroup != null) {
                                    albumID = releaseGroup.optString("id");
                                    albumName = releaseGroup.optString("title");
                                    releaseDate = releaseGroup.optString("first-release-date");
                                }
                            }
                        }
                    }

                    Track track = new Track(id, name, albumID, albumName,
                            artistID, artistName, trackDuration, releaseDate);

                    trackList.add(track);
                }
            } catch (JSONException e) { e.printStackTrace(); }

        }
        return trackList;
    }
}
