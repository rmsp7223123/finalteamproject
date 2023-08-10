package com.example.finalteamproject.setting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.finalteamproject.Login.LoginActivity;
import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivitySetPatternBinding;
import com.itsxtt.patternlock.PatternLockView;

import java.util.ArrayList;
import java.util.List;

public class SetPatternActivity extends AppCompatActivity {
    ActivitySetPatternBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetPatternBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });
        binding.patternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<com.andrognito.patternlockview.PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<com.andrognito.patternlockview.PatternLockView.Dot> pattern) {
                String pw = PatternLockUtils.patternToString(binding.patternLockView, pattern);
                if(pw.length() < 4) {
                    Toast.makeText(SetPatternActivity.this, "패턴이 너무 짧습니다. 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                    binding.patternLockView.clearPattern();
                } else {
                    Intent intent = new Intent(SetPatternActivity.this, CheckPatternActivity.class);
                    intent.putExtra("pw",pw);
                    startActivity(intent);
                    finish();
                }


            }

            @Override
            public void onCleared() {

            }
        });
    }
}