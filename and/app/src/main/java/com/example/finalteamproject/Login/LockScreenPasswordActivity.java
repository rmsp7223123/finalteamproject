package com.example.finalteamproject.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finalteamproject.databinding.ActivityLockScreenPasswordBinding;

public class LockScreenPasswordActivity extends AppCompatActivity {

    ActivityLockScreenPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityLockScreenPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}