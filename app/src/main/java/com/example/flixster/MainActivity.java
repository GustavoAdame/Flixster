package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
    /* String constant for API key */
    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=57012594aa33de542798335f5dbb10ce&language=en-US&page=1";
    /* String constant for MainActivity for ease of use */
    public static final String TAG = "MainActivity";
    /* Memeber variable */
    private List<Movie> movies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Creating an instance of a client using CodePath library */
        AsyncHttpClient client = new AsyncHttpClient();

        /* Client get request to URL */
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");

                /* Get the JSON object for data */
                JSONObject jsonObject = json.jsonObject;

                /* Parse the JSON object within try_catch */
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    /* Get actual the JSON array on success */
                    Log.i(TAG, "Results: " + results.toString() );

                    /* Calling method from Movie Class to return json object into a List */
                    movies = Movie.fromJsonArray(results);

                    Log.i(TAG, "Movie: " + movies.size() );
                } catch (JSONException e) {
                    Log.e(TAG, "JSON Expection", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onSuccess");
            }
        });
    }
}