package com.example.chris.twittertrends.ui.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.chris.twittertrends.R;
import com.example.chris.twittertrends.di.HasComponent;

import butterknife.BindView;

/**
 * Created by Chris on 3/21/18.
 */

public abstract class BaseFragment extends Fragment {
    @Nullable @BindView(R.id.progress_bar) ViewGroup progressView;

    //region common utilities
    protected void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    protected void showProgress() {
        if (progressView != null) {
            progressView.setVisibility(View.VISIBLE);
        }
    }

    protected void dismissProgress() {
        if (progressView != null) {
            progressView.setVisibility(View.GONE);
        }
    }
    //endregion

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    protected <T> T getComponent(Class<T> componentType) {
        return componentType.cast(((HasComponent<T>) getActivity()).getComponent());
    }
}
