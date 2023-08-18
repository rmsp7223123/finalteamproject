package com.example.finalteamproject.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivityChatPhotoGalleryDetailBinding;

public class ChatPhotoGalleryDetailActivity extends AppCompatActivity {
    ActivityChatPhotoGalleryDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatPhotoGalleryDetailBinding.inflate(getLayoutInflater());
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