package com.hk.transportProject.Data.Weather;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class WeatherItem {

    @SerializedName("baseDate")
    @Expose
    private String baseDate;
    @SerializedName("baseTime")
    @Expose
    private String baseTime;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("nx")
    @Expose
    private Integer nx;
    @SerializedName("ny")
    @Expose
    private Integer ny;
    @SerializedName("obsrValue")
    @Expose
    private String obsrValue;

    public String getBaseDate() {
        return baseDate;
    }

    public void setBaseDate(String baseDate) {
        this.baseDate = baseDate;
    }

    public String getBaseTime() {
        return baseTime;
    }

    public void setBaseTime(String baseTime) {
        this.baseTime = baseTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getNx() {
        return nx;
    }

    public void setNx(Integer nx) {
        this.nx = nx;
    }

    public Integer getNy() {
        return ny;
    }

    public void setNy(Integer ny) {
        this.ny = ny;
    }

    public String getObsrValue() {
        return obsrValue;
    }

    public void setObsrValue(String obsrValue) {
        this.obsrValue = obsrValue;
    }

}