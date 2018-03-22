package com.example.chris.twittertrends.util;

import android.content.SharedPreferences;

/**
 * Created by Chris on 3/22/18.
 */

public class AppSharedPreference {
    private static final String TOKEN_DATA = "TOKEN_DATA";

    private SharedPreferences sharedPreferences;

    public AppSharedPreference(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void setToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_DATA, token);

        editor.apply();
    }

    public String getToken() {
        return sharedPreferences.getString(TOKEN_DATA, "");
    }

    public void clearToken() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(TOKEN_DATA);

        editor.apply();
    }
}
