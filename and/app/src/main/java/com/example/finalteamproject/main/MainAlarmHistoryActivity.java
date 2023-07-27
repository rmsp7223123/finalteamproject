package com.example.finalteamproject.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finalteamproject.HideActionBar;
import com.example.finalteamproject.databinding.ActivityMainAlarmHistoryBinding;

public class MainAlarmHistoryActivity extends AppCompatActivity {

    ActivityMainAlarmHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainAlarmHistoryBinding.inflate(getLayoutInflater());
        new HideActionBar().hideActionBar(this);
        setContentView(binding.getRoot());
    }
}