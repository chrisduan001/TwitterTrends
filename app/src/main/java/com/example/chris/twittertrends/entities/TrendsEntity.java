package com.example.chris.twittertrends.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Chris on 3/21/18.
 */

public class TrendsEntity {
    @SerializedName("name")
    public String trendsName;

    @SerializedName("query")
    public String trendsQuery;

    @SerializedName("tweet_volume")
    public String tweetVolume;
}
