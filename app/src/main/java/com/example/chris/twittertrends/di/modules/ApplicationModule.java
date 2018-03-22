package com.example.chris.twittertrends.di.modules;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.chris.twittertrends.TwitterTrendsApplication;
import com.example.chris.twittertrends.util.AppSharedPreference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Chris on 3/21/18.
 */

@Module
public class ApplicationModule {
    private final static String SP_NAME = "com.chris.twittertrends";
    private final TwitterTrendsApplication application;

    public ApplicationModule(TwitterTrendsApplication application) {
        this.application = application;
    }

    @Provides @Singleton
    public Context provideApplicationContext() {
        return application;
    }

    @Provides @Singleton
    public AppSharedPreference provideSharedPreference() {
        SharedPreferences sp = application.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);

        return new AppSharedPreference(sp);
    }
}
