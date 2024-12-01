package com.hk.transportProject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.hk.transportProject.AppService.TrafficApiService;
import com.hk.transportProject.AppService.WeatherApiService;
import com.hk.transportProject.Retrofit_Intanse.WeatherRetrofitClient;
import com.hk.transportProject.databinding.ActivityMainBinding;
import com.hk.transportProject.manager.BusStopManager;
import com.hk.transportProject.repository.WeatherRepository;
import com.hk.transportProject.ui.LoginActivity;
import com.hk.transportProject.ui.WeatherActivity;
import com.hk.transportProject.viewmodel.WeatherViewModel;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.overlay.Marker;
import com.hk.transportProject.Retrofit_Intanse.TrafficRetrofitClient;
import java.util.ArrayList;
import java.util.List;

import com.hk.transportProject.security.ApiKeyStore;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 5000;

    // 현재 위치 좌표를 저장할 변수 추가
    private double currentLat;
    private double currentLng;
    
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };


    private Button btn_weather_detail;
    private WeatherViewModel viewModel;
    private ActivityMainBinding binding;
    private NaverMap naverMap;
    private FusedLocationSource locationSource;
    private BusStopManager busStopManager;

    private TrafficApiService apiService;
    private List<Marker> busStopMarkers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // API 키 초기화
        try {
            ApiKeyStore apiKeyStore = new ApiKeyStore(this);
            if (apiKeyStore.getApiKey() == null) {
                apiKeyStore.storeApiKey(BuildConfig.TRAFFIC_API_KEY);
            }
        } catch (Exception e) {
            Log.e("MainActivity", "API 키 초기화 실패", e);
        }

        binding.btnWeatherDetail.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
            startActivity(intent);
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        // 위치 권한 체크 및 요청
        checkLocationPermission();

        // API 서비스 초기화
        apiService = TrafficRetrofitClient.getClient()
            .create(TrafficApiService.class);

        if (!hasPermission()) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            initMapView();
        }

        /*
        Button test_btn = (Button) findViewById(R.id.test_bnt);
        test_btn.setOnClickListener(view -> {
            Intent i = new Intent(this, TestActivity.class);
            startActivity(i);
        });*/

    }

    private void checkLocationPermission() {
        // 권한이 없는 경우
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) 
                != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) 
                != PackageManager.PERMISSION_GRANTED) {
            
            // 권한 요청 다이얼로그 표시
            ActivityCompat.requestPermissions(this,
                new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                },
                LOCATION_PERMISSION_REQUEST_CODE
            );
        } else {
            // 이미 권한이 있는 경우
            initMapView();
        }
    }

    private void initMapView() {
        // FragmentManager를 사용하여 MapFragment를 불러온다.
        if (getSupportFragmentManager().findFragmentById(R.id.NaverMap) == null) {
            MapFragment mapFragment = MapFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.NaverMap, mapFragment)
                    .commit();
            mapFragment.getMapAsync(this);
        } else {
            MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.NaverMap);
            mapFragment.getMapAsync(this);
        }

        // 위치를 추적하는 소스를 설정
        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
    }

    private boolean hasPermission() {
        for (String permission : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        
        // BusStopManager 초기화
        busStopManager = new BusStopManager(this, naverMap);
        
        // 위치 소스 설정
        naverMap.setLocationSource(locationSource);
        
        // 위치 추적 모드 설정
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
        
        // 위치 변경 리스너 설정
        naverMap.addOnLocationChangeListener(location -> {
            currentLat = location.getLatitude();
            currentLng = location.getLongitude();
            
            // BusStopManager를 통해 주변 버스 정류장 검색
            busStopManager.fetchNearbyBusStops(currentLat, currentLng);
        });
        
        // UI 설정
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initMapView();
            } else {
                Toast.makeText(this, "위치 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (busStopManager != null) {
            busStopManager.clearMarkers();  // 리소스 정리
        }
    }
}
