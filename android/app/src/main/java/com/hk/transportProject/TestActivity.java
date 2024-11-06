package com.hk.transportProject;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.JsonObject;
import com.hk.transportProject.AppService.EventApiService;
import com.hk.transportProject.AppService.WeatherApiService;
import com.hk.transportProject.Retrofit_Intanse.EventRetrofitClient;
import com.hk.transportProject.Retrofit_Intanse.WeatherRetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 문화유산 API 서비스 인터페이스 호출
        EventApiService eventApiService = EventRetrofitClient.getClient().create(EventApiService.class);

        // 현재 날짜와 시간 설정 (예: 기본값으로 2024년 10월 14일, 06:00)
        String base_Date = "20241016"; // YYYYMMDD 형식
        String baseTime = "0630"; // HHmm 형식
        int nx = 60; // 예시 X 좌표 (서울)
        int ny = 127; // 예시 Y 좌표 (서울)

        Call<JsonObject> call = eventApiService.getEventInfo(
                            1,
                            10,
                        "g3s/sU96JysexYpblDXIc4+V33peeadeoSi2BpBF5ej8XHRQtmPphiSA4dkF3s7b0CF5gDDO6/N2/weFSIDgCA=="
        );


        // 비동기 API 요청
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // 성공적인 응답
                    JsonObject evenData = response.body();
                    Log.d("EvenAPI", "응답 성공: " + evenData.toString());

                    // 텍스트뷰에 출력
                    TextView textView = findViewById(R.id.textView);
                    textView.setText(evenData.toString());
                } else {
                    // 실패 응답
                    Log.e("WeatherAPI", "응답 실패, 상태 코드: " + response.code());
                    try {
                        Log.e("WeatherAPI", "에러 응답 본문: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                // API 호출 실패
                Log.e("EvenAPI", "API 호출 실패: " + t.getMessage());
            }
        });



    }
}