package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    /* Global String variable */
    private String posterPath;
    private String title;
    private String overview;

    /* Default constructor to parse JSON objects */
    public Movie(JSONObject jsonObject) throws JSONException {

        /* Storing Parsed objects to be referenced in MainActivity */
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
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
