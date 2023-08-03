package com.example.finalteamproject.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.finalteamproject.HideActionBar;
import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.databinding.ActivityLoginInfoBinding;

import java.util.regex.Pattern;

public class LoginInfoActivity extends AppCompatActivity {

    ActivityLoginInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        new HideActionBar().hideActionBar(this);

        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });

        binding.edtName.setText(LoginVar.name);

        binding.edtId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Pattern.compile("[A-Z$@$!%*#?&]").matcher(binding.edtId.getText().toString()).find()){
                    binding.tvIdAlert.setVisibility(View.VISIBLE);
                    binding.tvIdAlert.setText("대문자와 특수문자 미포함");
                }else if(!Pattern.compile("[0-9]").matcher(binding.edtId.getText().toString()).find()){
                    binding.tvIdAlert.setVisibility(View.VISIBLE);
                    binding.tvIdAlert.setText("숫자 1글자 이상");
                }else if(!Pattern.compile("[a-z]").matcher(binding.edtId.getText().toString()).find()){
                    binding.tvIdAlert.setVisibility(View.VISIBLE);
                    binding.tvIdAlert.setText("영문 1글자 이상");
                }else if(binding.edtId.getText().toString().length()<5||binding.edtId.getText().toString().length()>15){
                    binding.tvIdAlert.setVisibility(View.VISIBLE);
                    binding.tvIdAlert.setText("5 - 15자");
                }else {
                    binding.tvIdAlert.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.tvIdCheck.setOnClickListener(v -> {
            if(binding.edtId.getText().toString().length()>0&&binding.tvIdAlert.getVisibility()==View.INVISIBLE){
                CommonConn conn = new CommonConn(this, "login/checkId");
                conn.addParamMap("id", binding.edtId.getText().toString());
                conn.onExcute((isResult, data) -> {
                    if(!data.equals("null")){
                        Toast.makeText(this, "이미 존재하는 아이디입니다", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "사용가능한 아이디입니다", Toast.LENGTH_SHORT).show();
                        binding.tvIdCheck.setVisibility(View.GONE);
                        binding.tvIdCheckDone.setVisibility(View.VISIBLE);
                    }
                });
            }else {
                Toast.makeText(this, "조건에 맞는 아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
            }
        });
        binding.tvNicknameCheck.setOnClickListener(v -> {
            if(binding.edtNickname.getText().toString().length()>0){
                CommonConn conn = new CommonConn(this, "login/checkNickname");
                conn.addParamMap("nickname", binding.edtNickname.getText().toString());
                conn.onExcute((isResult, data) -> {
                    if(!data.equals("null")){
                        Toast.makeText(this, "이미 존재하는 닉네임입니다", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "사용가능한 닉네임입니다", Toast.LENGTH_SHORT).show();
                        binding.tvNicknameCheck.setVisibility(View.GONE);
                        binding.tvNicknameCheckDone.setVisibility(View.VISIBLE);
                    }
                });
            }else {
                Toast.makeText(this, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show();
            }
        });

        binding.edtPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!Pattern.compile("[0-9]").matcher(binding.edtPw.getText().toString()).find()){
                    binding.tvPwAlert.setVisibility(View.VISIBLE);
                    binding.tvPwAlert.setText("숫자 1글자 이상");
                }else if(!Pattern.compile("[a-z]").matcher(binding.edtPw.getText().toString()).find()){
                    binding.tvPwAlert.setVisibility(View.VISIBLE);
                    binding.tvPwAlert.setText("영문 1글자 이상");
                }else if(!Pattern.compile("[A-Z$@$!%*#?&]").matcher(binding.edtPw.getText().toString()).find()){
                    binding.tvPwAlert.setVisibility(View.VISIBLE);
                    binding.tvPwAlert.setText("특수문자 1글자 이상");
                }else if(binding.edtPw.getText().toString().length()<8||binding.edtPw.getText().toString().length()>18){
                    binding.tvPwAlert.setVisibility(View.VISIBLE);
                    binding.tvPwAlert.setText("8 - 18자");
                }else {
                    binding.tvPwAlert.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        InputMethodManager manager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        binding.cvNext.setOnClickListener(v -> {
            if(binding.edtName.getText().toString().length()<1){
                Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                binding.edtName.requestFocus();
                manager.showSoftInput(binding.edtName, InputMethodManager.SHOW_IMPLICIT);
            }else if(binding.tvNicknameCheck.getVisibility()==View.VISIBLE){
                Toast.makeText(this, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show();
                binding.edtNickname.requestFocus();
                manager.showSoftInput(binding.edtNickname, InputMethodManager.SHOW_IMPLICIT);
            }else if(binding.tvIdCheck.getVisibility()==View.VISIBLE){
                Toast.makeText(this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
                binding.edtId.requestFocus();
                manager.showSoftInput(binding.edtId, InputMethodManager.SHOW_IMPLICIT);
            }else if(binding.edtPw.getText().toString().length()<1){
                Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                binding.edtPw.requestFocus();
                manager.showSoftInput(binding.edtPw, InputMethodManager.SHOW_IMPLICIT);
            }else {
                Intent intent1 = new Intent(this, LoginFavorActivity.class);
                LoginVar.name = binding.edtName.getText().toString();
                LoginVar.nickname = binding.edtNickname.getText().toString();
                LoginVar.id = binding.edtId.getText().toString();
                LoginVar.pw = binding.edtPw.getText().toString();
                if(binding.edtImage.getText().toString().length()>0){
                    LoginVar.profileimg = binding.edtImage.getText().toString();
                }
                startActivity(intent1);
            }
        });

    }
}