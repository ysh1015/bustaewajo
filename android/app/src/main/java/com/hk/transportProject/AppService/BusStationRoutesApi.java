package com.hk.transportProject.AppService;

import com.hk.transportProject.response.BusRouteResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BusStationRoutesApi {
    @GET("1613000/BusSttnInfoInqireService/getSttnThrghRouteList")
    Call<BusRouteResponse> getStationRoutes(
            @Query("serviceKey") String serviceKey,
            @Query("cityCode") int cityCode,
            @Query("nodeId") String nodeId,
            @Query("numOfRows") int numOfRows,
            @Query("pageNo") int pageNo,
            @Query("_type") String type
    );
}
