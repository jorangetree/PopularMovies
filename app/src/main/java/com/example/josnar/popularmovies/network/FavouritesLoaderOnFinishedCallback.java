package com.example.josnar.popularmovies.network;


import org.json.JSONObject;

public interface FavouritesLoaderOnFinishedCallback {
    void deliverResult(JSONObject jsonObject);
}
