package com.petar.weather.networking.models;

import com.google.gson.annotations.SerializedName;
import com.petar.weather.logic.models.ISource;

/**
 * Created by User on 21.6.2017 г..
 */

public class NSource implements ISource {

    @SerializedName("title")
    private String title;
    @SerializedName("slug")
    private String slug;
    @SerializedName("url")
    private String url;
    @SerializedName("crawl_rate")
    private Integer crawlRate;
}
