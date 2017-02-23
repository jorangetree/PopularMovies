package com.example.josnar.popularmovies;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.josnar.popularmovies.network.MovieDetailsTrailersLoader;
import com.example.josnar.popularmovies.utilities.MovieItem;
import com.github.paolorotolo.expandableheightlistview.ExpandableHeightListView;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MovieDetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject> {

    private static final int TRAILER_LIST_LOADER = 0;

    private MovieItem mMovieItem;
    private MovieTrailersAdapter mMovieTrailersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intent = getIntent();
        mMovieItem = (MovieItem) intent.getSerializableExtra("movie_details");

        TextView titleTextView = (TextView) findViewById(R.id.movie_details_title_text_view);
        titleTextView.setText(mMovieItem.originalTitle);

        ImageView posterImageView = (ImageView) findViewById(R.id.movie_details_poster_image_view);
        Picasso.with(this).load(MoviesCatalogAdapter.BASE_IMAGE_URL + mMovieItem.posterPath).into(posterImageView);

        TextView releaseDateTextView = (TextView) findViewById(R.id.movie_details_release_date_text_view);
        releaseDateTextView.setText(mMovieItem.releaseDate);

        TextView averageRateTextView = (TextView) findViewById(R.id.movie_details_average_rate_text_view);
        averageRateTextView.setText(mMovieItem.voteAverage.toString());

        TextView plotSynopsisTextView = (TextView) findViewById(R.id.movie_details_plot_synopsis_text_view);
        plotSynopsisTextView.setText(mMovieItem.plotSynopsis);

        mMovieTrailersAdapter = new MovieTrailersAdapter(this, R.layout.trailer_item);
        ExpandableHeightListView trailerListView = (ExpandableHeightListView) findViewById(R.id.trailer_list_view);
        trailerListView.setAdapter(mMovieTrailersAdapter);
        trailerListView.setExpanded(true);
        trailerListView.setOnItemClickListener(mMovieTrailersAdapter);

        getSupportLoaderManager().initLoader(TRAILER_LIST_LOADER, null, this);
    }

    @Override
    public Loader<JSONObject> onCreateLoader(int id, Bundle args) {
        return new MovieDetailsTrailersLoader(this, mMovieItem.id);
    }

    @Override
    public void onLoadFinished(Loader<JSONObject> loader, JSONObject data) {
        mMovieTrailersAdapter.populateTrailerList(data);
        mMovieTrailersAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<JSONObject> loader) {

    }
}
