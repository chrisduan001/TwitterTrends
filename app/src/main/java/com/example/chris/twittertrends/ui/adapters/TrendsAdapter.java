package com.example.chris.twittertrends.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chris.twittertrends.R;
import com.example.chris.twittertrends.entities.TrendsEntity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Chris on 3/21/18.
 */

public class TrendsAdapter extends RecyclerView.Adapter<TrendsAdapter.TrendsVH> {
    private final WeakReference<TrendsAdapterListener> listener;

    private List<TrendsEntity> trends = new ArrayList<>();

    public TrendsAdapter(TrendsAdapterListener listener) {
        this.listener = new WeakReference<>(listener);
    }

    public void updateList(List<TrendsEntity> trends) {
        this.trends = trends;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrendsVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trends, parent, false);

        return new TrendsVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendsVH holder, int position) {
        final TrendsEntity trend = trends.get(position);

        holder.hashTagName.setText(trend.trendsName);
        holder.tweetVolume.setText(String.format("Volume: %s", trend.tweetVolume));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.get().onTrendsClicked(trend);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trends.size();
    }

    static class TrendsVH extends RecyclerView.ViewHolder {
        @BindView(R.id.name_tv)     TextView hashTagName;
        @BindView(R.id.volume_tv)   TextView tweetVolume;

        TrendsVH(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public interface TrendsAdapterListener {
        void onTrendsClicked(TrendsEntity trends);
    }
}
