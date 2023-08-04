package com.example.finalteamproject.gps;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.databinding.FragmentGpsBinding;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.util.MarkerIcons;

import java.util.ArrayList;

//지도 나오는 첫 페이지
public class GpsFragment extends Fragment implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;

    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    private double lat, lon;
    FragmentGpsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGpsBinding.inflate(inflater, container, false);

        //지도 객체 생성
        FragmentManager fm = getChildFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.map);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        //현재 위치 생성, naverMap에 지정
        locationSource =
                new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        //경로당 리스트(리사이클러뷰)
        binding.recvGps.setAdapter(new GpsAdapter(getContext()));
        binding.recvGps.setLayoutManager(new LinearLayoutManager(getContext()));

        //(자주가는 경로당)더보기 메뉴
        binding.tvMore.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), GpsLikeActivity.class);
            startActivity(intent);
        });

        return binding.getRoot();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,  @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated()) { // 권한 거부됨
                naverMap.setLocationTrackingMode(LocationTrackingMode.None);
            }
            return;
        }
        super.onRequestPermissionsResult(
                requestCode, permissions, grantResults);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        naverMap.setLocationSource(locationSource); //내 위치
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow); //위치 추적 모드

        //내 위치 위도, 경도 이동
        naverMap.addOnLocationChangeListener(new NaverMap.OnLocationChangeListener() {
            @Override
            public void onLocationChange(@NonNull Location location) {
                lat = location.getLatitude();
                lon = location.getLongitude();
                // 현재 위치에 마커 생성
                Marker marker = new Marker();
                marker.setPosition(new LatLng(lat, lon));
                marker.setMap(naverMap);
                //마커 디자인
                marker.setIcon(MarkerIcons.BLACK); //색상
                marker.setIconTintColor(Color.RED);
                marker.setWidth(70); //마커사이즈
                marker.setHeight(100);

                //현재 위치 오버레이 생성
//                LocationOverlay locationOverlay = naverMap.getLocationOverlay();
//                locationOverlay.setVisible(true);
//                locationOverlay.setPosition(new LatLng(lat, lon));
//                locationOverlay.setCircleRadius(100);

                Log.d("위치", "위도, 경도: "+lat+", "+lon);
            }
        });
    }

    public void seniorList(){
        CommonConn conn = new CommonConn(getContext(), "gps/senior");
        conn.onExcute((isResult, data) -> {
            ArrayList<GpsVO> list = new ArrayList<>();



        });
    }


}

