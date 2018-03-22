package com.example.chris.twittertrends.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chris.twittertrends.R;
import com.example.chris.twittertrends.entities.StatusEntity;
import com.example.chris.twittertrends.entities.TweetsEntity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Chris on 3/22/18.
 */

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.TweetsVH> {
    private List<StatusEntity> tweetsList = new ArrayList<>();

    public void updateList(List<StatusEntity> tweets) {
        tweetsList = tweets;
    }

    @NonNull
    @Override
    public TweetsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tweet, parent, false);

        return new TweetsVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TweetsVH holder, int position) {
        final StatusEntity status = tweetsList.get(position);

        Picasso.get().load(status.user.profileBannerImage).into(holder.bannerImage);

        holder.nameLocationText.setText(String.format("%s, (%s)",
                status.user.userName, status.user.location));
        holder.userDescription.setText(status.user.description);
        holder.favCount.setText(String.format("Favourite count: %s", status.user.favouritesCount));
        holder.tweets.setText(status.text);
        holder.createAt.setText(status.createdAt);
    }

    @Override
    public int getItemCount() {
        return tweetsList.size();
    }

    static class TweetsVH extends RecyclerView.ViewHolder {
        @BindView(R.id.profile_banner_image)    ImageView bannerImage;
        @BindView(R.id.user_name_location_tv)   TextView nameLocationText;
        @BindView(R.id.user_desc_tv)            TextView userDescription;
        @BindView(R.id.fav_count_tv)            TextView favCount;
        @BindView(R.id.tweets_text)             TextView tweets;
        @BindView(R.id.create_at_tv)            TextView createAt;

        TweetsVH(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
