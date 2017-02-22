package com.example.josnar.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.josnar.popularmovies.utilities.MovieItem;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

class MoviesCatalogAdapter extends RecyclerView.Adapter<MoviesCatalogAdapter.MoviesCatalogAdapterViewHolder>  {
    private Context mContext;
    private List<MovieItem> mMovieItemList;
    static String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500";

    MoviesCatalogAdapter(Context context) {
        mContext = context;
    }

    public void populateMoviesCatalog(JSONObject jsonData) {
        mMovieItemList = new ArrayList<>();
        JSONArray movies;
        try {
            movies = jsonData.getJSONArray("results");
            for (int i = 0; i < movies.length(); i++) {
                MovieItem item = new MovieItem();
                item.id = movies.getJSONObject(i).getInt("id");
                item.originalTitle = movies.getJSONObject(i).getString("original_title");
                item.posterPath = movies.getJSONObject(i).getString("poster_path");
                item.releaseDate = movies.getJSONObject(i).getString("release_date");
                item.voteAverage = movies.getJSONObject(i).getDouble("vote_average");
                item.plotSynopsis = movies.getJSONObject(i).getString("overview");
                mMovieItemList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public MoviesCatalogAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movies_catalog_item, parent, false);

        return new MoviesCatalogAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MoviesCatalogAdapterViewHolder holder, int position) {
        Picasso.with(mContext).load(BASE_IMAGE_URL + mMovieItemList.get(position).posterPath).into(holder.mMovieImageView);
        holder.mMovieImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MovieDetailsActivity.class);
                intent.putExtra("movie_details", mMovieItemList.get(holder.getAdapterPosition()));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        if (null == mMovieItemList) return 0;
        return mMovieItemList.size();
    }

    class MoviesCatalogAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView mMovieImageView;

        MoviesCatalogAdapterViewHolder(View itemView) {
            super(itemView);
            mMovieImageView = (ImageView) itemView.findViewById(R.id.movie_image_view);
        }
    }

}
