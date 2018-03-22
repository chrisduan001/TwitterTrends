package com.example.chris.twittertrends.di.components;

import com.example.chris.twittertrends.di.PerActivity;
import com.example.chris.twittertrends.ui.fragment.TweetsFragment;

import dagger.Component;

/**
 * Created by Chris on 3/22/18.
 */
@PerActivity
@Component(dependencies = {ApplicationComponent.class})
public interface TweetsComponent {
    void inject(TweetsFragment fragment);
}
