package com.example.josnar.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MovieCatalogActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MoviesCatalogAdapter mMoviesCatalogAdapter;


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
        mMoviesCatalogAdapter.populate();
    }
}
