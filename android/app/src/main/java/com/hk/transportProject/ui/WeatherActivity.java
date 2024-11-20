package com.hk.transportProject.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.hk.transportProject.AppService.WeatherApiService;
import com.hk.transportProject.Retrofit_Intanse.WeatherRetrofitClient;
import com.hk.transportProject.databinding.ActivityWeatherBinding;
import com.hk.transportProject.repository.WeatherRepository;
import com.hk.transportProject.viewmodel.WeatherViewModel;
import com.hk.transportProject.viewmodel.WeatherViewModelFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class WeatherActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private FusedLocationProviderClient fusedLocationClient;
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

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        binding.btnLoadWeather.setOnClickListener(v -> fetchWeatherForCurrentLocation());

        binding.btnGoBack.setOnClickListener(v ->{
            finish();
        });
    }

    private void fetchWeatherForCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();

                            //String baseDate = "20241120";
                            //String baseTime = "2000";
                            //int nx = 60;
                            //int ny = 127;

                            String baseDate = getBaseDate();
                            String baseTime = getBaseTime();

                            int[] gridCoords = convertToGrid(latitude, longitude);
                            int nx = gridCoords[0];
                            int ny = gridCoords[1];

                            viewModel.fetchWeather(baseDate, baseTime, nx, ny);
                        } else {
                            Toast.makeText(this, "위치를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "위치 요청 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private String getBaseDate() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // 발표 시간보다 이전이면 하루 전 날짜로 설정
        if (hour < 2 || (hour == 2 && minute < 10)) {
            calendar.add(Calendar.DATE, -1);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

        return sdf.format(calendar.getTime());
    }

    private String getBaseTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // 기상청 발표 시간 목록
        int[] baseTimes = {2, 5, 8, 11, 14, 17, 20, 23};

        int closestTime = baseTimes[0];
        for (int i = 0; i < baseTimes.length; i++) {
            if (hour < baseTimes[i] || (hour == baseTimes[i] && minute < 10)) {
                closestTime = (i == 0) ? baseTimes[baseTimes.length - 1] : baseTimes[i - 1];
                break;
            }
            closestTime = baseTimes[i];
        }

        // String 형식의 시간 반환 (예: "0200", "0500")
        return String.format(Locale.getDefault(), "%02d00", closestTime);
    }


    private int[] convertToGrid(double lat, double lon) {
        double RE = 6371.00877;
        double GRID = 5.0;
        double SLAT1 = 30.0;
        double SLAT2 = 60.0;
        double OLON = 126.0;
        double OLAT = 38.0;
        double XO = 210 / GRID;
        double YO = 675 / GRID;

        double DEGRAD = Math.PI / 180.0;

        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);

        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;

        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);

        double ra = Math.tan(Math.PI * 0.25 + lat * DEGRAD * 0.5);
        ra = re * sf / Math.pow(ra, sn);
        double theta = lon * DEGRAD - olon;
        if (theta > Math.PI) theta -= 2.0 * Math.PI;
        if (theta < -Math.PI) theta += 2.0 * Math.PI;
        theta *= sn;

        int x = (int) (ra * Math.sin(theta) + XO + 0.5);
        int y = (int) (ro - ra * Math.cos(theta) + YO + 0.5);

        return new int[]{x, y};
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); // 추가된 코드

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 허용된 경우 위치 데이터를 가져옴
                fetchWeatherForCurrentLocation();
            } else {
                // 권한이 거부된 경우 사용자에게 알림
                Toast.makeText(this, "위치 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
