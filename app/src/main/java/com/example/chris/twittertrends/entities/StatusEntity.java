package com.example.chris.twittertrends.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Chris on 3/21/18.
 */

public class StatusEntity {
    @SerializedName("created_at")
    public String createdAt;

    @SerializedName("text")
    public String text;

    @SerializedName("user")
    public UserEntity user;
}
