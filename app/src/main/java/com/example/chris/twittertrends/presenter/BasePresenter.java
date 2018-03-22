package com.example.chris.twittertrends.presenter;

import android.support.annotation.CallSuper;
import android.support.annotation.StringRes;

import java.lang.ref.WeakReference;

/**
 * Created by Chris on 3/22/18.
 */

public class BasePresenter<VIEW extends BaseView> {
    protected WeakReference<VIEW> view;

    @CallSuper
    public void setView(VIEW view) {
        this.view = new WeakReference<>(view);
    }
}
