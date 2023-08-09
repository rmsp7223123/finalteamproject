package com.example.finalteamproject.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivityChangePasswordBinding;

public class ChangePasswordActivity extends AppCompatActivity {
    ActivityChangePasswordBinding binding;
    Intent intent;

    public static String password = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });
        binding.btnPassword.setOnClickListener(view -> {
            intent = new Intent(this, SetPasswordActivity.class);
            startActivity(intent);
        });
        binding.btnPattern.setOnClickListener(view -> {
            intent = new Intent(this, SetPatternActivity.class);
            startActivity(intent);
        });
        binding.btnDeletePassword.setOnClickListener(v -> {
            if(password.length() > 4) {
                // 패턴
            } else if (!password.equals("") && password.length()<5){
                // 비밀번호
            } else {
                Toast.makeText(this, "비밀번호가 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        // 비밀번호가 있는경우 비밀번호 재입력, 패턴이 있는경우 패턴 재입력 후 비밀번호 삭제되게 추가하기
    }
}