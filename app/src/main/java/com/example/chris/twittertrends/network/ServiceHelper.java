package com.example.chris.twittertrends.network;

import com.example.chris.twittertrends.entities.LocationEntity;
import com.example.chris.twittertrends.entities.PlaceEntity;
import com.example.chris.twittertrends.entities.TokenEntity;
import com.example.chris.twittertrends.entities.TweetsEntity;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Chris on 3/21/18.
 */

public class ServiceHelper {
    private static final String BASIC_AUTH_TOKEN = "Basic SkpJOGlPejlKb1h2bW5RaVhtbnNYTXdiNDpsYlBBWkZVejZ5N3lYVWtVU3NsbzJYMFVMM3JBMU9zdE00SkJMNE1Ta0ZGS3g4dXhRYQ==";
    private static final String GRANT_TYPE = "client_credentials";

    private final TwitterApi twitterApi;
    private String token;

    public ServiceHelper(TwitterApi twitterApi) {
        this.twitterApi = twitterApi;
    }

    public void setToken(String token) {
        this.token = String.format("Bearer %s", token);
    }

    public Single<TokenEntity> getOauthToken() {
        return twitterApi.getOauthToken(BASIC_AUTH_TOKEN, GRANT_TYPE);
    }

    public Single<List<LocationEntity>> getClosestLocation(String lat, String longi) {
        return twitterApi.getCloestLocation(token, lat, longi);
    }

    public Single<List<PlaceEntity>> getTrends( String id) {
        return twitterApi.getTrends(token, id);
    }

    public Single<TweetsEntity> getTweets(String query) {
        return twitterApi.getTweets(token, query);
    }
}
