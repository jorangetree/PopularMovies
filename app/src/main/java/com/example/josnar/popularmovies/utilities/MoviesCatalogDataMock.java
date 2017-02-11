package com.example.josnar.popularmovies.utilities;

import android.content.Context;

import com.example.josnar.popularmovies.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


public class MoviesCatalogDataMock {

    Context mContext;

    public MoviesCatalogDataMock(Context context) {
        mContext = context;
    }

    public JSONObject getMovies() {
        String jsonText = null;
        InputStream inputStream = null;
        try {
            inputStream = mContext.getAssets().open("catalog_mock.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonText = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonText);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
