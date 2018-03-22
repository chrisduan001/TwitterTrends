package com.example.chris.twittertrends.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Chris on 3/21/18.
 */

public class PlaceEntity {
    @SerializedName("trends")
    public List<TrendsEntity> trendsList;
}
