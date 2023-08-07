package com.example.finalteamproject.gps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
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

        binding.like.setVisibility(View.GONE);
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
        binding.unlike.setOnClickListener(v -> {
            binding.unlike.setVisibility(View.GONE);
            binding.like.setVisibility(View.VISIBLE);
        });
        binding.like.setOnClickListener(v -> {
            binding.like.setVisibility(View.GONE);
            binding.unlike.setVisibility(View.VISIBLE);
        });
        binding.phoneNumber.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:/"+binding.phoneNumber.getText().toString()));
            startActivity(intent);
        });
        binding.tvToadmin.setOnClickListener(v -> {
            Intent intent = new Intent(this, GpsAdminActivity.class);
            startActivity(intent);
        });
    }






}