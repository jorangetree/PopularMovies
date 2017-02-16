package com.example.josnar.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.josnar.popularmovies.utilities.MovieItem;
import com.squareup.picasso.Picasso;


public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Intent intent = getIntent();
        MovieItem movieItem = (MovieItem) intent.getSerializableExtra("movie_details");

        TextView titleTextView = (TextView) findViewById(R.id.movie_details_title_text_view);
        titleTextView.setText(movieItem.originalTitle);

        ImageView posterImageView = (ImageView) findViewById(R.id.movie_details_poster_image_view);
        Picasso.with(this).load(MoviesCatalogAdapter.BASE_IMAGE_URL + movieItem.posterPath).into(posterImageView);

        TextView releaseDateTextView = (TextView) findViewById(R.id.movie_details_release_date_text_view);
        releaseDateTextView.setText(movieItem.releaseDate);

        TextView averageRateTextView = (TextView) findViewById(R.id.movie_details_average_rate_text_view);
        averageRateTextView.setText(movieItem.voteAverage.toString());

        TextView plotSynopsisTextView = (TextView) findViewById(R.id.movie_details_plot_synopsis_text_view);
        plotSynopsisTextView.setText(movieItem.plotSynopsis);
    }
}
