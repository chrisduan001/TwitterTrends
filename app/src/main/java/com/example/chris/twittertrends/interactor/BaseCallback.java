package com.example.chris.twittertrends.interactor;

import android.support.annotation.StringRes;

/**
 * Created by Chris on 3/22/18.
 */

public interface BaseCallback {
    void onError(@StringRes int error);
}
