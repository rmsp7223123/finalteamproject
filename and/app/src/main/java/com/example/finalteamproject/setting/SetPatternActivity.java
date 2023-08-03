package com.example.finalteamproject.setting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivitySetPatternBinding;
import com.itsxtt.patternlock.PatternLockView;

import java.util.ArrayList;

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
        binding.patternLockView.setOnPatternListener(new PatternLockView.OnPatternListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(@NonNull ArrayList<Integer> arrayList) {

            }

            @Override
            public boolean onComplete(@NonNull ArrayList<Integer> arrayList) {
                return false;
            }
        });
    }
}