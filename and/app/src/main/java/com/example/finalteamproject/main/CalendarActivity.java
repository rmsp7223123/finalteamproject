package com.example.finalteamproject.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finalteamproject.ChangeStatusBar;
import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivityCalendarBinding;

public class CalendarActivity extends AppCompatActivity {
    ActivityCalendarBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCalendarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        new ChangeStatusBar().changeStatusBarColor(this);
        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });
    }
}