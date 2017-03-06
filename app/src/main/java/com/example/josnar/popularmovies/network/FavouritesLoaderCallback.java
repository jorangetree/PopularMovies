package com.example.josnar.popularmovies.network;


import org.json.JSONObject;

public interface FavouritesLoaderCallback {
    void deliverResult(JSONObject jsonObject);
}
