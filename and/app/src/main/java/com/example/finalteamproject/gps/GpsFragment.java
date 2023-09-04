package com.example.finalteamproject.gps;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.finalteamproject.Login.ProgressDialog;
import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.cs.NewCSBoardActivity;
import com.example.finalteamproject.databinding.FragmentGpsBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
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
    private Marker selectedMarker;
    FragmentGpsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGpsBinding.inflate(inflater, container, false);
        binding.lnResult.setVisibility(View.GONE);
        binding.lnDetail.setVisibility(View.GONE);

        //검색 결과
        binding.btnSearch.setOnClickListener(v -> {
            binding.lnResult.setVisibility(View.VISIBLE);
            binding.tvSearchResult.setText("검색결과");
            binding.lnDetail.setVisibility(View.INVISIBLE);

            //검색 결과 데이터
            CommonConn connresult = new CommonConn(getContext(), "gps/search");
            connresult.addParamMap("keyword", binding.gpsSearch.getText());
            connresult.onExcute((isResult, data) -> {
                ArrayList<GpsVO> list = new Gson().fromJson(data, new TypeToken<ArrayList<GpsVO>>(){}.getType());
                GpsAdapter adapter = new GpsAdapter(list, this);
                binding.recvSearchResult.setAdapter(adapter);
                binding.recvSearchResult.setLayoutManager(new LinearLayoutManager(getContext()));
            });
        });

        //검색결과 닫기
                binding.btnClose.setOnClickListener(v -> {
                binding.lnResult.setVisibility(View.GONE);
        });

        //자주 가는 경로당(메인화면 상단부)
        likelist();

        //(자주가는 경로당)더보기 메뉴
        binding.tvMore.setOnClickListener(v -> {
            binding.lnResult.setVisibility(View.VISIBLE);
            binding.tvSearchResult.setText("자주 가는 경로당");

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
    final int DEFAULT_ZOOM_LEVEL = 14;
    int CURR_ZOOM_LEVEL = 0;
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.show();

        this.naverMap = naverMap;
        naverMap.setLocationSource(locationSource); //내 위치
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow); //위치 추적 모드

        //내 위치 위도, 경도 이동
        naverMap.addOnLocationChangeListener(new NaverMap.OnLocationChangeListener() {
            @Override
            public void onLocationChange(@NonNull Location location) {
                //내 위치 위도, 경도
                if(lat != location.getLatitude() && lon!=location.getLongitude()) {
                    lat = location.getLatitude();
                    lon = location.getLongitude();


                }
            }

        });


        //지도 줌 레벨
        naverMap.addOnCameraIdleListener(() -> {

            if(lat == 0 || lon == 0) return;
            float ZoomLevel = (float) naverMap.getCameraPosition().zoom;
            if(CURR_ZOOM_LEVEL ==ZoomLevel ) return; //줌레벨이 클수록 결과값은 적게 나와야 함
            CommonConn conn = new CommonConn(getContext(), "gps/senior");
            conn.addParamMap("senior_latitude", lat);
            conn.addParamMap("senior_longitude", lon);
            CURR_ZOOM_LEVEL = (int) ZoomLevel;
            Log.d("줌 카메라", "onCameraChange: " + ZoomLevel);

            int sendZoomLevel = 0 ;

            if(ZoomLevel == DEFAULT_ZOOM_LEVEL){
                //기본 // 14->
                sendZoomLevel= DEFAULT_ZOOM_LEVEL ;
            }else if(DEFAULT_ZOOM_LEVEL - ZoomLevel < 0 ){ //줌 레벨이 큼(적은 결과)
                sendZoomLevel = (int) (DEFAULT_ZOOM_LEVEL - ((ZoomLevel - DEFAULT_ZOOM_LEVEL) * 5));

            }else if(DEFAULT_ZOOM_LEVEL - ZoomLevel > 0){ //줌 레벨이 작음(많은 결과)
                sendZoomLevel = (int) (DEFAULT_ZOOM_LEVEL - ((ZoomLevel - DEFAULT_ZOOM_LEVEL) * 5)+2);

            }

            if(sendZoomLevel < 5){
                sendZoomLevel = 5;
            }

            conn.addParamMap("zoom_level", sendZoomLevel);
            Log.d("줌 레벨", "onMapReady: " + sendZoomLevel);


            int finalSendZoomLevel = sendZoomLevel;
            conn.onExcute((isResult, data) -> {
                ArrayList<GpsVO> list = new Gson().fromJson(data, new TypeToken<ArrayList<GpsVO>>() {
                }.getType());
                if (list == null) {
                    Log.e("줌 레벨", "error: " + finalSendZoomLevel);
                    return;
                }
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

                    Log.d("위치", "위도, 경도: " + lat + ", " + lon);

                    // 각 경로당 마커 위치 정보를 설정하고 지도에 추가
                    Marker marker = new Marker();
                    marker.setPosition(new LatLng(markerLat, markerLon));
                    marker.setMap(naverMap);
                    marker.setIcon(MarkerIcons.BLACK);
                    marker.setIconTintColor(Color.parseColor("#4B6EFD")); // 다중 마커 색상
                    marker.setWidth(70);
                    marker.setHeight(100);
                    marker.setOnClickListener(overlay -> {
                        selectDetail(gpsVO);
                        return true;
                    });
                }


                //경로당 리스트(리사이클러뷰)
                GpsAdapter adapter = new GpsAdapter(list, this);
                binding.recvGps.setAdapter(adapter);
                binding.recvGps.setLayoutManager(new LinearLayoutManager(getContext()));

                binding.btnClose2.setOnClickListener(v -> {
                    binding.lnDetail.setVisibility(View.GONE);
                });
//                binding.phoneNumber.setOnClickListener(v -> {
//                        Intent intent = new Intent(Intent.ACTION_DIAL,
//                                Uri.parse("tel:/"+binding.phoneNumber.getText().toString()));
//                        startActivity(intent);
//                });
                binding.tvToadmin.setOnClickListener(v -> {
                    Intent intent = new Intent(getContext(), NewCSBoardActivity.class);
                    startActivity(intent);
                });

            });


            dialog.dismiss();


        });

    }



    public void likelist(){
        CommonConn conn = new CommonConn(getContext(), "gps/likelist");
        conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
        conn.onExcute((isResult, data) -> {
            ArrayList<GpsVO> list = new Gson().fromJson(data, new TypeToken<ArrayList<GpsVO>>(){}.getType());

            GpsBmarkAdapter adapter = new GpsBmarkAdapter(list);
            binding.recvBmark.setAdapter(adapter);
            binding.recvBmark.setLayoutManager(new LinearLayoutManager(getContext()));

            GpsLikeAdapter likeadapter = new GpsLikeAdapter(list,this);
            binding.recvSearchResult.setAdapter(likeadapter);
            binding.recvSearchResult.setLayoutManager(new LinearLayoutManager(getContext()));
        });
    }

