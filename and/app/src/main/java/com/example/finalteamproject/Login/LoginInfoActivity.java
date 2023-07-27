package com.example.finalteamproject.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.finalteamproject.HideActionBar;
import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivityLoginInfoBinding;

public class LoginInfoActivity extends AppCompatActivity {

    ActivityLoginInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new HideActionBar().hideActionBar(this);

        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });

        Intent intent = getIntent();

        binding.edtName.setText(intent.getStringExtra("name"));

        binding.cvNext.setOnClickListener(v -> {
            Intent intent1 = new Intent(this, LoginFavorActivity.class);
            startActivity(intent1);
        });

    }
}