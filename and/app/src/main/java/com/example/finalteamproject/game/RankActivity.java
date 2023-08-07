package com.example.finalteamproject.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivityRankBinding;

public class RankActivity extends AppCompatActivity {

    ActivityRankBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRankBinding.inflate(getLayoutInflater());

        binding.recvRank.setAdapter(new RankAdapter());
        binding.recvRank.setLayoutManager(new LinearLayoutManager(this));
        binding.btnBack.setOnClickListener(v -> {
            finish();
        });

        setContentView(binding.getRoot());
    }
}