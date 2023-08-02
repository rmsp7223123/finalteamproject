package com.example.finalteamproject.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivityChangePasswordBinding;

public class ChangePasswordActivity extends AppCompatActivity {
    ActivityChangePasswordBinding binding;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });
        // 비밀번호나 패턴이 있을때 어떻게 할지 추가 및 수정하기
        binding.btnPassword.setOnClickListener(view -> {
            intent = new Intent(this, SetPasswordActivity.class);
            startActivity(intent);
        });
        binding.btnPattern.setOnClickListener(view -> {
            intent = new Intent(this, SetPatternActivity.class);
            startActivity(intent);
        });
    }
}