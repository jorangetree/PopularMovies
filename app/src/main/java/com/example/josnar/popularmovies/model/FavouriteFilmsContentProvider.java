package com.example.josnar.popularmovies.model;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.josnar.popularmovies.model.FavouriteFilmsContract;
import com.example.josnar.popularmovies.model.FavouriteFilmsDbHelper;

public class FavouriteFilmsContentProvider extends ContentProvider {

    private FavouriteFilmsDbHelper mDatabase;

    public static final int FAVOURITE_FILMS = 100;
    public static final int FAVOURITE_FILM_ID = 101;


    private static final UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(FavouriteFilmsContract.AUTHORITY,
                FavouriteFilmsContract.PATH_FAVOURITE_FILMS, FAVOURITE_FILMS);
        uriMatcher.addURI(FavouriteFilmsContract.AUTHORITY,
                FavouriteFilmsContract.PATH_FAVOURITE_FILMS + "/#", FAVOURITE_FILM_ID);
        return uriMatcher;
    }

    public FavouriteFilmsContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDatabase.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        int tasksDeleted;

        switch (match) {
            case FAVOURITE_FILM_ID:
                String id = uri.getPathSegments().get(1);
                tasksDeleted = db.delete(FavouriteFilmsContract.FavouriteFilmsEntry.TABLE_NAME,
                        "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (tasksDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return tasksDeleted;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mDatabase.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case FAVOURITE_FILMS:
                long id =
                        db.insert(FavouriteFilmsContract.FavouriteFilmsEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(
                            FavouriteFilmsContract.FavouriteFilmsEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public boolean onCreate() {
        mDatabase = new FavouriteFilmsDbHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mDatabase.getReadableDatabase();

        int match = sUriMatcher.match(uri);
        Cursor retCursor;
        switch (match) {
            case FAVOURITE_FILMS:
                retCursor =  db.query(FavouriteFilmsContract.FavouriteFilmsEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
