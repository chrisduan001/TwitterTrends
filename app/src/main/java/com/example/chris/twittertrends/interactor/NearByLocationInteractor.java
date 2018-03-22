package com.example.chris.twittertrends.interactor;

import com.example.chris.twittertrends.R;
import com.example.chris.twittertrends.entities.LocationEntity;
import com.example.chris.twittertrends.network.ServiceHelper;
import com.example.chris.twittertrends.util.AppSharedPreference;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by Chris on 3/22/18.
 */

public class NearByLocationInteractor
        extends BaseInteractor<List<LocationEntity>, NearByLocationInteractor.NearByLocationCallback> {
    //temporary hold the value for make network request
    private String lat, longi;

    @Inject
    public NearByLocationInteractor(ServiceHelper serviceHelper, AppSharedPreference sharedPreference) {
        super(serviceHelper, sharedPreference);
    }

    public void getNearbyLocation(String lat, String longi) {
        this.lat = lat;
        this.longi = longi;
        makeNetworkCall();
    }

    @Override
    protected Single<List<LocationEntity>> getNetworkCall() {
        return serviceHelper.getClosestLocation(lat, longi);
    }

    @Override
    protected void processNetworkResponse(List<LocationEntity> locationEntities) {
        if (locationEntities == null || locationEntities.size() < 1) {
            callback.onError(R.string.error_no_location);
        } else {
            callback.onGetLocationInfo(locationEntities.get(0));
        }
    }

    public interface NearByLocationCallback extends BaseCallback {
        void onGetLocationInfo(LocationEntity locationEntity);
    }
}
