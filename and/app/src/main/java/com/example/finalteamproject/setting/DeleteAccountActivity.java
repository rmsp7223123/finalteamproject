package com.example.finalteamproject.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivityDeleteAccountBinding;

public class DeleteAccountActivity extends AppCompatActivity {
    ActivityDeleteAccountBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeleteAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });
    }
}