package com.example.finalteamproject.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.finalteamproject.HideActionBar;
import com.example.finalteamproject.Login.LockScreenPasswordActivity;
import com.example.finalteamproject.Login.LockScreenPatternActivity;
import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.common.CustomTextview;
import com.example.finalteamproject.common.MemberVO;
import com.example.finalteamproject.main.MainActivity;
import com.example.finalteamproject.Login.LoginActivity;
import com.example.finalteamproject.databinding.ActivitySplashBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;

    String storedPw = "";
    String storedPattern = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        new Handler().postDelayed(() -> {
            SharedPreferences pref = getSharedPreferences("loginInfo", MODE_PRIVATE);
            if (pref.getString("loginInfo", null) == null) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                CommonConn conn = new CommonConn(this, "login/checkId");
                conn.addParamMap("member_id", pref.getString("loginInfo", "null"));
                conn.onExcute((isResult, data) -> {
                    CommonVar.logininfo = new Gson().fromJson(data, MemberVO.class);
                    if (CommonVar.logininfo != null) {
                        getToken();
                        checkOption();
                        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
                        float fontSize = preferences.getFloat("font_size", 2); // 기본값 글씨 크기 설정
                        applyFontSize(fontSize);
                        CommonConn conn1 = new CommonConn(this, "setting/inquirePw");
                        conn1.addParamMap("member_id", CommonVar.logininfo.getMember_id());
                        conn1.onExcute((isResult1, data1) -> {
                            if (isResult1 && data1 != null) {
                                HashMap<String, String> paramMap = new Gson().fromJson(data1, new TypeToken<HashMap<String, String>>() {
                                }.getType());
                                if (paramMap != null && paramMap.containsKey("option_lock_pw")) {
                                    storedPw = paramMap.get("option_lock_pw");
                                }
                            }

                            if (!storedPw.isEmpty()) {
                                Intent intent = new Intent(this, LockScreenPasswordActivity.class);
                                startActivity(intent);
                                finish();
                            } else {

                                CommonConn conn2 = new CommonConn(this, "setting/inquirePattern");
                                conn2.addParamMap("member_id", CommonVar.logininfo.getMember_id());
                                conn2.onExcute((isResult2, data2) -> {
                                    if (isResult2 && data2 != null) {
                                        HashMap<String, String> paramMap = new Gson().fromJson(data2, new TypeToken<HashMap<String, String>>() {
                                        }.getType());
                                        if (paramMap != null && paramMap.containsKey("option_lock_pattern_pw")) {
                                            storedPw = paramMap.get("option_lock_pattern_pw");
                                        }
                                    }
                                    if (!storedPw.isEmpty()) {
                                        Intent intent = new Intent(this, LockScreenPatternActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Intent intent = new Intent(this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }
                        });
                    } else {
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }, 2000);


    }

    public void getToken(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        String token = task.getResult();
                        Log.d("토큰", "onComplete: " + token);
                        CommonConn conn = new CommonConn(SplashActivity.this, "main/updateToken");
                        conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
                        conn.addParamMap("member_phone_id", token);
                        conn.onExcute((isResult, data) -> {

                        });

                    }
                });
    }

    public void checkOption() {
        CommonConn conn1 = new CommonConn(this, "setting/viewOption");
        conn1.addParamMap("member_id", CommonVar.logininfo.getMember_id());
        conn1.onExcute((isResult, data) -> {

        });
    }

    private void applyFontSize(float fontSize) {
        if (fontSize == 0) {
            CustomTextview.plusTextSize = 10;
        } else if (fontSize == 1) {
            CustomTextview.plusTextSize = 20;
        } else {
            CustomTextview.plusTextSize = 30;
        }
    }
}
