package com.example.josnar.popularmovies.network;


import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.josnar.popularmovies.R;
import com.example.josnar.popularmovies.data.PrivateData;
import com.example.josnar.popularmovies.model.FavouriteFilmsContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FavouritesLoader {
    private static final String TAG = FavouritesLoader.class.getSimpleName();

    private JSONObject mJson;
    private Context mContext;

    public FavouritesLoader(Context context) {
        mContext = context;
    }

    public void load(FavouritesLoaderCallback callback) {
        List<Integer> movieIdList = getAllMovieIds();
        buildJsonAndDeliver(movieIdList, callback);
    }

    public List<Integer> getAllMovieIds() {
        Cursor cursor = null;
        try {
            cursor = mContext.getContentResolver().query(FavouriteFilmsContract.FavouriteFilmsEntry.CONTENT_URI,
                    null,
                    null,
                    null,
                    null);
        } catch (Exception e) {
            Log.e(TAG, "Failed to asynchronously load data.");
            e.printStackTrace();
        }
        List<Integer> movieIdList = new ArrayList<>();
        cursor.moveToFirst();
        do {
            int movieId = cursor.getInt(
                    cursor.getColumnIndex(FavouriteFilmsContract.FavouriteFilmsEntry.COLUMN_ID));
            movieIdList.add(movieId);
        }  while (cursor.moveToNext());
        return movieIdList;
    }

    private void buildJsonAndDeliver(List<Integer> movieIdList, final FavouritesLoaderCallback callback) {
        final int movieIdListLen = movieIdList.size();
        mJson = new JSONObject();
        final JSONArray resultsJson = new JSONArray();
        try {
            mJson.put("results", resultsJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        for (Integer id: movieIdList) {
            String url = mContext.getResources().getString(R.string.API_BASE_URL) +
                    mContext.getResources().getString(R.string.API_MOVIE_ENDPOINT) + id +
                    mContext.getResources().getString(R.string.API_KEY_PARAM) +
                            PrivateData.THE_MOVIE_DB_API_KEY;

            final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            JSONObject jsonEntry = new JSONObject();
                            try {
                                jsonEntry.put("id", jsonObject.getInt("id"));
                                jsonEntry.put("poster_path", jsonObject.getString("poster_path"));
                                jsonEntry.put("original_title", jsonObject.getString("original_title"));
                                jsonEntry.put("release_date", jsonObject.getString("release_date"));
                                jsonEntry.put("vote_average", jsonObject.getString("vote_average"));
                                jsonEntry.put("overview", jsonObject.getString("overview"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            resultsJson.put(jsonEntry);
                            if (resultsJson.length() == movieIdListLen) {
                                callback.deliverResult(mJson);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO: Error control
                }
            });
            requestQueue.add(stringRequest);
        }
    }
}
