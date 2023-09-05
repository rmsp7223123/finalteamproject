package com.example.finalteamproject.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.example.finalteamproject.databinding.ActivityChatPhotoGalleryBinding;

import java.util.ArrayList;

public class ChatPhotoGalleryActivity extends AppCompatActivity {
    ActivityChatPhotoGalleryBinding binding;

    PhotoGridvAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatPhotoGalleryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ArrayList<String> imgList = (ArrayList<String>) getIntent().getSerializableExtra("img");
        adapter = new PhotoGridvAdapter(getLayoutInflater(),this,imgList );
        binding.gridvPhoto.setAdapter(adapter);
        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });
    }
}