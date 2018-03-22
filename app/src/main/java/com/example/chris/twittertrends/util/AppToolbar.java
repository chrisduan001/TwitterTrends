package com.example.chris.twittertrends.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.chris.twittertrends.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Chris on 3/21/18.
 */

public class AppToolbar extends Toolbar {
    @Nullable @BindView(R.id.toolbar_title) TextView toolbarTitle;

    public AppToolbar(Context context) {
        super(context);
    }

    public AppToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AppToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void showToolbarTitle(@StringRes int stringRes) {
        if (toolbarTitle == null) {
            initView();
        }

        toolbarTitle.setText(getContext().getString(stringRes));
    }

    public void showToolbarTitle(String title) {
        if (toolbarTitle == null) {
            initView();
        }

        toolbarTitle.setText(title);
    }

    private void initView() {
        ButterKnife.bind(this);
    }
}
