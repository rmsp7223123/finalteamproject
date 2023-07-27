package com.example.finalteamproject.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.finalteamproject.HideActionBar;
import com.example.finalteamproject.databinding.ActivityMainAlarmHistoryBinding;

public class MainAlarmHistoryActivity extends AppCompatActivity {

    ActivityMainAlarmHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainAlarmHistoryBinding.inflate(getLayoutInflater());
        MainAlarmHistoryAdapter adapter = new MainAlarmHistoryAdapter();
        binding.recvAlarmHistory.setAdapter(adapter);
        binding.recvAlarmHistory.setLayoutManager(new LinearLayoutManager(this));
        //new HideActionBar().hideActionBar(this);
        setContentView(binding.getRoot());
        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });
    }
}