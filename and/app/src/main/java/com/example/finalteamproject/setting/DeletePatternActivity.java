package com.example.finalteamproject.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivityDeletePatternBinding;

public class DeletePatternActivity extends AppCompatActivity {
    ActivityDeletePatternBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeletePatternBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}