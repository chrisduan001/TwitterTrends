package com.example.chris.twittertrends.interactor;

import com.example.chris.twittertrends.R;
import com.example.chris.twittertrends.entities.PlaceEntity;
import com.example.chris.twittertrends.entities.TrendsEntity;
import com.example.chris.twittertrends.network.ServiceHelper;
import com.example.chris.twittertrends.util.AppSharedPreference;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by Chris on 3/22/18.
 */

public class TrendsInteractor extends BaseInteractor<List<PlaceEntity>, TrendsInteractor.TrendsCallback> {
    private String woeid;

    @Inject
    public TrendsInteractor(ServiceHelper serviceHelper, AppSharedPreference sharedPreference) {
        super(serviceHelper, sharedPreference);
    }

    public void getLocationTrends(String woeid) {
        this.woeid = woeid;
        makeNetworkCall();
    }

    @Override
    protected Single<List<PlaceEntity>> getNetworkCall() {
        return serviceHelper.getTrends(woeid);
    }

    @Override
    protected void processNetworkResponse(List<PlaceEntity> placeEntities) {
        if (placeEntities == null || placeEntities.size() < 1) {
            callback.onError(R.string.error_no_trends);

            return;
        }

        callback.onGetTrends(placeEntities.get(0).trendsList);
    }

    public interface TrendsCallback extends BaseCallback {
        void onGetTrends(List<TrendsEntity> trends);
    }
}
