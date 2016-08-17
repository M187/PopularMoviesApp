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
    private ViewHolder mHolder = new ViewHolder();

    private static class ViewHolder{
        //todo: substitue this with RecycleView
        ImageView imageView;
    }

    public MovieAdapter(Activity context, List<Movie> movieList){
        super(context, 0, R.id.movieGrid, movieList);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Movie movie = getItem(position);
        ImageView movieImageView;
        if (mHolder.imageView == null) {
            movieImageView = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.movie_tile, parent, false);
        } else {
            movieImageView = mHolder.imageView;
        }
        Picasso.with(this.getContext()).load("http://image.tmdb.org/t/p/" + size + "/" + movie.posterPath).placeholder(R.drawable.debug).error(R.drawable.debug).into(movieImageView);
        return movieImageView;
    }
}
