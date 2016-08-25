package com.miso.popularmovies.json;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by michal.hornak on 25.08.2016.
 */
public class Review implements Parcelable{

    public String userName;
    public String review;

    public Review(String userName, String review){
        this.userName = userName;
        this.review = review;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.userName);
        out.writeString(this.review);
    }

    private Review(Parcel in){
        userName = in.readString();
        review = in.readString();
    }

    // After implementing the `Parcelable` interface, we need to create the
    // `Parcelable.Creator<Movie> CREATOR` constant for our class;
    // Notice how it has our class specified as its type.
    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>(){
        @Override
        public Review createFromParcel(Parcel source) {
            return new Review(source);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}
