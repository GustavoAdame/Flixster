package com.example.flixster;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.databinding.ActivityMainBinding;
import com.example.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class MovieDetailsActivity  extends AppCompatActivity {
    /* The movie to display */
    public static Movie movie;
    public String Video_URL = "";

    /* The view objects */
    public TextView tvTitle;
    public ImageView ivBackdrop;
    public TextView tvOverview;
    public RatingBar rbVoteAverage;
    /* Important Key - Used to get video from MovieDB API */
    public String videoKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        /* Unwrap the movie passed in via intent, using its simple name as a key */
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));

        /* Resolve the view objects */
        ivBackdrop = findViewById(R.id.ivBackdrop); /* Used for onClick Image to fetch movie trailer */
        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        rbVoteAverage = findViewById(R.id.rbVoteAverage);

        /* Important API Request - get video for each movie via movie.getId() */
        Video_URL =
                String.format("https://api.themoviedb.org/3/movie/%s/videos?api_key=57012594aa33de542798335f5dbb10ce&language=en-US", movie.getId());

        /* Set the title and overview */
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        /* Vote average is 0..10, convert to 0..5 by dividing by 2 */
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);

        int radius = 30; /* corner radius, higher value = more rounded */
        int margin = 10; /* crop margin, set to 0 for corners with no crop */

        /* Important: Renders image in this activty - this image is the one being clicked to fetch trailer */
        Glide.with(MovieDetailsActivity.this).load(movie.getBackdropPath()).transform(new RoundedCornersTransformation(radius, margin)).into(ivBackdrop);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(Video_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");

                    /* Gets a single movie info hence positon at zero */
                    JSONObject movies = (JSONObject) results.get(0);
                    /* For a particular movie - get the key value, this value is passed to MovieTrailerActivity */
                    videoKey = movies.getString("key");

                    /* Important: The actual image click listener which setup the intent and pass the key to the another activity to handle */
                    ivBackdrop.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            /* Remember: Intent(current context -aka activity, goto new Activity) */
                            Intent i = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);

                            /* Important: String key is passed to be reference - when key is used - value = videoKey */
                            i.putExtra("key", videoKey);
                            startActivity(i);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                    Log.e("Gustavo", "HTTP request failed ");
            }
        });
    }
}
