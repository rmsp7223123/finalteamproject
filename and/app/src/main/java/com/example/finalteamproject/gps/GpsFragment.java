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
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.FragmentGpsBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.CameraUpdateParams;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
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

        //검색 결과 페이지
        binding.lnResult.setVisibility(View.GONE);
        binding.btnSearch.setOnClickListener(v -> {
            binding.lnResult.setVisibility(View.VISIBLE);
            //검색 결과 데이터
            CommonConn connresult = new CommonConn(getContext(), "gps/search");
            connresult.addParamMap("keyword", binding.gpsSearch.getText());
            connresult.onExcute((isResult, data) -> {
                ArrayList<GpsVO> list = new Gson().fromJson(data, new TypeToken<ArrayList<GpsVO>>(){}.getType());
                GpsAdapter adapter = new GpsAdapter(list);
                binding.recvSearchResult.setAdapter(adapter);
                binding.recvSearchResult.setLayoutManager(new LinearLayoutManager(getContext()));
            });
        });
        binding.btnClose.setOnClickListener(v -> {
            binding.lnResult.setVisibility(View.GONE);
        });

        //자주 가는 경로당(리사이클러뷰)
        CommonConn conn = new CommonConn(getContext(), "gps/likelist");
        conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
        conn.onExcute((isResult, data) -> {
            ArrayList<GpsVO> list = new Gson().fromJson(data, new TypeToken<ArrayList<GpsVO>>(){}.getType());

            GpsBmarkAdapter adapter = new GpsBmarkAdapter(list);
            binding.recvBmark.setAdapter(adapter);
            binding.recvBmark.setLayoutManager(new LinearLayoutManager(getContext()));
        });

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
                //내 위치 위도, 경도
                lat = location.getLatitude();
                lon = location.getLongitude();

                CommonConn conn = new CommonConn(getContext(), "gps/senior");
                conn.addParamMap("senior_latitude", lat);
                conn.addParamMap("senior_longitude", lon);
                conn.onExcute((isResult, data) -> {
                    ArrayList<GpsVO> list = new Gson().fromJson(data, new TypeToken<ArrayList<GpsVO>>() {
                    }.getType());

                    for (GpsVO gpsVO : list) {
                        double markerLat = Double.parseDouble(gpsVO.getSenior_latitude());
                        double markerLon = Double.parseDouble(gpsVO.getSenior_longitude());

                        // 현재 (내)위치에 마커 생성
                        Marker Imarker = new Marker();
                        Imarker.setPosition(new LatLng(lat, lon));
                        Imarker.setMap(naverMap);
                        //마커 디자인
                        Imarker.setIcon(MarkerIcons.BLACK); //색상
                        Imarker.setIconTintColor(Color.parseColor("#FE69B7"));
                        Imarker.setWidth(70); //마커사이즈
                        Imarker.setHeight(100);

                        Log.d("위치", "위도, 경도: "+lat+", "+lon);

                        // 각 경로당 마커 위치 정보를 설정하고 지도에 추가
                        Marker marker = new Marker();
                        marker.setPosition(new LatLng(markerLat, markerLon));
                        marker.setMap(naverMap);
                        marker.setIcon(MarkerIcons.BLACK);
                        marker.setIconTintColor(Color.parseColor("#4B6EFD")); // 다중 마커 색상
                        marker.setWidth(70);
                        marker.setHeight(100);

                        //지도 줌 레벨
                        UiSettings uiSettings = naverMap.getUiSettings();
                        float ZoomLevel = (float) naverMap.getCameraPosition().zoom;
                        uiSettings.setZoomControlEnabled(true); // 줌 컨트롤 활성화
                        naverMap.addOnCameraChangeListener((i, b) -> {
                            Log.d("줌", "onMapReady: "+ZoomLevel);
                        });

                    }

                    //경로당 리스트(리사이클러뷰)
                    GpsAdapter adapter = new GpsAdapter(list);
                    binding.recvGps.setAdapter(adapter);
                    binding.recvGps.setLayoutManager(new LinearLayoutManager(getContext()));
                });

            }
        });


    }





}

