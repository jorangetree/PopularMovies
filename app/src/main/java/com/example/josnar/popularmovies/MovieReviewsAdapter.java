package com.example.josnar.popularmovies;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.josnar.popularmovies.model.ReviewItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MovieReviewsAdapter extends ArrayAdapter<ReviewItem> {

    public MovieReviewsAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void populateReviewList(JSONObject jsonData) {
        clear();
        JSONArray trailers;
        try {
            trailers = jsonData.getJSONArray("results");
            for (int i = 0; i < trailers.length(); i++) {
                ReviewItem item = new ReviewItem();
                item.author = trailers.getJSONObject(i).getString("author");
                item.content = trailers.getJSONObject(i).getString("content");
                add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReviewItem reviewItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.review_item, parent, false);
        }

        TextView reviewAuthorTextView = (TextView) convertView.findViewById(R.id.review_author_text_view);
        reviewAuthorTextView.setText(reviewItem.author);
        TextView reviewContentTextView = (TextView) convertView.findViewById(R.id.review_content_text_view);
        reviewContentTextView.setText(reviewItem.content);

        return convertView;
    }
}
