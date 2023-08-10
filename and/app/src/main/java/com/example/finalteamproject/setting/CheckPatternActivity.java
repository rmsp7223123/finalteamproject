package com.example.finalteamproject.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.ActivityCheckPatternBinding;

import java.util.List;

public class CheckPatternActivity extends AppCompatActivity {
    ActivityCheckPatternBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckPatternBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.patternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<com.andrognito.patternlockview.PatternLockView.Dot> pattern) {
                String originalPw = getIntent().getStringExtra("pw");
                String pw2 = PatternLockUtils.patternToString(binding.patternLockView, pattern);
                if (pw2.equals(originalPw)) {
                    CommonConn conn = new CommonConn(CheckPatternActivity.this, "setting/insertPattern");
                    conn.addParamMap("option_lock_pattern_pw", pw2);
                    conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
                    conn.onExcute((isResult, data) -> {
                        if (isResult) {
                            Toast.makeText(CheckPatternActivity.this, "패턴이 설정되었습니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CheckPatternActivity.this, "오류", Toast.LENGTH_SHORT).show();
                        }
                    });
                    finish();
                } else {
                    Toast.makeText(CheckPatternActivity.this, "패턴이 일치하지 않습니다. 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CheckPatternActivity.this, SetPatternActivity.class);
                    startActivity(intent);
                    finish();
                }

            }

            @Override
            public void onCleared() {

            }
        });
    }
}