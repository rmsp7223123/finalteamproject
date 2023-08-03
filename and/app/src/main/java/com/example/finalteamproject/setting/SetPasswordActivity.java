package com.example.finalteamproject.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivitySetPasswordBinding;

public class SetPasswordActivity extends AppCompatActivity {

    ActivitySetPasswordBinding binding;

    // public String[] password = null;
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.containerFrameTv0.setOnClickListener(v -> {
            password += "0";
            int length = password.length();
            updatePasswordImage(length);
        });

        binding.containerFrameTv1.setOnClickListener(v -> {
            password += "1";
            int length = password.length();
            updatePasswordImage(length);
        });

        binding.containerFrameTv2.setOnClickListener(v -> {
            password += "2";
            int length = password.length();
            updatePasswordImage(length);
        });

        binding.containerFrameTv3.setOnClickListener(v -> {
            password += "3";
            int length = password.length();
            updatePasswordImage(length);
        });

        binding.containerFrameTv4.setOnClickListener(v -> {
            password += "4";
            int length = password.length();
            updatePasswordImage(length);
        });

        binding.containerFrameTv5.setOnClickListener(v -> {
            password += "5";
            int length = password.length();
            updatePasswordImage(length);
        });

        binding.containerFrameTv6.setOnClickListener(v -> {
            password += "6";
            int length = password.length();
            updatePasswordImage(length);
        });

        binding.containerFrameTv7.setOnClickListener(v -> {
            password += "7";
            int length = password.length();
            updatePasswordImage(length);
        });

        binding.containerFrameTv8.setOnClickListener(v -> {
            password += "8";
            int length = password.length();
            updatePasswordImage(length);
        });

        binding.containerFrameTv9.setOnClickListener(v -> {
            password += "9";
            int length = password.length();
            updatePasswordImage(length);
        });

        binding.containerFrameDelete.setOnClickListener(v -> {

            int length = password.length();
            if (length > 0) {
                password = password.substring(0, length - 1);
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
        } else {
            binding.imgvPw1.setImageResource(R.drawable.baseline_circle_24_white);
            binding.imgvPw2.setImageResource(R.drawable.baseline_circle_24_white);
            binding.imgvPw3.setImageResource(R.drawable.baseline_circle_24_white);
            binding.imgvPw4.setImageResource(R.drawable.baseline_circle_24_white);
        }
    }
}