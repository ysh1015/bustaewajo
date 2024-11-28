package com.hk.transportProject.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.hk.transportProject.AppService.WeatherApiService;
import com.hk.transportProject.Retrofit_Intanse.WeatherRetrofitClient;
import com.hk.transportProject.databinding.ActivityWeatherBinding;
import com.hk.transportProject.repository.WeatherRepository;
import com.hk.transportProject.viewmodel.WeatherViewModel;
import com.hk.transportProject.viewmodel.WeatherViewModelFactory;

public class WeatherActivity extends AppCompatActivity {

    private ActivityWeatherBinding binding;
    private WeatherViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // DataBinding 초기화
        binding = ActivityWeatherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // ViewModel 초기화
        WeatherRepository repository = new WeatherRepository(
                WeatherRetrofitClient.getClient().create(WeatherApiService.class)
        );
        WeatherViewModelFactory factory = new WeatherViewModelFactory(repository);
        viewModel = new ViewModelProvider(this, factory).get(WeatherViewModel.class);

        // ViewModel과 레이아웃 연결
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        binding.btnGoBack.setOnClickListener(v ->{
            finish();
        });
    }
}
