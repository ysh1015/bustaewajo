package com.hk.transportProject.Data.Weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

@Generated("jsonschema2pojo")
public class WeatherData {

    @SerializedName("response")
    @Expose
    private Weather_Response response;

    public Weather_Response getResponse() {
        return response;
    }

    public void setResponse(Weather_Response response) {
        this.response = response;
    }

}
