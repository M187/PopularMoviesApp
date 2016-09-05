package com.miso.popularmovies;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.miso.popularmovies.http.AppRequestQueue;
import com.miso.popularmovies.http.FetchReviewsResponseListener;
import com.miso.popularmovies.json.Movie;
import com.miso.popularmovies.json.Review;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnMovieDetailsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailFragment extends Fragment {

    private static final String SELECETD_MOVIE = "selectedMovie";

    private Movie selectedMovie;
    private ViewHolder mHolder = new ViewHolder();
    private OnMovieDetailsFragmentInteractionListener mListener;

    private FetchReviewsResponseListener fetchReviewsResponseListener;
    private volatile List<Review> reviews = new ArrayList<>();
    private ReviewAdapter mReviewAdapter;

    private static class ViewHolder {
        RelativeLayout scrollView;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param selectedMovie in ?onClickMethod
     * @return A new instance of fragment MovieDetailFragment.
     */
    public static MovieDetailFragment newInstance(Movie selectedMovie) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(SELECETD_MOVIE, selectedMovie);
        fragment.setArguments(args);
        return fragment;
    }

    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.selectedMovie = getArguments().getParcelable(SELECETD_MOVIE);
        }

        this.mReviewAdapter = new ReviewAdapter(reviews);
        this.fetchReviewsResponseListener = new FetchReviewsResponseListener(mReviewAdapter, reviews);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout view;
        if (mHolder.scrollView == null) {
            view = (RelativeLayout) inflater.inflate(R.layout.movie_details_fragment, container, false);
        } else {
            view = mHolder.scrollView;
        }
        setHasOptionsMenu(true);
        setMovieData(view);


        //Attach recyclerView here
        RecyclerView reviewsRecyclerView = (RecyclerView) view.findViewById(R.id.reviewGrid);
        GridLayoutManager layMan = new GridLayoutManager(getActivity(), 1);
        reviewsRecyclerView.setLayoutManager(layMan);
        reviewsRecyclerView.setAdapter(this.mReviewAdapter);
        fetchReviewData();


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Movie movie) {
        if (mListener != null) {
            mListener.onMovieDetailsFragmentInteraction(movie);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnMovieDetailsFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnMovieDetailsFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_movie_details, menu);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMovieDetailsFragmentInteractionListener {
        // TODO: Update argument type and name
        void onMovieDetailsFragmentInteraction(Movie movie);
    }

    private void setMovieData(View view) {

        try {
            ((TextView) view.findViewById(R.id.movieTitle)).setText(selectedMovie.title);
            ((TextView) view.findViewById(R.id.movieReleaseDate)).setText(selectedMovie.releaseDate);
            ((TextView) view.findViewById(R.id.movieVoteAverage)).setText(selectedMovie.voteAverage);
            ((TextView) view.findViewById(R.id.moviePlotSynopsis)).setText(selectedMovie.overview);

            Picasso.with(this.getActivity().getBaseContext()).load("http://image.tmdb.org/t/p/" + "w342" + "/" + selectedMovie.posterPath).into(((ImageView) view.findViewById(R.id.moviePoster)));
        }catch (NullPointerException e){
            ((TextView) view.findViewById(R.id.movieTitle)).setText("SELECT MOVIE");
            ((TextView) view.findViewById(R.id.movieTitle)).setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        }
    }

    private void fetchReviewData(){
        String url = ("http://api.themoviedb.org/3/movie/" + this.selectedMovie.id + "/reviews?api_key=" + MainActivity.moviesdbApiKey);

        AppRequestQueue.getInstance(getActivity()).addToRequestQueue(
                new JsonObjectRequest(
                        Request.Method.GET,
                        url,
                        null,
                        this.fetchReviewsResponseListener,
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
