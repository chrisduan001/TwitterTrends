package com.example.chris.twittertrends.interactor;

import com.example.chris.twittertrends.entities.StatusEntity;
import com.example.chris.twittertrends.entities.TweetsEntity;
import com.example.chris.twittertrends.network.ServiceHelper;
import com.example.chris.twittertrends.util.AppSharedPreference;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by Chris on 3/22/18.
 */

public class TweetsInteractor extends BaseInteractor<TweetsEntity, TweetsInteractor.TweetsCallback> {
    private String query;

    @Inject
    public TweetsInteractor(ServiceHelper serviceHelper, AppSharedPreference sharedPreference) {
        super(serviceHelper, sharedPreference);
    }

    public void getTweets(String query) {
        this.query = query;

        makeNetworkCall();
    }

    @Override
    protected Single<TweetsEntity> getNetworkCall() {
        return serviceHelper.getTweets(query);
    }

    @Override
    protected void processNetworkResponse(TweetsEntity tweetsEntity) {
        callback.onGetTweets(tweetsEntity.statuses);
    }

    public interface TweetsCallback extends BaseCallback {
        void onGetTweets(List<StatusEntity> tweets);
    }
}
