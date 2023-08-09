package com.example.finalteamproject.gps;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivityGpsAdminBinding;

//관리자에게 알림 페이지(액티비티)
public class GpsAdminActivity extends AppCompatActivity {
    ActivityGpsAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding=ActivityGpsAdminBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(v -> {
            finish();
        });
        binding.btnSend.setOnClickListener(v -> {

//            Intent intent = new Intent(this, SuccessActivity.class);
//            startActivity(intent);
        });
    }
    
}