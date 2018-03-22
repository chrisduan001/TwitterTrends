package com.example.chris.twittertrends.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Chris on 3/21/18.
 */

public class LocationEntity {
    @SerializedName("name")
    public String name;

    @SerializedName("country")
    public String country;

    @SerializedName("woeid")
    public String woeid;
}
