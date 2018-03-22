package com.example.chris.twittertrends.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chris.twittertrends.R;
import com.example.chris.twittertrends.di.components.TweetsComponent;
import com.example.chris.twittertrends.ui.activity.TweetsActivity;
import com.example.chris.twittertrends.ui.adapters.TweetsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Chris on 3/22/18.
 */

public class TweetsFragment extends BaseFragment {
    public static TweetsFragment newInstance(String searchQuery) {
        TweetsFragment fragment = new TweetsFragment();

        Bundle bundle = new Bundle();
        bundle.putString(TweetsActivity.EXTRA_SEARCH_QUERY, searchQuery);

        fragment.setArguments(bundle);

        return fragment;
    }

    @BindView(R.id.recyclerview) RecyclerView recyclerView;

    private TweetsAdapter adapter;

    //region init
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweets, container, false);

        ButterKnife.bind(this, view);

        initInjector();

        setupRecycler();

        return view;
    }

    private void initInjector() {
        getComponent(TweetsComponent.class).inject(this);
    }

    private void setupRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TweetsAdapter();

        recyclerView.setAdapter(adapter);
    }
    //endregion
}
