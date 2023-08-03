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
                SharedPreferences sharedPreferences = getSharedPreferences("PREFS", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("password", PatternLockUtils.patternToString(binding.patternLockView, pattern));
                editor.apply();
//                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(intent);
                Toast.makeText(SetPatternActivity.this, sharedPreferences.getString("password","0") + " 확인용 ", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onCleared() {

            }
        });
    }
}