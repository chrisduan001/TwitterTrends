package com.example.chris.twittertrends.presenter;

import android.support.annotation.StringRes;

/**
 * Created by Chris on 3/22/18.
 */

public interface BaseView {
    void onError(@StringRes int error);
    void showProgress();
    void dismissProgres();
}
