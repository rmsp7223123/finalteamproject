package com.example.finalteamproject.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.MemberVO;
import com.example.finalteamproject.databinding.ActivityLoginFindPwBinding;
import com.google.gson.Gson;

public class LoginFindPwActivity extends AppCompatActivity {

    ActivityLoginFindPwBinding binding;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginFindPwBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        phoneNumber = getIntent().getStringExtra("phoneNumber");

        binding.tvPhoneNumber.setText(phoneNumber+"(으)로 가입된 아이디를 입력해주세요");

        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });

        InputMethodManager manager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        binding.cvDone.setOnClickListener(v -> {            if(binding.edtId.getText().toString().length()<1){
            Toast.makeText(this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
            binding.edtId.requestFocus();
            manager.showSoftInput(binding.edtId, InputMethodManager.SHOW_IMPLICIT);
        }else {
            CommonConn conn = new CommonConn(this, "login/findPw");
            conn.addParamMap("phoneNumber", phoneNumber);
            conn.addParamMap("id", binding.edtId.getText().toString());
            conn.onExcute((isResult, data) -> {
                if(data!=null&&data.length()>0){
                    CommonConn commonConn = new CommonConn(this, "login/modifyPw");
                    commonConn.addParamMap("id", binding.edtId.getText().toString());
                    commonConn.onExcute((isResult2, data2) -> {
                        if(data2!=null){
                            CommonConn conn1 = new CommonConn(this, "login/sendPw");
                            conn1.addParamMap("phoneNumber", phoneNumber);
                            conn1.addParamMap("pw", data2);
                            conn1.onExcute((isResult1, data1) -> {
                                if(data1.equals("성공")){
                                    Toast.makeText(this, "문자 전송 성공\n발급된 임시 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(this, "문자 전송 실패", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else {
                            Toast.makeText(this, "임시 비밀번호 발급 실패", Toast.LENGTH_SHORT).show();
                        }
                    });


                }else {
                    Toast.makeText(this, "일치하지 않는 아이디입니다\n다시 입력해주세요", Toast.LENGTH_SHORT).show();
                    binding.edtId.requestFocus();
                    manager.showSoftInput(binding.edtId, InputMethodManager.SHOW_IMPLICIT);
                }
            });
        }
        });



//        binding.edtId.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(binding.edtId.getText().toString().length()>0){
//                    binding.tvSendPw.setVisibility(View.VISIBLE);
//                }else {
//                    binding.tvSendPw.setVisibility(View.INVISIBLE);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

//        binding.tvSendPw.setOnClickListener(v -> {
//
//        });

    }
}