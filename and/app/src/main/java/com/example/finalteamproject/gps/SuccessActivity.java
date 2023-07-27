package com.example.finalteamproject.gps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivitySuccessBinding;

//관리자에게 전송 성공 메시지
//닫기 기능 작업중
public class SuccessActivity extends AppCompatActivity {
    ActivitySuccessBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = ActivitySuccessBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
        binding.btnReturn.setOnClickListener(v -> {
            finish();
        });
    }
}