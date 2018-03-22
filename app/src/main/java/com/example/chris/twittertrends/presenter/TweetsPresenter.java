package com.example.chris.twittertrends.presenter;

import com.example.chris.twittertrends.entities.StatusEntity;
import com.example.chris.twittertrends.interactor.TweetsInteractor;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Chris on 3/22/18.
 */

public class TweetsPresenter extends BasePresenter<TweetsPresenter.TweetsView> implements
        TweetsInteractor.TweetsCallback{

    private final TweetsInteractor interactor;

    @Inject
    public TweetsPresenter(TweetsInteractor interactor) {
        this.interactor = interactor;

        this.interactor.call(this);
    }

    public void getTweets(String query) {
        view.get().showProgress();
        interactor.getTweets(query);
    }

    public void onPause() {
        interactor.disposeRequest();
    }

    //region interactor callback
    @Override
    public void onError(int error) {
        view.get().onError(error);
        view.get().dismissProgres();
    }

    @Override
    public void onGetTweets(List<StatusEntity> tweets) {
        view.get().onGetTweetStatus(tweets);
        view.get().dismissProgres();
    }
    //endregion

    public interface TweetsView extends BaseView {
        void onGetTweetStatus(List<StatusEntity> status);
    }
}
