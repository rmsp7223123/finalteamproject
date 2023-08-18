package com.example.finalteamproject.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.databinding.ActivityRankBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class RankActivity extends AppCompatActivity {

    ActivityRankBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRankBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        CommonConn conn = new CommonConn(this, "game/rank");
        conn.onExcute((isResult, data) -> {
            ArrayList<GameVO> list = new Gson().fromJson(data, new TypeToken<ArrayList<GameVO>>(){}.getType());
            RankAdapter adapter = new RankAdapter(list, this);
            binding.recvRank.setAdapter(adapter);
            binding.recvRank.setLayoutManager(new LinearLayoutManager(this));
        });

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
    }

}