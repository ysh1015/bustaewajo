package com.hk.transportProject.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.hk.transportProject.repository.WeatherRepository;

public class WeatherViewModelFactory implements ViewModelProvider.Factory {

    private final WeatherRepository repository;

    // Constructor for injecting dependencies
    public WeatherViewModelFactory(WeatherRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WeatherViewModel.class)) {
            // Pass the repository to the ViewModel's constructor
            return (T) new WeatherViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
