package com.hk.transportProject.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.hk.transportProject.AppService.WeatherApiService;
import com.hk.transportProject.model.Result;
import com.hk.transportProject.response.WeatherResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {

    private final WeatherApiService apiService;

    public WeatherRepository(WeatherApiService apiService) {
        this.apiService = apiService;
    }

    public LiveData<Result<List<WeatherResponse.Response.Body.Items.Item>>> getWeather(
            String serviceKey, String baseDate, String baseTime, int nx, int ny) {

        MutableLiveData<Result<List<WeatherResponse.Response.Body.Items.Item>>> liveData = new MutableLiveData<>();

        liveData.postValue(Result.loading());

        apiService.getWeather(serviceKey, 10, 1, "JSON", baseDate, baseTime, nx, ny)
                .enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            WeatherResponse weatherResponse = response.body();

                            // Check for null fields in the response
                            if (weatherResponse.response != null && weatherResponse.response.body != null) {
                                if (weatherResponse.response.body.items != null) {
                                    liveData.postValue(Result.success(weatherResponse.response.body.items.item));
                                } else {
                                    liveData.postValue(Result.error("No weather data available."));
                                }
                            } else {
                                liveData.postValue(Result.error("Invalid response structure from API."));
                            }
                        } else {
                            Log.e("WeatherRepository", "API Error: " + response.message());
                            liveData.postValue(Result.error("API Error: " + response.message()));
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {
                        Log.e("WeatherRepository", "Network Error: " + t.getMessage());
                        liveData.postValue(Result.error("Network Error: " + t.getMessage()));
                    }
                });

        return liveData;
    }
}
