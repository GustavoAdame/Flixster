package com.example.flixster.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    /* Must need objects to properly displaying the ViewAdapter */
    private Context context;
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

    /* ViewHolder Class - standard in implementing an adapter */
    public class ViewHolder extends RecyclerView.ViewHolder{
        /* Member Variables */
        private TextView tvTitle;
        private TextView tvOverview;
        private ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            /* Reference xml elements from @item_movie.xml */
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }

        /* This method is used to populate each view */
        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            /* Use Glide library to render remote images */
            Glide.with(context).load(movie.getPosterPath()).into(ivPoster);
        }
    }
}
