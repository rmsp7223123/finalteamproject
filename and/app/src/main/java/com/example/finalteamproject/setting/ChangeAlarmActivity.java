package com.example.finalteamproject.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivityChangeAlarmBinding;
import com.example.finalteamproject.main.MainAlarmHistoryAdapter;

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
        // 기본 알람을 껐을때 알람창 데이터 삭제하기
    }

}