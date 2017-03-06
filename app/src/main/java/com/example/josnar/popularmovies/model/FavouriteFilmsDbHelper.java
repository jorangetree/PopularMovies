package com.example.josnar.popularmovies.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class FavouriteFilmsDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "popularmovies.db";
    private static final int DATABASE_VERSION = 1;

    public FavouriteFilmsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FAVOURITE_FILMS_TABLE = "CREATE TABLE " +
                FavouriteFilmsContract.FavouriteFilmsEntry.TABLE_NAME + " (" +
                FavouriteFilmsContract.FavouriteFilmsEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT);";
        db.execSQL(SQL_CREATE_FAVOURITE_FILMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavouriteFilmsContract.FavouriteFilmsEntry.TABLE_NAME);
        onCreate(db);
    }
}
