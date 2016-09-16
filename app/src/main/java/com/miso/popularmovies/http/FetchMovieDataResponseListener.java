package com.miso.popularmovies.http;

import android.util.Log;

import com.android.volley.Response;
import com.miso.popularmovies.MovieAdapter;
import com.miso.popularmovies.json.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michal.hornak on 18.08.2016.
 */
public class FetchMovieDataResponseListener implements Response.Listener<JSONObject> {

    MovieAdapter mMovieAdapter;
    private volatile List<Movie> movies;

    public FetchMovieDataResponseListener(MovieAdapter movieAdapter, List<Movie> movies) {
        this.mMovieAdapter = movieAdapter;
        this.movies = movies;
    }

    @Override
    /**
     * clear movies array and push new data into it.
     * notify adapter that list of movies has been changed.
     */
    public void onResponse(JSONObject response) {
        try {
            movies.add(new Movie(response));
            Log.d("API.response", response.toString());
        } catch (JSONException e) {}
        mMovieAdapter.notifyDataSetChanged();
    }
}
