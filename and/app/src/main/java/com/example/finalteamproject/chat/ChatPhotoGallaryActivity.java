package com.example.finalteamproject.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivityChatPhotoGallaryBinding;

import java.util.ArrayList;

public class ChatPhotoGallaryActivity extends AppCompatActivity {
    ActivityChatPhotoGallaryBinding binding;

    PhotoGridvAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatPhotoGallaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ArrayList<String> imgList = (ArrayList<String>) getIntent().getSerializableExtra("img");//getIntent().getSerializableExtra("img")
        adapter = new PhotoGridvAdapter(getLayoutInflater(),this,imgList );
        binding.gridvPhoto.setAdapter(adapter);
        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });
    }
}