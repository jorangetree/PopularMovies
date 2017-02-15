package com.example.josnar.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MovieCatalogActivity extends AppCompatActivity {

    enum  TYPE_ORDER { TOP_RATED, MOST_POPULAR }

    private RecyclerView mRecyclerView;
    private MoviesCatalogAdapter mMoviesCatalogAdapter;
    private static TYPE_ORDER mOrder = TYPE_ORDER.TOP_RATED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_catalog);

        mRecyclerView = (RecyclerView) findViewById(R.id.reciclerview_moviescatalog);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mMoviesCatalogAdapter = new MoviesCatalogAdapter(this);
        mRecyclerView.setAdapter(mMoviesCatalogAdapter);
        mMoviesCatalogAdapter.populate(mOrder);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.top_rated)
            mOrder = TYPE_ORDER.TOP_RATED;
        else
            mOrder = TYPE_ORDER.MOST_POPULAR;

        mMoviesCatalogAdapter.populate(mOrder);
        return super.onOptionsItemSelected(item);
    }
}
