package com.example.finalteamproject.Login;

import static android.view.View.GONE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finalteamproject.HideActionBar;
import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.databinding.ActivityLoginGodokBinding;
import com.google.android.gms.common.internal.service.Common;
import com.google.android.play.core.integrity.b;

import java.util.regex.Pattern;

public class LoginGodokActivity extends AppCompatActivity {

    ActivityLoginGodokBinding binding;

    String[] list = {"선택", "아들", "딸", "며느리", "사위", "손자", "손녀", "친구", "이웃", "기타"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginGodokBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });

        binding.edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", binding.edtPhone.getText().toString())){
                    binding.tvWrong.setVisibility(View.VISIBLE);
                }else {
                    binding.tvWrong.setVisibility(View.INVISIBLE);
                }
            }
        });

        Spinner relationSpinner = binding.spnRelation;
        ArrayAdapter relationAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list);
        relationSpinner.setAdapter(relationAdapter);
        binding.spnRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int i, long id) {
                if(list[i].equals("기타")){
                    binding.edtRelation.setVisibility(View.VISIBLE);
                }else {
                    binding.edtRelation.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        InputMethodManager manager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        binding.cvDone.setOnClickListener(v -> {
            if(binding.edtName.getText().toString().length()<1){
                Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                binding.edtName.requestFocus();
                manager.showSoftInput(binding.edtName, InputMethodManager.SHOW_IMPLICIT);
            }else if(binding.edtPhone.getText().toString().length()<1||binding.tvWrong.getVisibility()==View.VISIBLE){
                Toast.makeText(this, "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                binding.edtPhone.requestFocus();
                manager.showSoftInput(binding.edtPhone, InputMethodManager.SHOW_IMPLICIT);
            }else if(binding.spnRelation.getSelectedItem().toString().equals("선택")){
                Toast.makeText(this, "주변인과의 관계를 선택해주세요.", Toast.LENGTH_SHORT).show();
                binding.spnRelation.requestFocus();
            }else if(binding.edtRelation.getVisibility()==View.VISIBLE&&binding.edtRelation.getText().toString().length()<1){
                Toast.makeText(this, "주변인과의 관계를 입력해주세요.", Toast.LENGTH_SHORT).show();
                binding.edtRelation.requestFocus();
                manager.showSoftInput(binding.edtRelation, InputMethodManager.SHOW_IMPLICIT);
            }else {
                CommonConn conn = new CommonConn(this, "login/join");
                conn.addParamMap("member_id", LoginVar.id);
                conn.addParamMap("member_pw", LoginVar.pw);
                conn.addParamMap("member_name", LoginVar.name);
                conn.addParamMap("member_nickname", LoginVar.nickname);
                conn.addParamMap("member_birth", LoginVar.birth);
                conn.addParamMap("member_gender", LoginVar.gender);
                conn.addParamMap("member_phone", LoginVar.phone);
                conn.addParamMap("member_phone_id", Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
                if(LoginVar.profileimg!=null){
                    conn.addParamMap("member_profileimg", LoginVar.profileimg);
                }else {
                    conn.addParamMap("member_profileimg", "없음");
                }
                conn.onExcute((isResult, data) -> {
                    if(data.equals("성공")){
//                        CommonConn conn1 = new CommonConn(this, "login/godok");
//                        conn1.addParamMap("member_id", LoginVar.id);
//                        conn1.addParamMap("ephone_name", binding.edtName.getText().toString());
//                        conn1.addParamMap("ephone_phone", binding.edtPhone.getText().toString());
//                        if(binding.edtRelation.getText().toString().length()>0){
//                            conn1.addParamMap("ephone_relation", binding.edtRelation.getText().toString());
//                        }else {
//                            conn1.addParamMap("ephone_relation", binding.spnRelation.getSelectedItem().toString());
//                        }
//                        conn1.addParamMap("ephone_phone_id", Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
//                        conn1.onExcute((isResult1, data1) -> {
//                            if(data1.equals("성공")){
//                                Toast.makeText(this, "회원가입, 비상연락망 성공", Toast.LENGTH_SHORT).show();
//                            }else {
//                                Toast.makeText(this, "회원가입에는 성공했으나 비상연락망 정보 입력 실패", Toast.LENGTH_SHORT).show();
//                            }
//                        });
                    }else {
                        Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                    }
                });
                Intent intent = new Intent(this, LoginDoneActivity.class);
                startActivity(intent);
            }

        });


    }
}