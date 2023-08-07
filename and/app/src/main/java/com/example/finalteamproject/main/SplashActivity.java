package com.example.finalteamproject.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.finalteamproject.HideActionBar;
import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.common.MemberVO;
import com.example.finalteamproject.main.MainActivity;
import com.example.finalteamproject.Login.LoginActivity;
import com.example.finalteamproject.databinding.ActivitySplashBinding;
import com.google.gson.Gson;

public class SplashActivity extends AppCompatActivity {

    ActivitySplashBinding binding;


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
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }, 2000);


        new Handler().postDelayed(() -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 2000);

//        new Handler().postDelayed(() -> {
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//            finish();
//        }, 2000);

    }
//        new Handler().postDelayed(() -> {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }, 2000);

//            new Handler().postDelayed(() -> {
//                Intent intent = new Intent(this, LoginActivity.class);
//                startActivity(intent);
//                finish();
//            }, 2000);
}
