package com.example.chris.twittertrends.ui.fragment;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chris.twittertrends.R;
import com.example.chris.twittertrends.di.components.TrendsComponent;
import com.example.chris.twittertrends.entities.TrendsEntity;
import com.example.chris.twittertrends.presenter.TrendsPresenter;
import com.example.chris.twittertrends.ui.activity.TrendsActivity;
import com.example.chris.twittertrends.ui.adapters.TrendsAdapter;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/**
 * Created by Chris on 3/21/18.
 */

public class TrendsFragment extends BaseFragment implements
        TrendsActivity.TrendsContract, TrendsAdapter.TrendsAdapterListener,
        TrendsPresenter.TrendsView{

    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    @BindView(R.id.location_tv) TextView locationText;

    private TrendsAdapter adapter;

    @Inject TrendsPresenter presenter;

    //region init
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trends, container, false);

        ButterKnife.bind(this, view);

        initInjector();

        setupRecycler();

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

        presenter.onpause();
    }

    private void initInjector() {
        getComponent(TrendsComponent.class).inject(this);

        presenter.setView(this);
    }

    private void setupRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TrendsAdapter(this);

        recyclerView.setAdapter(adapter);
    }
    //endregion

    //region contract
    @Override
    public void onLocationPermissionGranted() {
        showProgress();
        //Get device location and request twitter trends
        getDeviceLocation();
    }

    @Override
    public void onLocationPermissionDenied() {
        //Get global trends and show toast
        showToast(R.string.permission_deny);
        presenter.getGlobalTrends();
    }
    //endregion

    //region presenter callback
    @Override
    public void setLocationInfo(String locationInfo) {
        locationText.setText(locationInfo);
    }

    @Override
    public void setTrendsList(List<TrendsEntity> trends) {
        adapter.updateList(trends);
    }

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
    //endregion

    @Override
    public void onTrendsClicked(TrendsEntity trends) {

    }

    //location
    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {
        @SuppressWarnings("ConstantConditions")
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(getActivity());

        client.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            presenter.getCloeseLocation(location.getLatitude(), location.getLongitude());
                        } else {
                            showToast(R.string.get_location_failed);
                            presenter.getGlobalTrends();
                        }
                    }
                });
    }
}
