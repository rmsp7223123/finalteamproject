package com.example.finalteamproject.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.finalteamproject.HideActionBar;
import com.example.finalteamproject.databinding.ActivityMainAlarmHistoryBinding;
import com.example.finalteamproject.setting.ChangeAlarmActivity;

public class MainAlarmHistoryActivity extends AppCompatActivity {

    ActivityMainAlarmHistoryBinding binding;
    int itemCnt;

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
        binding.imgvAlarmClean.setOnClickListener(view -> {
        });
        itemCnt = adapter.getItemCount();
        if (itemCnt > 0) {
            binding.containerLinearAlarm.setVisibility(View.INVISIBLE);
        }
        // 알람 기록이 있을때 -- > 어댑터 리턴 사이즈가 0이 아닐 때 프레임 레이아웃안에 linear 안보이게 추가하기
    }
}