package com.hk.transportProject.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hk.transportProject.model.Result;
import com.hk.transportProject.response.WeatherResponse;
import com.hk.transportProject.repository.WeatherRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class WeatherViewModel extends ViewModel {

    private final WeatherRepository repository;
    private final MutableLiveData<String> weatherText = new MutableLiveData<>();

    // Default values for API request
    private final String serviceKey = "xZVyEXB5g2v3CEyT4QnoWdABt2ZHH4jLNuEHT2R7ivPWW6oBFrBUDOdw6mAt9dohXYqIpHc5SPlF5pmgRZ2dFg==";
    //private final String baseDate = "20241119";
    //private final String baseTime = "0500";
    //private final int nx = 60;
    //private final int ny = 127;

    public WeatherViewModel(WeatherRepository repository) {
        this.repository = repository;
        weatherText.setValue("날씨 정보를 확인하세요.");
    }

    public LiveData<String> getWeatherText() {
        return weatherText;
    }

    // No-argument fetchWeather method for DataBinding
    public void fetchWeather(String baseDate, String baseTime, int nx, int ny) {
        weatherText.setValue("Loading...");

        repository.getWeather(serviceKey, baseDate, baseTime, nx, ny).observeForever(result -> {
            switch (result.status) {
                case LOADING:
                    weatherText.setValue("Loading...");
                    break;

                case SUCCESS:
                    /*
                    StringBuilder formattedWeather = new StringBuilder();
                    for (WeatherResponse.Response.Body.Items.Item item : result.data) {
                        formattedWeather.append(getDescription(item.category, item.fcstValue)).append("\n");
                    }
                    weatherText.setValue(formattedWeather.toString());
                    break;
                    */
                    StringBuilder formattedWeather = new StringBuilder();
                    for (WeatherResponse.Response.Body.Items.Item item : result.data) {
                        formattedWeather.append(getDescription(item.category, item.fcstValue)).append("\n");
                    }

                    weatherText.setValue(formattedWeather.toString());
                    break;

                    /*
                    for (WeatherResponse.Response.Body.Items.Item item : result.data) {
                        if ("TMP".equals(item.category)) {
                            String temperature = getDescription(item.category, item.fcstValue);
                            weatherText.setValue(temperature); // 기온만 설정
                            break; // 첫 번째 TMP 항목만 처리 후 종료
                        }
                    }
                    break;
                    */
                case ERROR:
                    weatherText.setValue("Error: " + result.message);
                    break;
            }
        });
    }

    private String getDescription(String category, String value) {
        switch (category) {
            case "TMP": return "기온: " + value + "℃";
            case "UUU": return "동서 바람 속도: " + value + " m/s";
            case "VVV": return "남북 바람 속도: " + value + " m/s";
            case "VEC": return "풍향: " + value + "°";
            case "WSD": return "풍속: " + value + " m/s";
            case "SKY": return "하늘 상태: " + getSkyDescription(value);
            case "PTY": return "강수 형태: " + getPtyDescription(value);
            case "POP": return "강수 확률: " + value + "%";
            case "WAV": return "파고: " + value + " m";
            case "PCP": return "강수량: " + (value.equals("강수없음") ? value : value + " mm");
            default: return category + ": " + value;
        }
    }

    private String getSkyDescription(String value) {
        switch (value) {
            case "1": return "맑음";
            case "3": return "구름 많음";
            case "4": return "흐림";
            default: return "알 수 없음";
        }
    }

    private String getPtyDescription(String value) {
        switch (value) {
            case "0": return "없음";
            case "1": return "비";
            case "2": return "비/눈";
            case "3": return "눈";
            case "4": return "소나기";
            default: return "알 수 없음";
        }
    }
}

