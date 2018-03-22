package com.example.chris.twittertrends.network;

import com.example.chris.twittertrends.entities.LocationEntity;
import com.example.chris.twittertrends.entities.PlaceEntity;
import com.example.chris.twittertrends.entities.TokenEntity;
import com.example.chris.twittertrends.entities.TweetsEntity;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Chris on 3/21/18.
 */

public interface TwitterApi {

    @FormUrlEncoded
    @POST("/oauth2/token")
    Single<TokenEntity> getOauthToken(
            @Header("Authorization") String token,
            @Field("grant_type") String grantType
    );

    @GET("/1.1/trends/closest.json")
    Single<List<LocationEntity>> getCloestLocation(
            @Header("Authorization") String token,
            @Query("lat") String lat,
            @Query("long") String longitude
    );

    @GET("/1.1/trends/place.json")
    Single<List<PlaceEntity>> getTrends(
            @Header("Authorization") String token,
            @Query("id") String id
    );

    @GET("/1.1/search/tweets.json")
    Single<TweetsEntity> getTweets(
            @Header("Authorization") String token,
            @Query("q") String query
    );
}
