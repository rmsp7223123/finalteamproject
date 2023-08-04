package com.example.finalteamproject.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivityLockScreenPatternBinding;

public class LockScreenPatternActivity extends AppCompatActivity {

    ActivityLockScreenPatternBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLockScreenPatternBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}