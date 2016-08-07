package com.miso.popularmovies;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Miso on 7.8.2016.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

    public MovieAdapter(Activity context, List<Movie> movieList){
        super(context, 0, R.id.movieGrid, movieList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Movie movie = getItem(position);
        View movieImageView = LayoutInflater.from(getContext()).inflate(R.layout.movie_tile, parent, false);

        //todo: set actual values here from entry in List.



        return movieImageView;
    }
}
