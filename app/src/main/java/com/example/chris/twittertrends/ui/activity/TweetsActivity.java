package com.example.chris.twittertrends.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.example.chris.twittertrends.R;
import com.example.chris.twittertrends.di.HasComponent;
import com.example.chris.twittertrends.di.components.DaggerTweetsComponent;
import com.example.chris.twittertrends.di.components.TweetsComponent;
import com.example.chris.twittertrends.ui.fragment.TweetsFragment;
import com.example.chris.twittertrends.util.AppToolbar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Chris on 3/22/18.
 */

public class TweetsActivity extends BaseActivity implements HasComponent<TweetsComponent> {

    private static final String EXTRA_HASHTAG_TITLE = "HASH_TAG_TITLE";
    public static final String EXTRA_SEARCH_QUERY = "SEARCH_QUERY";
    public static Intent setBundle(Context context, String hashTagTitle, String query) {
        Intent intent = new Intent(context, TweetsActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_HASHTAG_TITLE, hashTagTitle);
        bundle.putString(EXTRA_SEARCH_QUERY, query);
        intent.putExtras(bundle);

        return intent;
    }

    @BindView(R.id.toolbar) AppToolbar toolbar;

    private TweetsComponent component;

    //region init
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_generic);
        ButterKnife.bind(this);

        initToolbar();

        replaceFragment(TweetsFragment.newInstance(
                getExtraQuery()),
                TweetsFragment.class.getSimpleName(),
                REPLACE_FRAGMENT);
    }

    @Override
    protected void initInjector() {
        component = DaggerTweetsComponent.builder()
                .applicationComponent(getApplicationComponent())
                .build();
    }

    @Override
    public TweetsComponent getComponent() {
        return component;
    }
    //endregion

    //region toolbar
    private void initToolbar() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        toolbar.showToolbarTitle(getToolbarTitle());

        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.back_button));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private String getToolbarTitle() {
        Bundle bundle = getIntent().getExtras();

        return bundle == null ? "" : bundle.getString(EXTRA_HASHTAG_TITLE);
    }

    private String getExtraQuery() {
        Bundle bundle = getIntent().getExtras();

        return bundle == null ? "" : bundle.getString(EXTRA_SEARCH_QUERY);
    }
    //endregion
}
