package com.miso.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.miso.popularmovies.http.AppRequestQueue;
import com.miso.popularmovies.http.FetchMovieTrailerResponseListener;
import com.miso.popularmovies.json.Movie;

public class MainActivity extends ActionBarActivity implements MovieDetailFragment.OnMovieDetailsFragmentInteractionListener {

    public static volatile String moviesdbApiKey;
    private MoviesGridFragment mMoviesList;
    private Movie mSelectedMovie;

    public boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        moviesdbApiKey = this.getResources().getString(R.string.MoviedbApiCode);
        setContentView(R.layout.movie_list);
        this.mTwoPane = (findViewById(R.id.movie_detail_fragment) != null) ? true : false;
        this.mMoviesList = (MoviesGridFragment) getFragmentManager().findFragmentById(R.id.movies_fragment);
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
        switch (item.getItemId()) {
            case R.id.action_top_rated:
                this.mMoviesList.fetchMeMoviesData(true);
                return true;
            case R.id.action_most_popular:
                this.mMoviesList.fetchMeMoviesData(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onMovieDetailsFragmentInteraction(Movie movie) {
        this.mSelectedMovie = movie;
        MovieDetailFragment frag = MovieDetailFragment.newInstance(movie);

        if (mTwoPane) {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.movie_detail_fragment, frag, "movieDetailFragment")
                    .commit();
        }else {
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main, frag, "movieDetailFragment")
                    .addToBackStack(null)
                    .commit();
        }
        Log.d("fragmentCreation", "Click ListItem " + movie.title);
    }

    public void playTrailer(View view){
        FetchMovieTrailerResponseListener comentsListener = new FetchMovieTrailerResponseListener(this);
        String url;
        url = "http://api.themoviedb.org/3/movie/" + this.mSelectedMovie.id + "/videos?api_key=" + MainActivity.moviesdbApiKey;
        AppRequestQueue.getInstance(this).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET,
                        url,
                        null,
                        comentsListener,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        }
                ));


    }
}
