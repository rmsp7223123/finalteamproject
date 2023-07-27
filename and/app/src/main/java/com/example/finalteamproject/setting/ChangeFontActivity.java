package com.example.finalteamproject.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivityChangeFontBinding;

public class ChangeFontActivity extends AppCompatActivity {

    ActivityChangeFontBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeFontBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });
        // 글씨 크기 조절, 글씨 색깔 변경 dialog로 띄워서 처리 하기
    }
}