package com.hk.transportProject.Retrofit_Intanse;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// 교통 정보 API 설정
public class TrafficRetrofitClient {
    private static final String BASE_URL = "https://api.traffic.com/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

