package com.example.finalteamproject.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivityChatPhotoDetailBinding;

import java.util.ArrayList;

public class ChatPhotoDetailActivity extends AppCompatActivity {


    ActivityChatPhotoDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatPhotoDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });
        String imageUri = getIntent().getStringExtra("image");
        if(imageUri.contains("https://firebasestorage.googleapis.com/")) {
        Glide.with(this).load(imageUri).into(binding.imgvImage);
        }
    }
}