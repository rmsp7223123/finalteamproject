package com.example.finalteamproject.gps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivityGpsDetailBinding;

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
        binding.tvCall.setOnClickListener(v -> {
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