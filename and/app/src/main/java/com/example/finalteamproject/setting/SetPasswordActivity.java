package com.example.finalteamproject.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivitySetPasswordBinding;

public class SetPasswordActivity extends AppCompatActivity {

    ActivitySetPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.containerLinearPassword.setOnClickListener(v -> {
            binding.imgvPw1.setImageResource(R.drawable.baseline_circle_24);
        });
        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });
    }
}