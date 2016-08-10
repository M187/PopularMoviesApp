package com.miso.popularmovies.json;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Miso on 7.8.2016.
 *
 * Simple POJO to hold data about Movie.
 */
public class Movie {

    public String posterPath;
    public String id;
    public boolean adult;
    public String overview;
    public String releaseDate;
    public String genreIds;
    public String originalTitle;
    public String originalLanguage;
    public String title;
    public String backdropPath;
    public String popularity;
    public String voteCount;
    public boolean video;
    public String voteAverage;

    public Movie(JSONObject jsonMovieRepresentation) throws JSONException{
        this.posterPath = jsonMovieRepresentation.getString("poster_path");
        this.id = jsonMovieRepresentation.getString("id");
        this.adult = jsonMovieRepresentation.getBoolean("adult");
        this.overview = jsonMovieRepresentation.getString("overview");
        this.releaseDate = jsonMovieRepresentation.getString("release_date");
        //todo - genre handling
        this.genreIds = jsonMovieRepresentation.getString("genre_ids");
        this.originalTitle = jsonMovieRepresentation.getString("original_title");
        this.originalLanguage = jsonMovieRepresentation.getString("original_language");
        this.title = jsonMovieRepresentation.getString("title");
        this.backdropPath = jsonMovieRepresentation.getString("backdrop_path");
        this.popularity = jsonMovieRepresentation.getString("popularity");
        this.voteCount = jsonMovieRepresentation.getString("vote_count");
        this.video = jsonMovieRepresentation.getBoolean("video");
        this.voteAverage = jsonMovieRepresentation.getString("vote_average");
    }
}
