package com.example.chris.twittertrends.ui.activity;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.chris.twittertrends.R;
import com.example.chris.twittertrends.TwitterTrendsApplication;
import com.example.chris.twittertrends.di.components.ApplicationComponent;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Chris on 3/21/18.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected static final int REPLACE_FRAGMENT = 0;
    protected static final int ADD_BACKSTACK = 1;
    @IntDef({REPLACE_FRAGMENT, ADD_BACKSTACK})
    @Retention(RetentionPolicy.SOURCE)
    protected @interface FrgTransactionType{}

    @Override
    @CallSuper
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initInjector();
    }

    //region di
    protected ApplicationComponent getApplicationComponent() {
        return ((TwitterTrendsApplication)getApplication()).getAppComponent();
    }
    //endregion

    protected abstract void initInjector();

    protected void replaceFragment(Fragment fragment, @Nullable String tag,
                                   @FrgTransactionType int type) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();

        transaction.replace(R.id.container, fragment, tag);
        if (type == ADD_BACKSTACK) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }
}
