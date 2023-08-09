package com.example.finalteamproject.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivitySetPasswordBinding;

public class SetPasswordActivity extends AppCompatActivity {

    ActivitySetPasswordBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.containerFrameTv0.setOnClickListener(v -> {
            ChangePasswordActivity.password += "0";
            int length =  ChangePasswordActivity.password.length();
            updatePasswordImage(length);
        });

        binding.containerFrameTv1.setOnClickListener(v -> {
            ChangePasswordActivity.password += "1";
            int length =  ChangePasswordActivity.password.length();
            updatePasswordImage(length);
        });

        binding.containerFrameTv2.setOnClickListener(v -> {
            ChangePasswordActivity.password += "2";
            int length =  ChangePasswordActivity.password.length();
            updatePasswordImage(length);
        });

        binding.containerFrameTv3.setOnClickListener(v -> {
            ChangePasswordActivity.password += "3";
            int length =  ChangePasswordActivity.password.length();
            updatePasswordImage(length);
        });

        binding.containerFrameTv4.setOnClickListener(v -> {
            ChangePasswordActivity.password += "4";
            int length =  ChangePasswordActivity.password.length();
            updatePasswordImage(length);
        });

        binding.containerFrameTv5.setOnClickListener(v -> {
            ChangePasswordActivity.password += "5";
            int length =  ChangePasswordActivity.password.length();
            updatePasswordImage(length);
        });

        binding.containerFrameTv6.setOnClickListener(v -> {
            ChangePasswordActivity.password += "6";
            int length =  ChangePasswordActivity.password.length();
            updatePasswordImage(length);
        });

        binding.containerFrameTv7.setOnClickListener(v -> {
            ChangePasswordActivity.password += "7";
            int length =  ChangePasswordActivity.password.length();
            updatePasswordImage(length);
        });

        binding.containerFrameTv8.setOnClickListener(v -> {
            ChangePasswordActivity.password += "8";
            int length =  ChangePasswordActivity.password.length();
            updatePasswordImage(length);
        });

        binding.containerFrameTv9.setOnClickListener(v -> {
            ChangePasswordActivity.password += "9";
            int length =  ChangePasswordActivity.password.length();
            updatePasswordImage(length);
        });

        binding.containerFrameDelete.setOnClickListener(v -> {

            int length =  ChangePasswordActivity.password.length();
            if (length > 0) {
                ChangePasswordActivity.password =  ChangePasswordActivity.password.substring(0, length - 1);
                updatePasswordImage(length - 1);
            }
        });

        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });
    }

    private void updatePasswordImage(int length) {
        if (length == 1) {
            binding.imgvPw1.setImageResource(R.drawable.baseline_circle_24);
            binding.imgvPw2.setImageResource(R.drawable.baseline_circle_24_white);
            binding.imgvPw3.setImageResource(R.drawable.baseline_circle_24_white);
            binding.imgvPw4.setImageResource(R.drawable.baseline_circle_24_white);
        } else if (length == 2) {
            binding.imgvPw2.setImageResource(R.drawable.baseline_circle_24);
            binding.imgvPw3.setImageResource(R.drawable.baseline_circle_24_white);
            binding.imgvPw4.setImageResource(R.drawable.baseline_circle_24_white);
        } else if (length == 3) {
            binding.imgvPw3.setImageResource(R.drawable.baseline_circle_24);
            binding.imgvPw4.setImageResource(R.drawable.baseline_circle_24_white);
        } else if (length == 4) {
            binding.imgvPw4.setImageResource(R.drawable.baseline_circle_24);
            Toast.makeText(this,  ChangePasswordActivity.password+" 확인용 ", Toast.LENGTH_SHORT).show();
        } else {
            binding.imgvPw1.setImageResource(R.drawable.baseline_circle_24_white);
            binding.imgvPw2.setImageResource(R.drawable.baseline_circle_24_white);
            binding.imgvPw3.setImageResource(R.drawable.baseline_circle_24_white);
            binding.imgvPw4.setImageResource(R.drawable.baseline_circle_24_white);
        }
    }
}