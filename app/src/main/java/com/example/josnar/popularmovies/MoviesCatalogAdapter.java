package com.example.josnar.popularmovies;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.josnar.popularmovies.data.PrivateData;
import com.example.josnar.popularmovies.utilities.MovieItem;
import com.example.josnar.popularmovies.utilities.MoviesCatalogDataMock;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MoviesCatalogAdapter extends RecyclerView.Adapter<MoviesCatalogAdapter.MoviesCatalogAdapterViewHolder> {
    Context mContext;
    List<MovieItem> mMovieItemList;
    String mBaseImageURL;
    String API_BASE_URL = "http://api.themoviedb.org/3";
    String API_CONFIGURATION_ENDPOINT = "/configuration";
    String API_KEY_PARAM = "?api_key=";

    public MoviesCatalogAdapter() {
        // TODO: Provisional. Code above this line must be removed and use instead the code below
        mBaseImageURL = new String("http://image.tmdb.org/t/p/w500");
        // Some queries to the Movie Database need some default data that can be obtainer with the next lines
        // Mandatory to get the images
        // String configuratioURL = API_BASE_URL + API_CONFIGURATION_ENDPOINT + API_KEY_PARAM + PrivateData.THE_MOVIE_DB_API_KEY;
    }

    private void populateMoviesCatalog(JSONObject jsonData) {
        mMovieItemList = new ArrayList<MovieItem>();
        JSONArray movies = null;
        try {
            movies = jsonData.getJSONArray("results");
            for (int i=0; i<movies.length(); i++) {
                MovieItem item = new MovieItem();
                item.id = movies.getJSONObject(i).getInt("id");
                item.originalTitle = movies.getJSONObject(i).getString("original_title");
                item.posterPath = movies.getJSONObject(i).getString("poster_path");
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
    public void onBindViewHolder(MoviesCatalogAdapterViewHolder holder, int position) {
        Picasso.with(mContext).load(mBaseImageURL + mMovieItemList.get(position).posterPath).into(holder.mMovieImageView);
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

    public void populate(Context context) {
        if (null == mMovieItemList) {
            mContext = context;
            MoviesCatalogDataMock dataMock = new MoviesCatalogDataMock(mContext);
            populateMoviesCatalog(dataMock.getMovies());
        }
        notifyDataSetChanged();
    }

    public class MoviesCatalogAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView mMovieImageView;

        public MoviesCatalogAdapterViewHolder(View itemView) {
            super(itemView);
            mMovieImageView = (ImageView) itemView.findViewById(R.id.movie_image_view);
        }
    }
}
