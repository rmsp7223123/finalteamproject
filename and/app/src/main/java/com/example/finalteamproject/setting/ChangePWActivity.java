package com.example.finalteamproject.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.ActivityChangePwactivityBinding;

import java.util.regex.Pattern;

public class ChangePWActivity extends AppCompatActivity {

    ActivityChangePwactivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePwactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });

        binding.edtPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Pattern.compile("[가-힣]").matcher(binding.edtPw.getText().toString()).find()){
                    binding.tvPwAlert.setVisibility(View.VISIBLE);
                    binding.tvPwAlert.setText("영문, 숫자, 특수문자 외의 다른 문자 미포함");
                }if(!Pattern.compile("[0-9]").matcher(binding.edtPw.getText().toString()).find()){
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

                if(binding.edtPwCheck.getText().toString().equals(binding.edtPw.getText().toString())){
                    binding.tvPwCheckAlert.setVisibility(View.INVISIBLE);
                }else {
                    binding.tvPwCheckAlert.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.edtPwCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(binding.edtPwCheck.getText().toString().equals(binding.edtPw.getText().toString())){
                    binding.tvPwCheckAlert.setVisibility(View.INVISIBLE);
                }else {
                    binding.tvPwCheckAlert.setVisibility(View.VISIBLE);
                }
            }
        });

        InputMethodManager manager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        binding.cvNext.setOnClickListener(v -> {
            if(binding.edtCurPw.getText().toString().length()<1){
                Toast.makeText(this, "비밀번호 입력을 완료해주세요", Toast.LENGTH_SHORT).show();
                binding.edtCurPw.requestFocus();
                manager.showSoftInput(binding.edtCurPw, InputMethodManager.SHOW_IMPLICIT);
            }else if(binding.edtPw.getText().toString().length()<1||binding.tvPwAlert.getVisibility()==View.VISIBLE){
                Toast.makeText(this, "조건에 맞는 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                binding.edtPw.requestFocus();
                manager.showSoftInput(binding.edtPw, InputMethodManager.SHOW_IMPLICIT);
            }else if(binding.tvPwCheckAlert.getVisibility()==View.VISIBLE||binding.edtPwCheck.getText().toString().length()<1){
                Toast.makeText(this, "비밀번호 확인을 완료해주세요", Toast.LENGTH_SHORT).show();
                binding.edtPwCheck.requestFocus();
                manager.showSoftInput(binding.edtPwCheck, InputMethodManager.SHOW_IMPLICIT);
            }else if(!binding.edtCurPw.getText().toString().equals(CommonVar.logininfo.getMember_pw())){
                Toast.makeText(this, "현재 비밀번호와 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                binding.edtCurPw.requestFocus();
                manager.showSoftInput(binding.edtCurPw, InputMethodManager.SHOW_IMPLICIT);
            }else if(binding.edtPw.getText().toString().equals(CommonVar.logininfo.getMember_pw())){
                Toast.makeText(this, "현재 비밀번호와 일치합니다", Toast.LENGTH_SHORT).show();
                binding.edtPw.requestFocus();
                manager.showSoftInput(binding.edtPw, InputMethodManager.SHOW_IMPLICIT);
            }else {
                CommonConn conn = new CommonConn(this, "login/settingPw");
                conn.addParamMap("id", CommonVar.logininfo.getMember_id());
                conn.addParamMap("pw", binding.edtPw.getText().toString());
                conn.onExcute((isResult, data) -> {
                    if(data.equals("성공")){
                        CommonVar.logininfo.setMember_pw(binding.edtPw.getText().toString());
                        Toast.makeText(this, "비밀번호 변경 성공", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(this, "비밀번호 변경 실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}