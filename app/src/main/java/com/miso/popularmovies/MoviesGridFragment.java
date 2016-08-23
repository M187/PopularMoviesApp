package com.miso.popularmovies;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.miso.popularmovies.http.AppRequestQueue;
import com.miso.popularmovies.http.FetchMoviesDataResponseListener;
import com.miso.popularmovies.json.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michal.hornak on 23.08.2016.
 */
public class MoviesGridFragment extends Fragment {

    public static volatile String moviesdbApiKey;

    private MovieAdapter mMovieAdapter;
    private FetchMoviesDataResponseListener mListener;
    private volatile List<Movie> movies = new ArrayList<>();

    //public MoviesGridFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        moviesdbApiKey = this.getResources().getString(R.string.MoviedbApiCode);

        this.mMovieAdapter = new MovieAdapter(movies, createMovieRecycleViewClickListener());

        this.mListener = new FetchMoviesDataResponseListener(this.mMovieAdapter, this.movies);
        fetchMeMoviesData(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedINstanceState){
        RecyclerView moviesView = (RecyclerView) container.findViewById(R.id.movieGrid);
        GridLayoutManager layMan = new GridLayoutManager(getActivity(), 2);
        moviesView.setLayoutManager(layMan);
        moviesView.setAdapter(this.mMovieAdapter);
        return moviesView;
    }

    /**
     * @return default MyClickListener for our RecycleView and its MovieHolders
     */
    private MovieAdapter.MyClickListener createMovieRecycleViewClickListener() {
        return ( new MovieAdapter.MyClickListener(){
            public void onItemClick(int position, View view) {
                Movie selectedMovie = movies.get(position);
                MovieDetailFragment frag = MovieDetailFragment.newInstance(selectedMovie);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main, frag, "movieDetailFragment")
                        .addToBackStack(null)
                        .commit();
                Log.d("fragmentCreation", "Click ListItem Number " + position);
            }
        });
    }

    /**
     * Add jsonRequest to fetch movies data into RequestQueue instance.
     * Calls URL to fetch response from movieDb server and parse it into Movie ArrayList.
     * Then it resets the movies array and refresh adapter.
     */
    private void fetchMeMoviesData(boolean isPopular) {
        String url;
        if (isPopular) {
            url = "http://api.themoviedb.org/3/movie/popular?api_key=" + MainActivity.moviesdbApiKey;
        } else {
            url = "http://api.themoviedb.org/3/movie/top_rated?api_key=" + MainActivity.moviesdbApiKey;
        }
        AppRequestQueue.getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET,
                        url,
                        null,
                        this.mListener,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getActivity(), "Error during fetching data!", Toast.LENGTH_LONG).show();
                                Log.d("ToastError:", error.toString());
                            }
                        }
                ));

    }

}
