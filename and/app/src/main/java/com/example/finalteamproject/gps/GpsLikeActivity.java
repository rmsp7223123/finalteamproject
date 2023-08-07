package com.example.finalteamproject.gps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.databinding.ActivityGpsLikeBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

//자주가는 경로당 리스트
public class GpsLikeActivity extends AppCompatActivity {
    ActivityGpsLikeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding=ActivityGpsLikeBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        senior_like();

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
    }

    public void senior_like(){
        CommonConn conn = new CommonConn(this, "gps/like");
        conn.onExcute((isResult, data) -> {
            ArrayList<GpsVO> list = new Gson().fromJson(data, new TypeToken<ArrayList<GpsVO>>(){}.getType());

            //자주 가는 경로당(리사이클러뷰)
            GpsLikeAdapter adapter = new GpsLikeAdapter(list);
            binding.recvGpsLike.setAdapter(adapter);
            binding.recvGpsLike.setLayoutManager(new LinearLayoutManager(this));

        });
    }



}