package com.hk.transportProject.Data.Weather;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class WeatherItems {

    @SerializedName("item")
    @Expose
    private List<WeatherItem> item;

    public List<WeatherItem> getItem() {
        return item;
    }

    public void setItem(List<WeatherItem> item) {
        this.item = item;
    }

}