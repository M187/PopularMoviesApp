package com.miso.popularmovies;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
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

public class MainActivity extends ActionBarActivity implements MovieFragment.OnMovieDetailsFragmentInteractionListener {

    public static volatile String moviesdbApiKey;

    private MovieAdapter mMovieAdapter;
    private FetchMoviesDataResponseListener mListener;
    private volatile List<Movie> movies = new ArrayList<>();

    public synchronized void setMovies(List<Movie> movies){
        this.movies.clear();
        this.movies.addAll(movies);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moviesdbApiKey = this.getResources().getString(R.string.MoviedbApiCode);
        setContentView(R.layout.movie_list);
        this.mMovieAdapter = new MovieAdapter(this, movies);
        GridView movieGrid = (GridView) findViewById(R.id.movieGrid);
        movieGrid.setAdapter(this.mMovieAdapter);
        movieGrid.setOnItemClickListener(createDefaultListViewClickListener());
        this.mListener = new FetchMoviesDataResponseListener(this.mMovieAdapter, this.movies);
        fetchMeMoviesData(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.action_top_rated:
                //etContentView(R.layout.loading_layout);
                fetchMeMoviesData(true);
                return true;
            case R.id.action_most_popular:
                //setContentView(R.layout.loading_layout);
                fetchMeMoviesData(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Add jsonRequest to fetch movies data into RequestQueue instance.
     * Calls URL to fetch response from movieDb server and parse it into Movie ArrayList.
     * Then it resets the movies array and refresh adapter.
     */
    private void fetchMeMoviesData(boolean isPopular){
        String url;
        if (isPopular){
            url = "http://api.themoviedb.org/3/movie/popular?api_key=" + MainActivity.moviesdbApiKey;
        } else {
            url = "http://api.themoviedb.org/3/movie/top_rated?api_key=" + MainActivity.moviesdbApiKey;
        }
        JsonObjectRequest fetchMoviesDataReq = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                this.mListener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getBaseContext(), "Error during fetching data!", Toast.LENGTH_LONG).show();
                        Log.d("Response:", error.networkResponse.toString());
                        Log.d("ToastError:", error.toString());
                    }
                }
        );

        AppRequestQueue.getInstance(this).addToRequestQueue(fetchMoviesDataReq);
    }

    /**
     * @return default OnItemClickListener for our ListView
     */
    private AdapterView.OnItemClickListener createDefaultListViewClickListener(){
        return (new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Movie selectedMovie = (Movie)parent.getAdapter().getItem(position);

                MovieFragment frag = MovieFragment.newInstance(selectedMovie);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main, frag, "movieDetailFragment")
                        .addToBackStack(null)
                        .commit();

                Log.d("fragmentCreation", "Click ListItem Number " + position);
            }
        });
    }

    @Override
    public void onMovieDetailsFragmentInteraction(Uri uri) {
        this.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
