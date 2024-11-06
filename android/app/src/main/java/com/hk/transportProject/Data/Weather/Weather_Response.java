package com.hk.transportProject.Data.Weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

import retrofit2.http.Body;

@Generated("jsonschema2pojo")
public class Weather_Response {

    @SerializedName("header")
    @Expose
    private WeatherHeader header;
    @SerializedName("body")
    @Expose
    private Body body;

    public WeatherHeader getHeader() {
        return header;
    }

    public void setHeader(WeatherHeader header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

}