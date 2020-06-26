package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.adapters.MovieAdapter;
import com.example.flixster.databinding.ActivityMainBinding;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
    /* String constant for API key */
    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=57012594aa33de542798335f5dbb10ce&language=en-US&page=1";
    /* Memeber variable */
    private List<Movie> movies;
    private RecyclerView rvMovies;

    /* Set up an instance of the binding class */
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Call the static inflate() method included in the generated binding class */
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());

        /* Get a reference to the root view by either calling the getRoot() method  */
        View view = activityMainBinding.getRoot();

        /* Pass the root view to setContentView() to make it the active view on the screen */
        setContentView(view);
        rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();

        /* Create the adapter */
        final MovieAdapter movieAdapter = new MovieAdapter(this, movies);
        /* Set the adapter on the RecyclerView */
        rvMovies.setAdapter(movieAdapter);
        /* Set a Layout Manager to let RV know how to display views onScreen */
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        /* Creating an instance of a client using CodePath library */
        AsyncHttpClient client = new AsyncHttpClient();
        /* Client get request to URL */
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                /* Get the JSON object for data */
                JSONObject jsonObject = json.jsonObject;
                /* Parse the JSON object within try_catch */
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    /* Calling method from Movie Class to return json object into a List */
                    movies.addAll(Movie.fromJsonArray(results));
                    /* Calling method to update the Adapter */
                    movieAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.e("Gustavo", "JSON Expection", e);
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d("Gustavo", "onSuccess");
            }
        });
    }
}