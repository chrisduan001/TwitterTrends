package com.example.chris.twittertrends.di.components;

import com.example.chris.twittertrends.di.PerActivity;
import com.example.chris.twittertrends.ui.fragment.TrendsFragment;

import dagger.Component;

/**
 * Created by Chris on 3/21/18.
 */
@PerActivity
@Component(dependencies = {ApplicationComponent.class})
public interface TrendsComponent {
    void inject(TrendsFragment fragment);
}
