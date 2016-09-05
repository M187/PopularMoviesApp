package com.miso.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miso.popularmovies.json.Review;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by michal.hornak on 25.08.2016.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder>  {

    private View view;
    private volatile List<Review> reviews;

    public static class ReviewHolder extends RecyclerView.ViewHolder{

        TextView textViewUsername;
        TextView textViewReview;

        public ReviewHolder(View view){
            super(view);
            this.textViewUsername = (TextView) view.findViewById(R.id.user);
            this.textViewReview = (TextView) view.findViewById(R.id.review);
        }
    }

    public ReviewAdapter(List<Review> reviews){
        this.reviews = reviews;
        try {
            this.reviews.add(new Review(new JSONObject("{\"author\":\"a\",\"content\":\"aa\"}")));
        } catch (Exception e){}
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_review, parent, false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position){
        holder.textViewUsername.setText((reviews.get(position)).userName);
        holder.textViewReview.setText((reviews.get(position)).review);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }
}