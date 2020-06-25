package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.MovieDetailsActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    /* Must need objects to properly displaying the ViewAdapter */
    public Context context;
    private List<Movie> movies;

    /* Default constructor for passing in necessary objects */
    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    /* This method inflates layout and returns to the holder */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /* Inflating View Model with item_movie.xml */
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        /* Return movieView inside a ViewHolder */
        return new ViewHolder(movieView);
    }

    /* This method populates data into the item via holder */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /* Get movie's position when passed */
        Movie movie = movies.get(position);
        /* Bind the movie data into the ViewHolder */
        holder.bind(movie);
    }

    /* Returns total count of items in the list */
    @Override
    public int getItemCount() {
        return movies.size();
    }

    /* ViewHolder Class - standard in implementing an adapter and implements View.OnClickListener */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /* Member Variables */
        public TextView tvTitle;
        public TextView tvOverview;
        public ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            /* Reference xml elements from @item_movie.xml */
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            /* itemView's OnClickListener */
            itemView.setOnClickListener(this);
        }

        /* This method is used to populate each view */
        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            /* Image Url Variable since it can change depending on orientation */
            String imageUrl = "";

            /* If landscape set correct path - Else set the other path and pass it to Glide to render */
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                /* imageUrl = back drop image */
                imageUrl = movie.getBackdropPath();
            } else {
                /* imageUrl = poster image */
                imageUrl = movie.getPosterPath();
            }

            /* Use Glide library to render remote images */
            Glide.with(context).load(imageUrl).into(ivPoster);

        }

        /* When the user clicks on a row, show MovieDetailsActivity for the selected movie */
        @Override
        public void onClick(View view) {
            /* Gets item position */
            int position = getAdapterPosition();

            /* Make sure the position is valid */
            if (position != RecyclerView.NO_POSITION) {
                /* Get the movie at the position, this won't work if the class is static */
                Movie movie = movies.get(position);
                /* Create intent for the new activity */
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                /* Serialize the movie using parceler, use its short name as a key */
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                /* Show the activity */
                context.startActivity(intent);
            }

        }
    }
}
