package com.example.finalteamproject.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivityInCallBinding;

public class InCallActivity extends AppCompatActivity {

    ActivityInCallBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInCallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}