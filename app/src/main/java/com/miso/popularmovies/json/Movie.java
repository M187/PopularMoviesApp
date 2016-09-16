package com.miso.popularmovies.json;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Miso on 7.8.2016.
 *
 * Simple POJO to hold data about Movie.
 */
public class Movie implements Parcelable {

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
        //this.genreIds = jsonMovieRepresentation.getString("genre_ids");
        this.originalTitle = jsonMovieRepresentation.getString("original_title");
        this.originalLanguage = jsonMovieRepresentation.getString("original_language");
        this.title = jsonMovieRepresentation.getString("title");
        this.backdropPath = jsonMovieRepresentation.getString("backdrop_path");
        this.popularity = jsonMovieRepresentation.getString("popularity");
        this.voteCount = jsonMovieRepresentation.getString("vote_count");
        this.video = jsonMovieRepresentation.getBoolean("video");
        this.voteAverage = jsonMovieRepresentation.getString("vote_average");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.posterPath);
        out.writeString(this.id);
        out.writeString(String.valueOf(this.adult));
        out.writeString(this.overview);
        out.writeString(this.releaseDate);
        //todo - genre handling
        out.writeString(this.genreIds);
        out.writeString(this.originalTitle);
        out.writeString(this.originalLanguage);
        out.writeString(this.title);
        out.writeString(this.backdropPath);
        out.writeString(this.popularity);
        out.writeString(this.voteCount);
        out.writeString(String.valueOf(this.video));
        out.writeString(this.voteAverage);
    }

    private Movie(Parcel in){
        posterPath = in.readString();
        id = in.readString();
        adult = Boolean.getBoolean(in.readString());
        overview = in.readString();
        releaseDate = in.readString();
        //todo - genre handling
        genreIds = in.readString();
        originalTitle = in.readString();
        originalLanguage = in.readString();
        title = in.readString();
        backdropPath = in.readString();
        popularity = in.readString();
        voteCount = in.readString();
        video = Boolean.parseBoolean(in.readString());
        voteAverage = in.readString();
    }

    // After implementing the `Parcelable` interface, we need to create the
    // `Parcelable.Creator<Movie> CREATOR` constant for our class;
    // Notice how it has our class specified as its type.
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

}
