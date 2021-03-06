package com.example.josnar.popularmovies.network;

import android.content.Context;
import android.support.v4.content.Loader;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.josnar.popularmovies.R;
import com.example.josnar.popularmovies.data.PrivateData;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetailsTrailersLoader extends Loader<JSONObject> {

    private final RequestQueue mRequestQueue;
    private int mMovieId;

    public MovieDetailsTrailersLoader(Context context, int id) {
        super(context);
        mMovieId = id;
        mRequestQueue = Volley.newRequestQueue(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        makeRequest();
    }

    private void makeRequest() {
        String url = getContext().getResources().getString(R.string.API_BASE_URL) +
                getContext().getResources().getString(R.string.API_MOVIE_ENDPOINT) + mMovieId +
                getContext().getResources().getString(R.string.API_TRAILERS_ENDPOINT) +
                getContext().getResources().getString(R.string.API_KEY_PARAM) +
                PrivateData.THE_MOVIE_DB_API_KEY;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        deliverResult(jsonObject);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Error control
            }
        });
        mRequestQueue.add(stringRequest);

    }

    @Override
    public void deliverResult(JSONObject data) {
        super.deliverResult(data);
    }
}
