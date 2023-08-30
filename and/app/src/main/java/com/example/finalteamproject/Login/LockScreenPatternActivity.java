package com.example.finalteamproject.Login;

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
import com.example.finalteamproject.databinding.ActivityLockScreenPatternBinding;
import com.example.finalteamproject.main.MainActivity;
import com.example.finalteamproject.setting.CheckPatternActivity;
import com.example.finalteamproject.setting.SetPatternActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;

public class LockScreenPatternActivity extends AppCompatActivity {

    ActivityLockScreenPatternBinding binding;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLockScreenPatternBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        new ChangeStatusBar().changeStatusBarColor(this);

        //비밀번호 찾기
        binding.tvFindPw.setOnClickListener(v -> {
            Intent intent = new Intent(this, FindLockPWActivity.class);
            startActivity(intent);
        });

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
                CommonConn conn = new CommonConn(LockScreenPatternActivity.this, "setting/inquirePattern");
                conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
                conn.onExcute((isResult, data) -> {
                    HashMap<String, String> paramMap = new Gson().fromJson(data, new TypeToken<HashMap<String, String>>() {
                    }.getType());
                    String storedPattern = paramMap.get("option_lock_pattern_pw");
                        if(pw.equals(storedPattern)) {
                            Intent intent = new Intent(LockScreenPatternActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            binding.patternLockView.clearPattern();
                            count++;
                            if(count<5){
                                Toast.makeText(LockScreenPatternActivity.this, "패턴이 틀렸습니다. ("+count+"회)", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(LockScreenPatternActivity.this, "패턴 시도 5회 초과", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LockScreenPatternActivity.this, FindLockPWActivity.class);
                                startActivity(intent);
                            }
                        }

                });
            }

            @Override
            public void onCleared() {

            }
        });
    }
}