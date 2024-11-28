package com.hk.transportProject.AppService;

import com.google.gson.JsonObject;
import com.hk.transportProject.response.WeatherResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;

import retrofit2.Call;


public interface WeatherApiService {
    @GET("getVilageFcst")
    Call<WeatherResponse> getWeather(
            @Query("serviceKey") String serviceKey,
            @Query("numOfRows") int numOfRows,
            @Query("pageNo") int pageNo,
            @Query("dataType") String dataType,
            @Query("base_date") String baseDate,
            @Query("base_time") String baseTime,
            @Query("nx") int nx,
            @Query("ny") int ny

    );
}

