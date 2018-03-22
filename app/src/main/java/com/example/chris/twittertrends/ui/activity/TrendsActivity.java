package com.example.chris.twittertrends.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.example.chris.twittertrends.R;
import com.example.chris.twittertrends.di.HasComponent;
import com.example.chris.twittertrends.di.components.DaggerTrendsComponent;
import com.example.chris.twittertrends.di.components.TrendsComponent;
import com.example.chris.twittertrends.ui.activity.listener.TrendsActivityListener;
import com.example.chris.twittertrends.ui.fragment.TrendsFragment;
import com.example.chris.twittertrends.util.AppToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrendsActivity extends BaseActivity implements
        HasComponent<TrendsComponent>, TrendsActivityListener {
    private static final int REQUEST_LOCATION_PERMISSION_CODE = 100;

    @BindView(R.id.toolbar) AppToolbar toolbar;

    private TrendsComponent component;

    //Should only show the custom location permission dialog once
    private boolean showDialogOnce = false;
    //Onresume called when permission changed, need only check the permission once
    private boolean hasCheckedUserPermission = false;

    //region init
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic);

        ButterKnife.bind(this);

        initToolbar();

        replaceFragment(new TrendsFragment(), TrendsFragment.class.getSimpleName(), REPLACE_FRAGMENT);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!hasCheckedUserPermission) {
            hasCheckedUserPermission = true;
            checkLocationPermission();
        }
    }

    @Override
    protected void initInjector() {
        component = DaggerTrendsComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build();
    }

    @Override
    public TrendsComponent getComponent() {
        return component;
    }
    //endregion

    //region toolbar
    private void initToolbar() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        toolbar.showToolbarTitle(R.string.trends);
    }
    //endregion

    //region permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION_CODE: {
                TrendsContract trendsContract = getTrendsContractFragment();
                if (trendsContract == null) {
                    return;
                }

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    trendsContract.onLocationPermissionGranted();
                } else {
                    trendsContract.onLocationPermissionDenied();
                }
            }
        }
    }

    //Request trends based on user location if the permission was granted
    //If not, show the global trends
    private void checkLocationPermission() {
        TrendsContract trendsContract = getTrendsContractFragment();

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                if (!showDialogOnce) {
                    showCustomPermissionDialog();
                    showDialogOnce = true;
                }
            } else {
                requestLocationPermission();
            }
        } else {
            if (trendsContract != null) {
                trendsContract.onLocationPermissionGranted();
            }
        }
    }

    private @Nullable TrendsContract getTrendsContractFragment() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof TrendsContract) {
                return (TrendsContract) fragment;
            }
        }

        return null;
    }

    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_LOCATION_PERMISSION_CODE);
    }

    //Show custom request permission dialog, click request button will display system request permission
    //dialog, dismiss/cancel will deny the permission
    private void showCustomPermissionDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this, R.style.PermissionDialogTheme)
                .setTitle(R.string.location_permission_title)
                .setMessage(R.string.location_permission_message)
                .setPositiveButton(R.string.request, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestLocationPermission();
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .create();

        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                TrendsContract trendsContract = getTrendsContractFragment();
                if (trendsContract != null) {
                    trendsContract.onLocationPermissionDenied();
                }
            }
        });

        dialog.show();
    }
    //endregion

    //fragment call
    @Override
    public void onTrendsSelected(String name, String query) {
        startActivity(TweetsActivity.setBundle(this, name, query));
    }

    public interface TrendsContract {
        void onLocationPermissionGranted();
        void onLocationPermissionDenied();
    }
}
