package com.example.finalteamproject.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.ActivityCheckPasswordBinding;
import com.example.finalteamproject.databinding.ActivityDeletePasswordBinding;
import com.example.finalteamproject.main.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

public class DeletePasswordActivity extends AppCompatActivity {
    ActivityDeletePasswordBinding binding;
    String pw = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeletePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });

        binding.containerFrameTv0.setOnClickListener(v -> {
            pw += "0";
            int length = pw.length();
            updatePasswordImage(length);
        });

        binding.containerFrameTv1.setOnClickListener(v -> {
            pw += "1";
            int length = pw.length();
            updatePasswordImage(length);
        });

        binding.containerFrameTv2.setOnClickListener(v -> {
            pw += "2";
            int length = pw.length();
            updatePasswordImage(length);
        });

        binding.containerFrameTv3.setOnClickListener(v -> {
            pw += "3";
            int length = pw.length();
            updatePasswordImage(length);
        });

        binding.containerFrameTv4.setOnClickListener(v -> {
            pw += "4";
            int length = pw.length();
            updatePasswordImage(length);
        });

        binding.containerFrameTv5.setOnClickListener(v -> {
            pw += "5";
            int length = pw.length();
            updatePasswordImage(length);
        });

        binding.containerFrameTv6.setOnClickListener(v -> {
            pw += "6";
            int length = pw.length();
            updatePasswordImage(length);
        });

        binding.containerFrameTv7.setOnClickListener(v -> {
            pw += "7";
            int length = pw.length();
            updatePasswordImage(length);
        });

        binding.containerFrameTv8.setOnClickListener(v -> {
            pw += "8";
            int length = pw.length();
            updatePasswordImage(length);
        });

        binding.containerFrameTv9.setOnClickListener(v -> {
            pw += "9";
            int length = pw.length();
            updatePasswordImage(length);
        });

        binding.containerFrameDelete.setOnClickListener(v -> {

            int length = pw.length();
            if (length > 0) {
                pw = pw.substring(0, length - 1);
                updatePasswordImage(length - 1);
            }
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


            CommonConn conn = new CommonConn(this, "setting/inquirePw");
            conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
            conn.onExcute((isResult, data) -> {
                HashMap<String, String> paramMap = new Gson().fromJson(data, new TypeToken<HashMap<String, String>>() {
                }.getType());
                String storedPw = paramMap.get("option_lock_pw");

                if (pw.equals(storedPw)) {
                    CommonConn conn1 = new CommonConn(this, "setting/deletePw");
                    conn1.addParamMap("member_id", CommonVar.logininfo.getMember_id());
                    conn1.onExcute((isResult1, data1) -> {
                        if (isResult1) {
                            Toast.makeText(this, "비밀번호가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "오류 발생", Toast.LENGTH_SHORT).show();
                        }
                    });
                    finish();
                } else {
                    Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            });
            binding.imgvPw1.setImageResource(R.drawable.baseline_circle_24_white);
            binding.imgvPw2.setImageResource(R.drawable.baseline_circle_24_white);
            binding.imgvPw3.setImageResource(R.drawable.baseline_circle_24_white);
            binding.imgvPw4.setImageResource(R.drawable.baseline_circle_24_white);
        } else {
            binding.imgvPw1.setImageResource(R.drawable.baseline_circle_24_white);
            binding.imgvPw2.setImageResource(R.drawable.baseline_circle_24_white);
            binding.imgvPw3.setImageResource(R.drawable.baseline_circle_24_white);
            binding.imgvPw4.setImageResource(R.drawable.baseline_circle_24_white);
        }
    }


}