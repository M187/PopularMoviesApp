package com.miso.popularmovies;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.miso.popularmovies.http.ThemoviedbClient;
import com.miso.popularmovies.json.Movie;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    public static volatile String moviesdbApiKey = null;

    MovieAdapter movieAdapter;

    public volatile List<Movie> movies = new ArrayList<>();

    public synchronized void setMovies(List<Movie> movies){
        this.movies = movies;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView loadingScreen = new ImageView(getBaseContext());
        loadingScreen.setImageDrawable(this.getResources().getDrawable(R.drawable.loading));
        setContentView(loadingScreen);
        moviesdbApiKey = getBaseContext().getString(R.string.MoviedbApiCode);

        fetchMeMoviesData(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
                fetchMeMoviesData(true);
                return true;
            case R.id.action_most_popular:
                fetchMeMoviesData(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Create and starts thread to fetch movie data.
     * Calls URL to fetch response from movieDb server and parse it into Movie ArrayList.
     */
    private void fetchMeMoviesData(boolean isPopular){

        new Thread(){

            private boolean isPopular = false;
            private MainActivity myActivity;

            /**
             * Just to initialize this thread.
             *
             * @param myActivity reference to List to store movies into
             * @param isPopular should we fetch popular or topRated?
             * @return this Thread to be started.
             */
            public Thread init(MainActivity myActivity, boolean isPopular){
                this.isPopular = isPopular;
                this.myActivity = myActivity;
                return this;
            }

            @Override
            public void run(){

                this.myActivity.setMovies(
                        (isPopular) ? ThemoviedbClient.getMostPopularMovies() : ThemoviedbClient.getTopRatedMovies());

                this.myActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setContentView(R.layout.movie_list);
                        movieAdapter = new MovieAdapter(myActivity, movies);
                        GridView movieGrid = (GridView) findViewById(R.id.movieGrid);
                        movieGrid.setAdapter(movieAdapter);
                        movieGrid.setOnItemClickListener(createDefaultListViewClickListener());
                    }
                });
            }

        }.init(this, isPopular).start();
    }

    /**
     * @return default OnItemClickListener for our ListView
     */
    private AdapterView.OnItemClickListener createDefaultListViewClickListener(){
        return (new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "Click ListItem Number " + position, Toast.LENGTH_LONG).show();
            }
        });
    }
}
