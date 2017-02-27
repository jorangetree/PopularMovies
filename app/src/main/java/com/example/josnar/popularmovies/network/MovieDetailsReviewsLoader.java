package com.example.josnar.popularmovies.network;


import android.content.Context;
import android.support.v4.content.Loader;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.josnar.popularmovies.data.PrivateData;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieDetailsReviewsLoader extends Loader<JSONObject> {
    private final int mMovieId;

    static private String API_BASE_URL = "http://api.themoviedb.org/3";
    static private String API_MOVIE_ENDPOINT = "/movie/";
    static private String API_REVIEWS_ENDPOINT = "/reviews";
    static private String API_KEY_PARAM = "?api_key=";
    private final RequestQueue mRequestQueue;

    public MovieDetailsReviewsLoader(Context context, int id) {
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
        String url = API_BASE_URL + API_MOVIE_ENDPOINT + mMovieId + API_REVIEWS_ENDPOINT + API_KEY_PARAM + PrivateData.THE_MOVIE_DB_API_KEY;

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
