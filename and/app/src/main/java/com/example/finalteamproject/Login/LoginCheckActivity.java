package com.example.finalteamproject.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.common.MemberVO;
import com.example.finalteamproject.databinding.ActivityLoginCheckBinding;
import com.example.finalteamproject.main.MainActivity;
import com.google.gson.Gson;

public class LoginCheckActivity extends AppCompatActivity {

    ActivityLoginCheckBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginCheckBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgvBack.setOnClickListener(v -> {
            finish();
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
                conn.addParamMap("member_id", binding.edtId.getText().toString());
                conn.addParamMap("member_pw", binding.edtPw.getText().toString());
                conn.onExcute((isResult, data) -> {
                    CommonVar.logininfo = new Gson().fromJson(data, MemberVO.class);
                    if(CommonVar.logininfo!=null){
                        Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(this, "로그인 실패\n아이디나 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}