public void moveCamera(String lat , String log){
        double latitude = Double.parseDouble(lat);
        double longitude = Double.parseDouble(log);

        if (selectedMarker != null){
            selectedMarker.setIconTintColor(Color.parseColor("#4B6EFD"));
        }

    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(latitude, longitude));
    naverMap.moveCamera(cameraUpdate);
    Marker marker = new Marker();
    marker.setPosition(new LatLng(latitude, longitude));
    marker.setMap(naverMap);
    marker.setIcon(MarkerIcons.BLACK);
    marker.setIconTintColor(Color.parseColor("#27D829")); //마커 색상
    marker.setWidth(70);
    marker.setHeight(100);

    selectedMarker = marker;
    selectedMarker.setIconTintColor(Color.parseColor("#27D829"));

}


    public void selectDetail(GpsVO vo) {
        binding.lnDetail.setVisibility(View.VISIBLE);
        CommonConn conn = new CommonConn(getContext(), "gps/detail");
        conn.addParamMap("key", vo.getKey());
        binding.seniorName.setText(vo.getSenior_name()+"");
        if (vo.getSenior_roadaddress() == null){
            binding.seniorAddress.setText("주소 정보 없음");
        }else {
            binding.seniorAddress.setText(vo.getSenior_roadaddress()+"");
        }
        if(vo.getSenior_call() == null){
            binding.phoneNumber.setText("전화번호 정보 없음");
            binding.phoneNumber.setOnClickListener(v -> {
                Toast.makeText(getContext(), "전화번호가 없습니다.", Toast.LENGTH_SHORT).show();
            });
        }else {
            binding.phoneNumber.setText(vo.getSenior_call()+"");
            binding.phoneNumber.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:/"+binding.phoneNumber.getText().toString()));
                startActivity(intent);
            });
        }
        binding.seniorLike.setText("좋아요 "+vo.getSenior_like_num()+"");
        //     if(vo.getSenior_call() == null){ => select nvl(adress , '주소 정보 없음')

        //좋아요 버튼 활성/비활성화
        CommonConn connlike = new CommonConn(getContext(), "gps/likeyet");
        connlike.addParamMap("member_id", CommonVar.logininfo.getMember_id());
        connlike.addParamMap("key", vo.getKey());
        connlike.onExcute((isResult, data) -> {
            if (data.equals(vo.getKey()+"")){
                binding.like.setVisibility(View.VISIBLE);
                binding.unlike.setVisibility(View.GONE);
            }else {
                binding.like.setVisibility(View.GONE);
                binding.unlike.setVisibility(View.VISIBLE);
            }
        });

        //좋아요 : 회색버튼 누르면 빨간색으로 변함
        binding.unlike.setOnClickListener(v1 -> {
            CommonConn connl = new CommonConn(v1.getContext(), "gps/likebtn");
            connl.addParamMap("key", vo.getKey());
            connl.addParamMap("member_id", CommonVar.logininfo.getMember_id());
            connl.onExcute((isResult, data) -> {
                binding.unlike.setVisibility(View.GONE);
                binding.like.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "자주가는 경로당이 추가되었습니다.", Toast.LENGTH_SHORT).show();

                likelist();
                selectDetail(vo);
            });
        });

        //좋아요 취소 : 빨간 버튼 누르면 회색으로 변함
        binding.like.setOnClickListener(v1 -> {
            CommonConn connul = new CommonConn(v1.getContext(), "gps/unlikebtn");
            connul.addParamMap("key", vo.getKey());
            connul.addParamMap("member_id", CommonVar.logininfo.getMember_id());
            connul.onExcute((isResult, data) -> {
                binding.like.setVisibility(View.GONE);
                binding.unlike.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "자주가는 경로당이 삭제되었습니다.", Toast.LENGTH_SHORT).show();

                likelist();
                selectDetail(vo);
            });
        });

        //카메라 이동
        moveCamera(vo.getSenior_latitude() , vo.getSenior_longitude());
    }

}

