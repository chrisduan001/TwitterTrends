package com.example.chris.twittertrends.di.components;

import android.content.Context;

import com.example.chris.twittertrends.di.modules.ApplicationModule;
import com.example.chris.twittertrends.di.modules.NetworkModule;
import com.example.chris.twittertrends.network.ServiceHelper;
import com.example.chris.twittertrends.util.AppSharedPreference;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Chris on 3/21/18.
 */
@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {

    ServiceHelper serviceHelper();
    Context context();
    AppSharedPreference sharedPreference();
}
