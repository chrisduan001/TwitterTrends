package com.example.chris.twittertrends.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.chris.twittertrends.R;
import com.example.chris.twittertrends.di.HasComponent;
import com.example.chris.twittertrends.di.components.DaggerApplicationComponent;
import com.example.chris.twittertrends.di.components.DaggerTrendsComponent;
import com.example.chris.twittertrends.di.components.TrendsComponent;
import com.example.chris.twittertrends.ui.fragment.TrendsFragment;
import com.example.chris.twittertrends.util.AppToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrendsActivity extends BaseActivity implements HasComponent<TrendsComponent> {

    @BindView(R.id.toolbar) AppToolbar toolbar;

    private TrendsComponent component;

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
}
