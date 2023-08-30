package com.example.finalteamproject.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.finalteamproject.ChangeStatusBar;
import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.ActivityDeletePatternBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;

public class DeletePatternActivity extends AppCompatActivity {
    ActivityDeletePatternBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeletePatternBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });
        new ChangeStatusBar().changeStatusBarColor(this);

        binding.patternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<com.andrognito.patternlockview.PatternLockView.Dot> pattern) {
                String pw = PatternLockUtils.patternToString(binding.patternLockView, pattern);
                CommonConn conn = new CommonConn(DeletePatternActivity.this, "setting/inquirePattern");
                conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
                conn.onExcute((isResult, data) -> {
                    HashMap<String, String> paramMap = new Gson().fromJson(data, new TypeToken<HashMap<String, String>>() {
                    }.getType());
                    String storedPattern = paramMap.get("option_lock_pattern_pw");
                    if(pw.equals(storedPattern)) {
                        CommonConn conn1 = new CommonConn(DeletePatternActivity.this, "setting/deletePattern");
                        conn1.addParamMap("member_id", CommonVar.logininfo.getMember_id());
                        conn1.onExcute((isResult1, data1) -> {
                            if (isResult1) {
                                Toast.makeText(DeletePatternActivity.this, "패턴이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(DeletePatternActivity.this, "오류 발생", Toast.LENGTH_SHORT).show();
                            }
                        });
                        finish();
                    } else {
                        Toast.makeText(DeletePatternActivity.this, "패턴이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCleared() {

            }
        });
    }
}