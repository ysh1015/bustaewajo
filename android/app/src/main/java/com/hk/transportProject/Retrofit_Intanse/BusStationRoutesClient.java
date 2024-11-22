package com.hk.transportProject.Retrofit_Intanse;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// 버스 정류장 경로 API 설정
public class BusStationRoutesClient 
{
    private static final String BASE_URL = "http://apis.data.go.kr/";
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
