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
import com.example.chris.twittertrends.entities.StatusEntity;
import com.example.chris.twittertrends.presenter.TweetsPresenter;
import com.example.chris.twittertrends.ui.activity.TweetsActivity;
import com.example.chris.twittertrends.ui.adapters.TweetsAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Chris on 3/22/18.
 */

public class TweetsFragment extends BaseFragment implements TweetsPresenter.TweetsView {
    public static TweetsFragment newInstance(String searchQuery) {
        TweetsFragment fragment = new TweetsFragment();

        Bundle bundle = new Bundle();
        bundle.putString(TweetsActivity.EXTRA_SEARCH_QUERY, searchQuery);

        fragment.setArguments(bundle);

        return fragment;
    }

    @BindView(R.id.recyclerview) RecyclerView recyclerView;

    @Inject TweetsPresenter presenter;

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

    @Override
    public void onResume() {
        super.onResume();

        presenter.getTweets(getArguments().getString(TweetsActivity.EXTRA_SEARCH_QUERY, ""));
    }

    @Override
    public void onPause() {
        super.onPause();

        presenter.onPause();
    }

    private void initInjector() {
        getComponent(TweetsComponent.class).inject(this);
        presenter.setView(this);
    }

    private void setupRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TweetsAdapter();

        recyclerView.setAdapter(adapter);
    }
    //endregion

    //region presenter callback
    @Override
    public void onError(int error) {
        showToast(error);
    }

    @Override
    public void showProgress() {
        showProgressDialog();
    }

    @Override
    public void dismissProgres() {
        dismissProgressDialog();
    }

    @Override
    public void onGetTweetStatus(List<StatusEntity> status) {
        adapter.updateList(status);
    }
    //endregion
}
