package com.example.finalteamproject.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivityInCallBinding;

public class InCallActivity extends AppCompatActivity {

    ActivityInCallBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInCallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imgvCalloff.setOnClickListener(view -> {
            finish();
        });

    }
}