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

    private RecyclerView mRecyclerView;
    private MoviesCatalogAdapter mMoviesCatalogAdapter;

    enum  TYPE_ORDER { TOP_RATED, MOST_POPULAR }

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
        mMoviesCatalogAdapter.populate(TYPE_ORDER.TOP_RATED);
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
            mMoviesCatalogAdapter.populate(TYPE_ORDER.TOP_RATED);
        else
            mMoviesCatalogAdapter.populate(TYPE_ORDER.MOST_POPULAR);
        return super.onOptionsItemSelected(item);
    }
}
