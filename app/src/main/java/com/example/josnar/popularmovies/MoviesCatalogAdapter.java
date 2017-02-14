package com.example.josnar.popularmovies;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.josnar.popularmovies.data.PrivateData;
import com.example.josnar.popularmovies.utilities.MovieItem;
import com.example.josnar.popularmovies.utilities.MoviesCatalogDataMock;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class MoviesCatalogAdapter extends RecyclerView.Adapter<MoviesCatalogAdapter.MoviesCatalogAdapterViewHolder> {
    private Context mContext;
    private List<MovieItem> mMovieItemList;
    private String mBaseImageURL;

    MoviesCatalogAdapter(Context context) {
        mContext = context;
        // TODO: Provisional. Code above this line must be removed and call the configuration endpoint of the API
        mBaseImageURL = "http://image.tmdb.org/t/p/w500";
    }

    private void populateMoviesCatalog(JSONObject jsonData) {
        mMovieItemList = new ArrayList<>();
        JSONArray movies;
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

    void populate(final MovieCatalogActivity.TYPE_ORDER type_order) {
        new AsyncTask<Void, Void, String>() {

            String API_BASE_URL = "http://api.themoviedb.org/3";
            String API_TOP_RATED_ENDPOINT = "/movie/top_rated";
            String API_POPULAR_ENDPOINT = "/movie/popular";
            String API_KEY_PARAM = "?api_key=";

            @Override
            protected String doInBackground(Void... params) {
                RequestQueue queue = Volley.newRequestQueue(mContext);
                String endpoint = API_POPULAR_ENDPOINT;
                if (type_order == MovieCatalogActivity.TYPE_ORDER.TOP_RATED)
                    endpoint = API_TOP_RATED_ENDPOINT;

                String url = API_BASE_URL + endpoint + API_KEY_PARAM + PrivateData.THE_MOVIE_DB_API_KEY;

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                populateMoviesCatalog(jsonObject);
                                notifyDataSetChanged();
                            }
                    }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Error control
                    }
                });
                // Add the request to the RequestQueue.
                queue.add(stringRequest);
                return null;
            }
        }.execute();
    }

    class MoviesCatalogAdapterViewHolder extends RecyclerView.ViewHolder {
        ImageView mMovieImageView;

        MoviesCatalogAdapterViewHolder(View itemView) {
            super(itemView);
            mMovieImageView = (ImageView) itemView.findViewById(R.id.movie_image_view);
        }
    }
}
