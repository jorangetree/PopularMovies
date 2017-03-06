package com.example.josnar.popularmovies.model;


import android.net.Uri;
import android.provider.BaseColumns;

public class FavouriteFilmsContract {

    public static final String AUTHORITY = "com.example.josnar.favouritefilmscontentprovider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_FAVOURITE_FILMS = "favourite_films";

    private FavouriteFilmsContract() {
    }

    public static class FavouriteFilmsEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITE_FILMS).build();
        public static final String TABLE_NAME = "favourite_films";
        public static final String COLUMN_ID = "_id";
    }
}
