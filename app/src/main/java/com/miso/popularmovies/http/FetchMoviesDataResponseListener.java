package com.miso.popularmovies.http;

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
public class FetchMoviesDataResponseListener implements Response.Listener<JSONObject> {

    MovieAdapter mMovieAdapter;
    private volatile List<Movie> movies;

    public FetchMoviesDataResponseListener(MovieAdapter movieAdapter, List<Movie> movies) {
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
            movies.clear();
            movies.addAll(getMovieList(response.getJSONArray("results")));
        } catch (JSONException e) {}
        mMovieAdapter.notifyDataSetChanged();
    }


    private static ArrayList<Movie> getMovieList(JSONArray jsonArray) {

        ArrayList<Movie> returnList = new ArrayList<>();
        int i = 0;
        while (i < jsonArray.length()) {
            try {
                returnList.add(new Movie(jsonArray.getJSONObject(i)));
            } catch (Exception e) {
            }
            i++;
        }
        return returnList;
    }
}
