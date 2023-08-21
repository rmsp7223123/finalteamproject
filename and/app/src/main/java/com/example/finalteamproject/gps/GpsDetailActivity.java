package com.example.finalteamproject.gps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.cs.NewCSBoardActivity;
import com.example.finalteamproject.databinding.ActivityGpsDetailBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

//경로당 상세정보 페이지(전화걸기 등)
public class GpsDetailActivity extends AppCompatActivity {
    ActivityGpsDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding=ActivityGpsDetailBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

//        binding.btnBack.setOnClickListener(v -> {
//            finish();
//        });

        //fragment의 리사이클러뷰에서 받아온 정보를 상세하게 보여줌
        //상세화면 구성
        Intent intenttx = getIntent();
        int senior_key;
        String senior_name, senior_address, senior_phone, senior_like;
        senior_key = intenttx.getIntExtra("senior_key", 0);
        senior_name = intenttx.getStringExtra("senior_name");
        senior_address = intenttx.getStringExtra("senior_address");
        senior_phone = intenttx.getStringExtra("senior_phone");
        senior_like = intenttx.getStringExtra("senior_like");

        binding.seniorName.setText(senior_name);
        binding.seniorAddress.setText(senior_address);
        binding.phoneNumber.setText(senior_phone);
        binding.seniorLike.setText("좋아요 "+senior_like);

        //좋아요 버튼 활성/비활성화
        binding.like.setVisibility(View.INVISIBLE);
        CommonConn conn = new CommonConn(this, "gps/likeyet");
        conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
        conn.addParamMap("key", senior_key);
        conn.onExcute((isResult, data) -> {
            if (data.equals(senior_key+"")){
                binding.like.setVisibility(View.VISIBLE);
            }
        });

        //좋아요 : 회색버튼 누르면 빨간색으로 변함
        binding.unlike.setOnClickListener(v -> {
            CommonConn connl = new CommonConn(v.getContext(), "gps/likebtn");
            connl.addParamMap("member_id", CommonVar.logininfo.getMember_id());
            connl.addParamMap("key", senior_key);
            connl.onExcute((isResult, data) -> {
                binding.unlike.setVisibility(View.GONE);
                binding.like.setVisibility(View.VISIBLE);
            });
        });
        //좋아요 취소 : 빨간 버튼 누르면 회색으로 변함
        binding.like.setOnClickListener(v -> {
            CommonConn connul = new CommonConn(v.getContext(), "gps/unlikebtn");
            connul.addParamMap("member_id", CommonVar.logininfo.getMember_id());
            connul.addParamMap("key", senior_key);
            connul.onExcute((isResult, data) -> {
                binding.like.setVisibility(View.GONE);
                binding.unlike.setVisibility(View.VISIBLE);
            });
        });

        binding.phoneNumber.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:/"+binding.phoneNumber.getText().toString()));
            startActivity(intent);
        });
        binding.tvToadmin.setOnClickListener(v -> {
            Intent intent = new Intent(this, NewCSBoardActivity.class);
            startActivity(intent);
        });

    }




}