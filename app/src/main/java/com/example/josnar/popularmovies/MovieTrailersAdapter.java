package com.example.josnar.popularmovies;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.josnar.popularmovies.utilities.TrailerItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MovieTrailersAdapter extends ArrayAdapter<TrailerItem> implements AdapterView.OnItemClickListener {

    public MovieTrailersAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void populateTrailerList(JSONObject jsonData) {
        clear();
        JSONArray trailers;
        try {
            trailers = jsonData.getJSONArray("results");
            for (int i = 0; i < trailers.length(); i++) {
                TrailerItem item = new TrailerItem();
                item.key = trailers.getJSONObject(i).getString("key");
                item.site = trailers.getJSONObject(i).getString("site");
                item.title = trailers.getJSONObject(i).getString("name");
                add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TrailerItem trailerItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trailer_item, parent, false);
        }

        TextView trailerTitleTextView = (TextView) convertView.findViewById(R.id.trailer_title_text_view);
        trailerTitleTextView.setText(trailerItem.title);
        return convertView;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String videoId = getItem(position).key;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://youtube.com/watch?v=" + videoId));
        getContext().startActivity(intent);
    }
}
