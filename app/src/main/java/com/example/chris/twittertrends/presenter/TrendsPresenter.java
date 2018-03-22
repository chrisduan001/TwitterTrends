package com.example.chris.twittertrends.presenter;

import android.support.annotation.StringRes;

import com.example.chris.twittertrends.entities.LocationEntity;
import com.example.chris.twittertrends.entities.TrendsEntity;
import com.example.chris.twittertrends.interactor.NearByLocationInteractor;
import com.example.chris.twittertrends.interactor.TrendsInteractor;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Chris on 3/22/18.
 */

public class TrendsPresenter extends BasePresenter<TrendsPresenter.TrendsView> implements
        NearByLocationInteractor.NearByLocationCallback,
        TrendsInteractor.TrendsCallback{

    private final NearByLocationInteractor locationInteractor;
    private final TrendsInteractor trendsInteractor;

    @Inject
    public TrendsPresenter(NearByLocationInteractor locationInteractor,
                           TrendsInteractor trendsInteractor) {

        this.locationInteractor = locationInteractor;
        this.trendsInteractor = trendsInteractor;

        this.locationInteractor.call(this);
        this.trendsInteractor.call(this);
    }

    public void getCloeseLocation(double lat, double longi) {
        view.get().showProgress();
        locationInteractor.getNearbyLocation(lat + "", longi + "");
    }

    //call when unable to get user location
    public void getGlobalTrends() {
        view.get().setLocationInfo("Global");
        trendsInteractor.getLocationTrends("1");
    }

    public void onpause() {
        trendsInteractor.disposeRequest();
        locationInteractor.disposeRequest();
    }

    //region interactor callback
    @Override
    public void onGetLocationInfo(LocationEntity locationEntity) {
        view.get().setLocationInfo(String.format("%s, %s", locationEntity.name, locationEntity.country));
        trendsInteractor.getLocationTrends(locationEntity.woeid);
    }

    @Override
    public void onGetTrends(List<TrendsEntity> trends) {
        view.get().dismissProgres();
        view.get().setTrendsList(trends);
    }

    @Override
    public void onError(@StringRes int error) {
        view.get().onError(error);
        view.get().dismissProgres();
    }
    //endregion

    public interface TrendsView extends BaseView {
        void setLocationInfo(String locationInfo);
        void setTrendsList(List<TrendsEntity> trends);
    }
}
