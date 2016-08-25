package com.miso.popularmovies.http;

import android.util.Log;

import com.android.volley.Response;
import com.miso.popularmovies.json.Review;
import com.miso.popularmovies.ReviewAdapter;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by michal.hornak on 25.08.2016.
 */
public class FetchReviewsResponseListener implements Response.Listener<JSONObject>{

    private ReviewAdapter mReviewAdapter;
    private List<Review> reviews;

    public FetchReviewsResponseListener(ReviewAdapter reviewAdapter, List<Review> reviews){
        this.mReviewAdapter = reviewAdapter;
        this.reviews = reviews;
    }

    @Override
    public void onResponse(JSONObject response){
        try {
            reviews.clear();
            Log.d("API.response", response.toString());
        } catch (Exception e) {}
        mReviewAdapter.notifyDataSetChanged();
    }

//    private List<Review> parseResponse(JSONObject response){
//    }
}
