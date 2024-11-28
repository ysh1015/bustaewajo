package com.hk.transportProject.Retrofit_Intanse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hk.transportProject.deserializer.TrafficApiResponseDeserializer;
import com.hk.transportProject.response.TrafficApiResponse;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// 버스 정류장 경로 API 설정
public class BusStationRoutesClient 
{
    private static final String BASE_URL = "http://apis.data.go.kr/";
    private static Retrofit retrofit = null;
    private static final Object LOCK = new Object();

    private static final int MAX_RETRIES = 4;
    private static final int TIMEOUT_SECONDS = 60;

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request request = chain.request();
                        Response response = chain.proceed(request);

                        // 응답이 성공이 아닌 경우 재시도
                        int tryCount = 0;
                        while (!response.isSuccessful() && tryCount < MAX_RETRIES) {
                            tryCount++;
                            response.close();
                            response = chain.proceed(request);
                        }
                        return response;
                    })
                    .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .registerTypeAdapter(TrafficApiResponse.class, new TrafficApiResponseDeserializer())
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
