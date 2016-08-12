package com.miso.popularmovies.http;

import android.util.Log;

import com.miso.popularmovies.MainActivity;
import com.miso.popularmovies.json.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michal.hornak on 10.08.2016.
 *
 * Sample response for movie list:
 * {
 "page": 1,
 "results": [
 {
 "poster_path": "\/9O7gLzmreU0nGkIB6K3BsJbzvNv.jpg",
 "adult": false,
 "overview": "Framed in the 1940s for the double murder of his wife and her lover, upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- for his integrity and unquenchable sense of hope.",
 "release_date": "1994-09-10",
 "genre_ids": [
 18,
 80
 ],
 "id": 278,
 "original_title": "The Shawshank Redemption",
 "original_language": "en",
 "title": "The Shawshank Redemption",
 "backdrop_path": "\/xBKGJQsAIeweesB79KC89FpBrVr.jpg",
 "popularity": 7.426397,
 "vote_count": 5143,
 "video": false,
 "vote_average": 8.32
 },
 */
public abstract class ThemoviedbClient {

    //<editor-fold @desc="Stuff related to obtaining Lists of movies.">
    public static List<Movie> getTopRatedMovies() {
        try {
            URL url = new URL("http://api.themoviedb.org/3/movie/top_rated?api_key=" + MainActivity.moviesdbApiKey);
            return getMovieList(new JSONObject(readGetResponse(url)).getJSONArray("results"));
        } catch (Exception e){
            return null;
        }
    }

    public static List<Movie> getMostPopularMovies() {
        try {
            URL url = new URL("http://api.themoviedb.org/3/movie/popular?api_key=" + MainActivity.moviesdbApiKey);
            return getMovieList(new JSONObject(readGetResponse(url)).getJSONArray("results"));
        } catch (Exception e){
            return null;
        }
    }

    private static String readGetResponse(URL url) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            reader.close();

            if (buffer.length() == 0) {
                return null;
            }

            Log.d("Http",buffer.toString());
            return buffer.toString();
        } catch (Exception e) {
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
    }

    private static ArrayList<Movie> getMovieList(JSONArray jsonArray){

        ArrayList<Movie> returnList = new ArrayList<>();
        int i = 0;
        while (i < jsonArray.length()){
            try {
                returnList.add(new Movie(jsonArray.getJSONObject(i)));
            } catch (Exception e){ }
            i++;
        }
        return returnList;
    }
    //</editor-fold>
}
