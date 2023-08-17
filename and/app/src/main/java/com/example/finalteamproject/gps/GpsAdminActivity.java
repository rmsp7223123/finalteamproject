package com.example.finalteamproject.gps;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
            AlertDialog.Builder builder = new AlertDialog.Builder(GpsAdminActivity.this);
            builder.setTitle("전송 성공!");
            builder.setMessage("보내주신 의견은 빠르게 검토하겠습니다.");
            builder.setPositiveButton("확인", (dialog, which) -> {
                finish();
            });
            builder.create().show();

        });
    }


}