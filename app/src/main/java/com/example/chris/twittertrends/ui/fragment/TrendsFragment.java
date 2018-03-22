package com.example.chris.twittertrends.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chris.twittertrends.R;
import com.example.chris.twittertrends.di.components.TrendsComponent;
import com.example.chris.twittertrends.ui.activity.TrendsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Chris on 3/21/18.
 */

public class TrendsFragment extends BaseFragment implements TrendsActivity.TrendsContract {

    @BindView(R.id.recyclerview) RecyclerView recyclerView;

    //region init
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trends, container, false);

        ButterKnife.bind(this, view);

        initInjector();

        return view;
    }

    private void initInjector() {
        getComponent(TrendsComponent.class).inject(this);
    }
    //endregion

    //region contract
    @Override
    public void onLocationPermissionGranted() {
        //Get device location and request twitter trends
        
    }

    @Override
    public void onLocationPermissionDenied() {
        //Get global trends and show toast
        showToast(R.string.permission_deny);
    }
    //endregion
}
