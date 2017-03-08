package com.example.josnar.popularmovies.network;

import android.content.Context;
import android.support.v4.content.Loader;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.josnar.popularmovies.MovieCatalogActivity;
import com.example.josnar.popularmovies.R;
import com.example.josnar.popularmovies.data.PrivateData;

import org.json.JSONException;
import org.json.JSONObject;


public class MovieCatalogLoader extends Loader<JSONObject> {
    private MovieCatalogActivity.TYPE_ORDER mTypeOrder;
    private RequestQueue mRequestQueue;

    public MovieCatalogLoader(Context context, MovieCatalogActivity.TYPE_ORDER typeOrder) {
        super(context);
        mTypeOrder = typeOrder;

        mRequestQueue = Volley.newRequestQueue(context);
    }

    private void makeRequest() {
        String endpoint = getContext().getResources().getString(R.string.API_POPULAR_ENDPOINT);
        if (mTypeOrder == MovieCatalogActivity.TYPE_ORDER.TOP_RATED)
            endpoint = getContext().getResources().getString(R.string.API_TOP_RATED_ENDPOINT);

        String url = getContext().getResources().getString(R.string.API_BASE_URL) + endpoint +
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

            }
        });
        mRequestQueue.add(stringRequest);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        if (mTypeOrder == MovieCatalogActivity.TYPE_ORDER.FAVOURITES) {
            showFavourites();
        }
        else {
            makeRequest();
        }
    }

    @Override
    public void deliverResult(JSONObject data) {
        super.deliverResult(data);
    }

    public void showFavourites() {
        FavouritesLoader favouritesLoader = new FavouritesLoader(getContext());
        favouritesLoader.load(new FavouritesLoaderOnFinishedCallback() {
            @Override
            public void deliverResult(JSONObject jsonObject) {
                MovieCatalogLoader.this.deliverResult(jsonObject);
            }
        });
    }
}
