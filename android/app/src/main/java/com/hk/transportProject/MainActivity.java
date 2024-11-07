package com.hk.transportProject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.hk.transportProject.R;
import com.hk.transportProject.databinding.ActivityMainBinding;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.util.FusedLocationSource;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 5000;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private ActivityMainBinding binding;
    private NaverMap naverMap;
    private FusedLocationSource locationSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        if (!hasPermission()) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
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
    public void onMapReady(NaverMap naverMap) {
        this.naverMap = naverMap;

        // 현재 위치 소스를 설정
        naverMap.setLocationSource(locationSource);

        // 현재 위치 버튼을 활성화
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);

        // 위치 추적 모드 설정
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initMapView();
            } else {
                // 권한을 거부한 경우 사용자에게 알림
                Toast.makeText(this, "위치 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
