package com.miso.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.miso.popularmovies.json.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by michal.hornak on 19.08.2016.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    private List<Movie> movies;
    private String size = "w342";
    private static MyClickListener mClickListener;

    public interface MyClickListener {
        void onItemClick(int position, View view);
    }

    public static class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView image;

        public MovieHolder(View itemView) {
            super(itemView);
            this.image = (ImageView) itemView.findViewById(R.id.movieTile);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mClickListener.onItemClick(getPosition(), view);
        }
    }

    public MovieAdapter(List<Movie> movies, MyClickListener clickListener) {
        this.movies = movies;
        this.mClickListener = clickListener;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_tile, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position){
        Picasso.with(holder.image.getContext())
                .load("http://image.tmdb.org/t/p/" + size + "/" + movies.get(position).posterPath)
                .placeholder(R.drawable.debug)
                .error(R.drawable.debug)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
