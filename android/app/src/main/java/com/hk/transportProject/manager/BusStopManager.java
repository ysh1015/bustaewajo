package com.hk.transportProject.manager;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.hk.transportProject.AppService.TrafficApiService;
import com.hk.transportProject.R;
import com.hk.transportProject.Retrofit_Intanse.TrafficRetrofitClient;
import com.hk.transportProject.callback.RetryableCallback;
import com.hk.transportProject.network.NetworkUtils;
import com.hk.transportProject.response.TrafficApiResponse;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import okhttp3.ResponseBody;
import com.hk.transportProject.security.ApiKeyStore;

public class BusStopManager {
    private final Context context;
    private final NaverMap naverMap;
    private final List<Marker> markers;
    private final TrafficApiService apiService;
    private final ApiKeyStore apiKeyStore;
    
    private double currentLat;
    private double currentLng;
    
    public BusStopManager(Context context, NaverMap naverMap) {
        this.context = context;
        this.naverMap = naverMap;
        this.markers = new ArrayList<>();
        this.apiService = TrafficRetrofitClient.getClient()
            .create(TrafficApiService.class);
        
        try {
            this.apiKeyStore = new ApiKeyStore(context);
        } catch (Exception e) {
            throw new RuntimeException("API 키 저장소 초기화 실패", e);
        }
    }
    
    public void fetchNearbyBusStops(double latitude, double longitude) {
        try {
            String apiKey = apiKeyStore.getApiKey();
            if (apiKey == null) {
                showError("API 키를 찾을 수 없습니다.");
                return;
            }
            
            this.currentLat = latitude;
            this.currentLng = longitude;
            
            if (!NetworkUtils.isNetworkAvailable(context)) {
                showError("네트워크 연결을 확인해주세요.");
                return;
            }
            
            clearMarkers();
            
            RetryableCallback<TrafficApiResponse> retryableCallback = new RetryableCallback<>(
                new Callback<TrafficApiResponse>() {
                    @Override
                    public void onResponse(Call<TrafficApiResponse> call, Response<TrafficApiResponse> response) {
                        try {
                            if (response.isSuccessful() && response.body() != null) {
                                TrafficApiResponse apiResponse = response.body();
                                if (apiResponse.isSuccessful()) {
                                    handleSuccessResponse(apiResponse);
                                } else {
                                    showError("API 오류: " + apiResponse.getErrorMessage());
                                }
                            } else {
                                String errorMsg = response.errorBody() != null ? 
                                    "API 오류: " + getErrorBodyString(response.errorBody()) :
                                    "API 응답에 실패했습니다.";
                                showError(errorMsg);
                                Log.e("BusStopManager", "API response error: " + response.code());
                            }
                        } catch (Exception e) {
                            Log.e("BusStopManager", "Response handling error", e);
                            showError("데이터 처리 중 오류가 발생했습니다.");
                        }
                    }
                    
                    @Override
                    public void onFailure(Call<TrafficApiResponse> call, Throwable t) {
                        Log.e("BusStopManager", "API call failed", t);
                        showError("API 호출 중 오류가 발생했습니다. 다시 시도합니다.");
                    }
                },
                3
            );

            apiService.getTrafficInfo(
                apiKey,
                1,
                10,
                "json",
                (float)latitude,
                (float)longitude
            ).enqueue(retryableCallback);
        } catch (Exception e) {
            Log.e("BusStopManager", "API 호출 실패", e);
            showError("API 호출 중 오류가 발생했습니다.");
        }
    }
    
    private String getErrorBodyString(ResponseBody errorBody) {
        try {
            return errorBody.string();
        } catch (IOException e) {
            return "알 수 없는 오류";
        }
    }
    
    private void handleSuccessResponse(TrafficApiResponse response) {
        List<TrafficApiResponse.BusStation> stations = response.getValidBusStations();
        if (stations.isEmpty()) {
            showMessage("주변에 버스 정류장이 없습니다.");
            return;
        }
        
        for (TrafficApiResponse.BusStation station : stations) {
            if (station.isWithinRadius(currentLat, currentLng, 500)) {
                addBusStopMarker(station);
            }
        }
    }
    
    private void addBusStopMarker(TrafficApiResponse.BusStation station) {
        Marker marker = new Marker();
        marker.setPosition(station.toLatLng());
        marker.setIcon(OverlayImage.fromResource(R.drawable.ic_bus_stop));
        marker.setWidth(60);
        marker.setHeight(60);
        marker.setCaptionText(station.getNodeName() + 
            " (" + station.getFormattedDistance(currentLat, currentLng) + ")");
        marker.setTag(station);
        
        marker.setOnClickListener(overlay -> {
            showBusStopInfo(station);
            return true;
        });
        
        markers.add(marker);
        marker.setMap(naverMap);
    }
    
    private void showBusStopInfo(TrafficApiResponse.BusStation station) {
        if (context == null) return;
        
        BottomSheetDialog bottomSheet = new BottomSheetDialog(context);
        try {
            View bottomSheetView = LayoutInflater.from(context)
                .inflate(R.layout.bottom_sheet_bus_stop, null);
            
            TextView stationNameText = bottomSheetView.findViewById(R.id.station_name);
            TextView stationInfoText = bottomSheetView.findViewById(R.id.station_info);
            
            if (stationNameText != null && stationInfoText != null) {
                stationNameText.setText(station.getNodeName());
                
                String stationInfo = String.format(
                    "정류소 번호: %d\n정류소 ID: %s\n도시 코드: %d",
                    station.getNodeNo(),
                    station.getNodeId(),
                    station.getCityCode()
                );
                stationInfoText.setText(stationInfo);
                
                bottomSheet.setContentView(bottomSheetView);
                bottomSheet.show();
            }
        } catch (Exception e) {
            Log.e("BusStopManager", "Error showing bus stop info", e);
            showError("정류장 정보를 표시할 수 없습니다.");
        }
    }
    
    public void clearMarkers() {
        try {
            if (markers != null) {
                for (Marker marker : markers) {
                    if (marker != null) {
                        marker.setMap(null);
                    }
                }
                markers.clear();
            }
        } catch (Exception e) {
            Log.e("BusStopManager", "Error clearing markers", e);
        }
    }
    
    private void showError(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    
    private void showMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

} 