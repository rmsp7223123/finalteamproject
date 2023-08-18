package com.example.finalteamproject.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.common.MemberVO;
import com.example.finalteamproject.databinding.ActivityFindLockPwactivityBinding;
import com.example.finalteamproject.main.MainActivity;
import com.example.finalteamproject.setting.ChangePasswordActivity;
import com.google.gson.Gson;

public class FindLockPWActivity extends AppCompatActivity {

    ActivityFindLockPwactivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFindLockPwactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });

        binding.tvFindId.setOnClickListener(v -> {
            Intent intent  = new Intent(this, LoginFindIdActivity.class);
            intent.putExtra("phoneNumber", CommonVar.logininfo.getMember_phone());
            startActivity(intent);
        });

        binding.tvFindPw.setOnClickListener(v -> {
            Intent intent  = new Intent(this, LoginFindPwActivity.class);
            intent.putExtra("phoneNumber", CommonVar.logininfo.getMember_phone());
            startActivity(intent);
        });

        InputMethodManager manager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        binding.cvNext.setOnClickListener(v -> {
            if(binding.edtId.getText().toString().length()<1){
                Toast.makeText(this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
                binding.edtId.requestFocus();
                manager.showSoftInput(binding.edtId, InputMethodManager.SHOW_IMPLICIT);
            }else if(binding.edtPw.getText().toString().length()<1){
                Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                binding.edtPw.requestFocus();
                manager.showSoftInput(binding.edtPw, InputMethodManager.SHOW_IMPLICIT);
            }else {
                CommonConn conn = new CommonConn(this, "login/check");
                Intent intent1 = getIntent();
                conn.addParamMap("member_phone", CommonVar.logininfo.getMember_phone());
                conn.addParamMap("member_id", binding.edtId.getText().toString());
                conn.addParamMap("member_pw", binding.edtPw.getText().toString());
                conn.onExcute((isResult, data) -> {
                    CommonVar.logininfo = new Gson().fromJson(data, MemberVO.class);
                    if(CommonVar.logininfo!=null){
                        Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show();
                        SharedPreferences pref = getSharedPreferences("loginInfo", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("loginInfo", binding.edtId.getText().toString());
                        editor.commit();
                        Intent intent = new Intent(this, ChangePasswordActivity.class);
                        intent.putExtra("delete", 1000);
                        startActivity(intent);
                    }else {
                        Toast.makeText(this, "로그인 실패\n아이디나 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}