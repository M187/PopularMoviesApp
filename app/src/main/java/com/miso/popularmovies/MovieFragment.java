package com.miso.popularmovies;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.miso.popularmovies.json.Movie;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnMovieDetailsFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieFragment extends Fragment {

    private static final String SELECETD_MOVIE = "selectedMovie";

    private Movie selectedMovie;
    private ViewHolder mHolder = new ViewHolder();

    private OnMovieDetailsFragmentInteractionListener mListener;

    private static class ViewHolder {
        ScrollView scrollView;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param selectedMovie in ?onClickMethod
     * @return A new instance of fragment MovieFragment.
     */
    public static MovieFragment newInstance(Movie selectedMovie) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putParcelable(SELECETD_MOVIE, selectedMovie);
        fragment.setArguments(args);
        return fragment;
    }

    public MovieFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.selectedMovie = getArguments().getParcelable(SELECETD_MOVIE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ScrollView view;
        if (mHolder.scrollView == null) {
            view = (ScrollView) inflater.inflate(R.layout.movie_details, container, false);
        } else {
            view = mHolder.scrollView;
        }
        setHasOptionsMenu(true);
        setMovieData(view);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onMovieDetailsFragmentInteraction(uri);
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
        public void onMovieDetailsFragmentInteraction(Uri uri);
    }

    private void setMovieData(View view) {

        ((TextView) view.findViewById(R.id.movieTitle)).setText(selectedMovie.title);
        ((TextView) view.findViewById(R.id.movieReleaseDate)).setText(selectedMovie.releaseDate);
        ((TextView) view.findViewById(R.id.movieVoteAverage)).setText(selectedMovie.voteAverage);
        ((TextView) view.findViewById(R.id.moviePlotSynopsis)).setText(selectedMovie.overview);

        Picasso.with(this.getActivity().getBaseContext()).load("http://image.tmdb.org/t/p/" + "w342" + "/" + selectedMovie.posterPath).into(((ImageView) view.findViewById(R.id.moviePoster)));
    }
}
