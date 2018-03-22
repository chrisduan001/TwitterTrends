package com.example.chris.twittertrends;

import android.app.Application;

import com.example.chris.twittertrends.di.components.ApplicationComponent;
import com.example.chris.twittertrends.di.components.DaggerApplicationComponent;
import com.example.chris.twittertrends.di.modules.ApplicationModule;

/**
 * Created by Chris on 3/21/18.
 */

public class TwitterTrendsApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        initializeInjector();
    }

    private void initializeInjector() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getAppComponent() {
        return applicationComponent;
    }
}
