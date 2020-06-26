package com.example.flixster.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel /* annotation indicates class is Parcelable */
public class Movie {
    /* Global String variable */
    public String posterPath;
    public String backdropPath;
    public String title;
    public String overview;
    public Double voteAverage;
    public int id;

    /* no-arg, empty constructor required for Parceler */
    public Movie() {}

    /* Default constructor to parse JSON objects */
    public Movie(JSONObject jsonObject) throws JSONException {
        /* Storing Parsed objects to be referenced in MainActivity */
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        backdropPath = jsonObject.getString("backdrop_path");
        voteAverage = jsonObject.getDouble("vote_average");
        id = jsonObject.getInt("id");
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    /* Used to get ID of a certain movie - used for Movie Trailer */
    public int getId() {
        return id;
    }

    /* This method returns a List of movie objects */
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < movieJsonArray.length(); i++) {
            /* Adding at each postion in JSONArray into ArrayList movies */
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getBackdropPath() {
        /* API configuration for image poster and appending relative paths */
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getPosterPath() {
        /* API configuration for image poster and appending relative paths */
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}
