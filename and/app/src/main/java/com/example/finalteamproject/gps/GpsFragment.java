package com.example.finalteamproject.gps;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.cs.NewCSBoardActivity;
import com.example.finalteamproject.databinding.BottomsheetDetailInfoBinding;
import com.example.finalteamproject.databinding.BottomsheetSearchResultBinding;
import com.example.finalteamproject.databinding.FragmentGpsBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.overlay.PathOverlay;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.util.MarkerIcons;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

//지도 나오는 첫 페이지
public class GpsFragment extends Fragment implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    MapFragment mapFragment;
    private FusedLocationSource locationSource;
    private NaverMap naverMap;
    private double lat, lon;
    private Marker selectedMarker;
    FragmentGpsBinding binding;
    BottomSheetBehavior listhavior;
    BottomSheetBehavior resultbehavior;
     BottomSheetDialog searchDialog;
 //   BottomSheetDialog detailDialog;
       BottomsheetSearchResultBinding searchBinding;
    GpsAdapter adapter;
    ArrayList<Marker> markers = new ArrayList<>();
    //BottomsheetDetailInfoBinding detailInfoBinding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGpsBinding.inflate(inflater, container, false);


        setTransStatus();


        searchDialog = new BottomSheetDialog(getContext(), R.style.DialogCustomTheme);
      //  detailDialog = new BottomSheetDialog(getContext(),R.style.DialogCustomTheme);

        searchBinding = BottomsheetSearchResultBinding.inflate(getLayoutInflater(), null, false);
      //  detailInfoBinding = BottomsheetDetailInfoBinding.inflate(getLayoutInflater(), null, false);


        binding.cardvLikelist.setOnClickListener(v->{
            likelist();
        });
        binding.rlDetail.setOnClickListener(v->{
            if(listhavior.getState() == BottomSheetBehavior.STATE_COLLAPSED){
                listhavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        searchDialog.setContentView(searchBinding.getRoot());
       // detailDialog.setContentView(detailInfoBinding.getRoot());


        listhavior = BottomSheetBehavior.from(binding.lnResult );
        resultbehavior = BottomSheetBehavior.from(binding.lnDetail );
        binding.btnClose.setOnClickListener(v->{
            behaviorSetHide();
        });

        //검색 결과
        binding.btnSearch.setOnClickListener(v -> {

            //검색 결과 데이터
            CommonConn connresult = new CommonConn(getContext(), "gps/search");
            connresult.addParamMap("keyword", binding.gpsSearch.getText());
            connresult.onExcute((isResult, data) -> {
                ArrayList<GpsVO> list = new Gson().fromJson(data, new TypeToken<ArrayList<GpsVO>>(){}.getType());
                adapter= new GpsAdapter(list, this);
                binding.recvSearchResult.setAdapter(adapter);
                binding.recvSearchResult.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.tvSearchResult.setText("검색 결과 ( " + list.size() + ")");
                InputMethodManager manager = (InputMethodManager)getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                if(list.size() != 0){
                    listhavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }else{
                    behaviorSetHide(listhavior , false);
                }

               // searchDialog.show();
            });
        });

        //검색결과 닫기
        searchBinding.btnClose.setOnClickListener(v -> {
            searchDialog.dismiss();
            behaviorSetHide(listhavior , false);
        });
        binding.tvMore.setOnClickListener(v -> {
            likelist();
        });

        //지도 객체 생성
        FragmentManager fm = getChildFragmentManager();
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance(new NaverMapOptions().locationButtonEnabled(true));
            fm.beginTransaction().add(binding.map.getId(), mapFragment).commit();
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
        this.naverMap = naverMap;
        this.naverMap.setLocationSource(locationSource); //내 위치
        this.naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        this.naverMap.getLocationOverlay().setVisible(true);
        this.naverMap.getLocationOverlay().setIcon(OverlayImage.fromResource(R.drawable.logo));
        this.naverMap.getLocationOverlay().setIconWidth(100);
        this.naverMap.getLocationOverlay().setIconHeight(100);
        animateCircle(naverMap.getLocationOverlay());
        this.naverMap.getUiSettings().setScaleBarEnabled(true);

        naverMap.setOnMapClickListener((pointF, latLng) -> {
            behaviorSetHide();
            binding.gpsSearch.setHint(getAddress(latLng.latitude,latLng.longitude));
        });

        naverMap.addOnCameraChangeListener((i, b) -> {
            if(i == CameraUpdate.REASON_GESTURE && b) {
                behaviorSetHide();
            }
        });
        naverMap.addOnLocationChangeListener(new NaverMap.OnLocationChangeListener() {
            @Override
            public void onLocationChange(@NonNull Location location) {
                //내 위치 위도, 경도
                if(lat != location.getLatitude() && lon!=location.getLongitude()) {
                    lat = location.getLatitude();
                    lon = location.getLongitude();
                    binding.gpsSearch.setHint(getAddress(lat,lon));
                }
            }

        });


        //지도 줌 레벨
        naverMap.addOnCameraIdleListener(() -> {
            if(lat == 0 || lon == 0) return;
            float ZoomLevel = (float) naverMap.getCameraPosition().zoom;
            if(CURR_ZOOM_LEVEL ==ZoomLevel ) return; //줌레벨이 클수록 결과값은 적게 나와야 함

            for (int i = 0; i < markers.size(); i++) {
                markers.get(i).setMap(null);
            }
            markers = new ArrayList<>();
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
                for (int i = 0 ; i < list.size() ; i ++) {
                    double markerLat = Double.parseDouble(list.get(i).getSenior_latitude());
                    double markerLon = Double.parseDouble(list.get(i).getSenior_longitude());

                    // 현재 (내)위치에 마커 생성
//                    Marker Imarker = new Marker();
//                    Imarker.setPosition(new LatLng(lat, lon));
//                    Imarker.setMap(naverMap);
                    //마커 디자인
//                    Imarker.setIcon(MarkerIcons.BLACK); //색상
//                    Imarker.setIconTintColor(Color.parseColor("#FE69B7"));
//                    Imarker.setWidth(70); //마커사이즈
//                    Imarker.setHeight(100);

                    Log.d("위치", "위도, 경도: " + lat + ", " + lon);

                    // 각 경로당 마커 위치 정보를 설정하고 지도에 추가
                    markers.add( new Marker() );
                    markers.get(i).setPosition(new LatLng(markerLat, markerLon));
                    markers.get(i).setMap(naverMap);
                    markers.get(i).setIcon(MarkerIcons.BLACK);
                    markers.get(i).setIconTintColor(Color.parseColor("#4B6EFD")); // 다중 마커 색상
                    markers.get(i).setWidth(70);
                    markers.get(i).setHeight(100);
                    final int idx = i ;
                    InfoWindow infoWindow = new InfoWindow(new InfoWindow.DefaultTextAdapter(getContext()) {
                        @NonNull
                        @Override
                        public CharSequence getText(@NonNull InfoWindow infoWindow) {
                            return list.get(idx).getSenior_name();
                        }
                    });
                    infoWindow.setOnClickListener(overlay -> {
                        selectDetail(list.get(idx));
                        return true;
                    });
                    infoWindow.setZIndex(10);
                    infoWindow.setAlpha(0.9f);
                    infoWindow.open( markers.get(i));
                    markers.get(i).setOnClickListener(overlay -> {
                        selectDetail(list.get(idx));
                        return true;
                    });
                }


                //경로당 리스트(리사이클러뷰) 잠깐주석
//                GpsAdapter adapter = new GpsAdapter(list, this);
//                binding.recvGps.setAdapter(adapter);
//                binding.recvGps.setLayoutManager(new LinearLayoutManager(getContext()));
//
//                detailInfoBinding.btnClose2.setOnClickListener(v -> {
//                    detailDialog.dismiss();
//                });
                binding.tvToadmin.setOnClickListener(v -> {
                    Intent intent = new Intent(getContext(), NewCSBoardActivity.class);
                    startActivity(intent);
                });

            });




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
            searchBinding.recvSearchResult.setAdapter(likeadapter);
            searchBinding.recvSearchResult.setLayoutManager(new LinearLayoutManager(getContext()));
            searchBinding.tvSearchResult.setText("자주가는 경로당" + list.size() + " 건 ");
            searchDialog.show();
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


        binding.seniorName.setText(vo.getSenior_name()+"");
       // binding.seniorName.setText(vo.getSenior_name()+"");
        setTextView(binding.seniorAddress , vo.getSenior_roadaddress() , "주소 정보 없음");
        if(setTextView(binding.phoneNumber , vo.getSenior_call() , "전화번호 정보 없음")){
            binding.phoneNumber.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_DIAL,
                        Uri.parse("tel:/"+ binding.phoneNumber.getText().toString()));
                startActivity(intent);
            });
        }
        binding.seniorLike.setText("좋아요 "+vo.getSenior_like_num()+"");

        //좋아요 버튼 활성/비활성화
        setLikeYet(vo);

        //좋아요 : 회색버튼 누르면 빨간색으로 변함
        binding.unlike.setOnClickListener(v1 -> {
            setLike("likebtn" ,vo);
        });

        //좋아요 취소 : 빨간 버튼 누르면 회색으로 변함
        binding.like.setOnClickListener(v1 -> {
            setLike("unlikebtn" ,vo);
        });
        //카메라 이동
        moveCamera(vo.getSenior_latitude() , vo.getSenior_longitude());
        if(adapter != null && adapter.list != null && adapter.list.size() > 0){
            listhavior.setPeekHeight( (int) (getResources().getDimension(R.dimen.peek_height)));


          listhavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        behaviorSetHide(resultbehavior , true);
    }


    public boolean setTextView(TextView tv , String data , String msg){
        if(data == null){
            tv.setText(msg);
            return false;
        }else{
            tv.setText(data);
            return true;
        }
    }

    public void setTransStatus(){
        Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        window.setStatusBarColor(Color.TRANSPARENT);

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);



        int status_height = getActivity().getResources().getIdentifier("status_bar_height", "dimen", "android");

        binding.container.setPadding(
                20,
                getActivity().getResources().getDimensionPixelSize(status_height)+10,
                20,
                0

        );

    }
    public void setLikeYet(GpsVO vo){
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
    }
    public void setLike(String path ,GpsVO vo){

        CommonConn connl = new CommonConn(getContext(), "gps/" + path);
        connl.addParamMap("key", vo.getKey());
        connl.addParamMap("member_id", CommonVar.logininfo.getMember_id());
        connl.onExcute((isResult, data) -> {
            if (path.equals("likebtn")){
                binding.like.setVisibility(View.VISIBLE);
                binding.unlike.setVisibility(View.GONE);
                Toast.makeText(getContext(), "자주가는 경로당이 추가되었습니다.", Toast.LENGTH_SHORT).show();
                binding.seniorLike.setText(data);
            }else {
                binding.like.setVisibility(View.GONE);
                binding.unlike.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "자주가는 경로당이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                binding.seniorLike.setText(data);
            }
            vo.setSenior_like_num(Integer.parseInt(data));

            likelist();
            selectDetail(vo);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    private Animator animator;
    private void animateCircle(@NonNull LocationOverlay locationOverlay) {
        if (animator != null) {
            animator.cancel();
        }
        AnimatorSet animatorSet = new AnimatorSet();
        locationOverlay.setZIndex(300000);
        locationOverlay.setVisible(true);
        locationOverlay.setIcon(OverlayImage.fromResource(R.drawable.logo));
        locationOverlay.setIconWidth(100);
        locationOverlay.setIconHeight(100);
        ObjectAnimator radiusAnimator = ObjectAnimator.ofInt(locationOverlay, "circleRadius",
                0, getResources().getDimensionPixelSize(R.dimen.location_overlay_circle_raduis));
        radiusAnimator.setRepeatCount(ValueAnimator.INFINITE);

        ObjectAnimator colorAnimator = ObjectAnimator.ofInt(locationOverlay, "circleColor",
                Color.argb(127, 148, 186, 250), Color.argb(0, 148, 186, 250));
        colorAnimator.setEvaluator(new ArgbEvaluator());
        colorAnimator.setRepeatCount(ValueAnimator.INFINITE);

        animatorSet.setDuration(2000);
        animatorSet.playTogether(radiusAnimator, colorAnimator);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                locationOverlay.setCircleRadius(100);
            }
        });
        animatorSet.start();

        animator = animatorSet;
    }

    @Override
    public void onPause() {
        super.onPause();
        mapFragment.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapFragment.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapFragment.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapFragment.onDestroy();
    }


    private String getAddress(double lat , double lng) {
        Geocoder geoCoder = new Geocoder(requireContext(), Locale.KOREA);
        ArrayList<Address> address = new ArrayList<>();
        String addressResult = "";

            //세번째 파라미터는 좌표에 대해 주소를 리턴 받는 갯수로
            //한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 최대갯수 설정
            try {
                address = (ArrayList<Address>) geoCoder.getFromLocation(lat , lng , 1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (address.size() > 0) {
                // 주소 받아오기
                addressResult  = address.get(0).getAddressLine(0).toString();

            }

        return addressResult;
    }



    public void behaviorSetHide(){
        listhavior.setPeekHeight(0);
        listhavior.setHideable(true);
        listhavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        resultbehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    public void behaviorSetHide(BottomSheetBehavior behavior , boolean isShow){
        if(isShow){

            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }else{
            behavior.setHideable(true);
            behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }

    }
    //
//    @Override
//    protected void onStart() {
//        super.onStart();
//        mapView.onStart();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        mapView.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mapView.onPause();
//    }

}

