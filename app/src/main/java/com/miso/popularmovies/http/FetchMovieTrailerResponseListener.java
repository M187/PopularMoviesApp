package com.miso.popularmovies.http;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by michal.hornak on 08.09.2016.
 */
public class FetchMovieTrailerResponseListener implements Response.Listener<JSONObject> {

    private Activity mActivity;

    public FetchMovieTrailerResponseListener(Activity mainActivity){
        mActivity = mainActivity;
    }


    @Override
    public void onResponse(JSONObject response){
        try {
            Log.d("API.response", response.toString());

            Uri youtubeUrl = Uri.parse("http://www.youtube.com/watch?v=" + parseResponse(response.getJSONArray("results")));
            mActivity.startActivity(new Intent(Intent.ACTION_VIEW, youtubeUrl));
        } catch (JSONException e){}
    }

    private String parseResponse(JSONArray jsonArray) throws JSONException {
        JSONObject temp = jsonArray.getJSONObject(1);
        String a = temp.getString("key");
        return a;
    }
}
