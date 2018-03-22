package com.example.chris.twittertrends.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Chris on 3/21/18.
 */

public class UserEntity {
    @SerializedName("screen_name")
    public String userName;

    @SerializedName("location")
    public String location;

    @SerializedName("description")
    public String description;

    @SerializedName("favourites_count")
    public String favouritesCount;

    @SerializedName("profile_banner_url")
    public String profileBannerImage;
}
