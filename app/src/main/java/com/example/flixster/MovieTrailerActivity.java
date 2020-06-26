package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

/* This activity for the video player must extend YouTubeBaseActivity */
public class MovieTrailerActivity extends YouTubeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);

        /* Create an intent object to get key passed from MovieDetailsActivity */
        Intent intent = getIntent();
        final String videoId = intent.getStringExtra("key");

        /* Resolve the player view from the layout */
        YouTubePlayerView playerView = findViewById(R.id.player);

        /* Initialize with API key stored in secrets.xml - its exclusive to me - get API key from Google  */
        playerView.initialize(getString(R.string.api_key), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                /* Do any work here to cue video, play video, etc. */
                youTubePlayer.cueVideo(videoId);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                /* Log the error */
                Log.e("Gustavo", "Error initializing YouTube player");
            }
        });
    }
}