package com.example.chris.twittertrends.di.modules;

import android.content.Context;

import com.example.chris.twittertrends.TwitterTrendsApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chris on 3/21/18.
 */

@Module
public class ApplicationModule {
    private final TwitterTrendsApplication application;

    public ApplicationModule(TwitterTrendsApplication application) {
        this.application = application;
    }

    @Provides @Singleton
    public Context provideApplicationContext() {
        return application;
    }
}
