package com.example.finalteamproject.gps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivityGpsLikeBinding;

//자주가는 경로당 리스트
public class GpsLikeActivity extends AppCompatActivity {
    ActivityGpsLikeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding=ActivityGpsLikeBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding.recvGpsLike.setLayoutManager(new LinearLayoutManager(this));
        binding.recvGpsLike.setAdapter(new GpsLikeAdapter());

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
    }
}