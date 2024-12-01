package com.hk.transportProject.callback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetryableCallback<T> implements Callback<T> {
    private final Callback<T> callback;
    private final int maxRetries;
    private int retryCount = 0;
    
    public RetryableCallback(Callback<T> callback, int maxRetries) {
        this.callback = callback;
        this.maxRetries = maxRetries;
    }
    
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (!response.isSuccessful() && retryCount < maxRetries) {
            retryCount++;
            call.clone().enqueue(this);
            return;
        }
        callback.onResponse(call, response);
    }
    
    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (retryCount < maxRetries) {
            retryCount++;
            call.clone().enqueue(this);
            return;
        }
        callback.onFailure(call, t);
    }
}
