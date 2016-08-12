package com.miso.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.miso.popularmovies.json.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Miso on 7.8.2016.
 *
 * Class to process movie list.
 * Returns initialized imageViews for relevant GridView.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

    public Context context;
    private String size = "w92";

    public MovieAdapter(Activity context, List<Movie> movieList){
        super(context, 0, R.id.movieGrid, movieList);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Movie movie = getItem(position);
        ImageView movieImageView = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.movie_tile, parent, false);

        //movieImageView.setImageResource(R.drawable.debug);
        //todo: set actual values here from entry in the List.
        Picasso.with(this.getContext()).load("http://image.tmdb.org/t/p/" + size + "/" + movie.posterPath).into(movieImageView);

        return movieImageView;
    }
}
