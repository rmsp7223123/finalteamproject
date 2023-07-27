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

    int godokcnt= 0;
    int call_message_cnt=0;
    
    private static final String PREF_NAME = "AlarmSettings";

    private static final String GODOK_ALARM_KEY = "godok_alarm";
    private static final String CALL_MESSAGE_ALARM_KEY = "call_message_alarm";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeAlarmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loadAlarmSettings();

        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });
        binding.tvGodokAlarmChange.setOnClickListener(v -> {
            if(godokcnt % 2 == 0) {
            binding.tvGodokAlarmChange.setText("켜짐");
                Toast.makeText(this, "고독사 알람이 켜졌습니다.", Toast.LENGTH_SHORT).show();
            } else {
                binding.tvGodokAlarmChange.setText("꺼짐");
                Toast.makeText(this, "고독사 알람이 꺼졌습니다.", Toast.LENGTH_SHORT).show();
            }
            godokcnt ++;
            saveAlarmSettings();
        });
        binding.tvCallMessageAlarmChange.setOnClickListener(v -> {
            if(call_message_cnt % 2 == 0) {
                binding.tvCallMessageAlarmChange.setText("켜짐");
                Toast.makeText(this, "전화, 문자 알람이 켜졌습니다.", Toast.LENGTH_SHORT).show();
            } else {
                binding.tvCallMessageAlarmChange.setText("꺼짐");
                Toast.makeText(this, "전화, 문자 알람이 꺼졌습니다.", Toast.LENGTH_SHORT).show();
            }
            call_message_cnt ++;
            saveAlarmSettings();
        });

        if(binding.tvGodokAlarmChange.getText().toString().equals("꺼짐") && binding.tvCallMessageAlarmChange.getText().toString().equals("꺼짐")) {
            // 둘다 꺼짐 일 경우 알람 히스토리 사이즈가 0으로 바뀌게 변경하기
        }
        // 알람 눌렀을 때 끄기 켜기 텍스트 전환 및 토스트로 변경 됐다고 알려주기 추가
    }

    private void saveAlarmSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(GODOK_ALARM_KEY, godokcnt % 2 == 0);
        editor.putBoolean(CALL_MESSAGE_ALARM_KEY, call_message_cnt % 2 == 0);

        editor.apply();
    }

    private void loadAlarmSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        boolean isGodokAlarmOn = sharedPreferences.getBoolean(GODOK_ALARM_KEY, false);
        boolean isCallMessageAlarmOn = sharedPreferences.getBoolean(CALL_MESSAGE_ALARM_KEY, false);

        if(isGodokAlarmOn) {
            binding.tvGodokAlarmChange.setText("켜짐");
        } else {
            binding.tvGodokAlarmChange.setText("꺼짐");
        }

        if(isCallMessageAlarmOn) {
            binding.tvCallMessageAlarmChange.setText("켜짐");
        } else {
            binding.tvCallMessageAlarmChange.setText("꺼짐");
        }

    }
}