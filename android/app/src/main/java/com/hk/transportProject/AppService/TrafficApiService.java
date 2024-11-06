package com.hk.transportProject.AppService;

import com.google.gson.JsonObject;
import com.hk.transportProject.Model.TrafficResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TrafficApiService {
    @GET("traffic-info")
    Call<JsonObject> getTrafficInfo(
            @Query("serviceKey") String servicekey, // 인증키
            @Query("pageNo") int pageno,  // 페이지번호
            @Query("numOFRows") int numofrows, // 한 페이지 결과 수
            @Query("_type") String type,    // 데이터 타입 : json
            @Query("gpsLati") float gpsy,   // GPS Y좌표
            @Query("gpsLong") float gpsx    // GPS X좌표
    );
}

