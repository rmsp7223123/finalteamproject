package com.example.finalteamproject.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivityChangeAlarmBinding;

public class ChangeAlarmActivity extends AppCompatActivity {
    ActivityChangeAlarmBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeAlarmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });
        // 알람 눌렀을 때 끄기 켜기 텍스트 전환 및 토스트로 변경 됐다고 알려주기 추가
    }
}