package com.example.finalteamproject.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.common.MemberVO;
import com.example.finalteamproject.databinding.ActivityLoginCheckBinding;
import com.example.finalteamproject.main.MainActivity;
import com.example.finalteamproject.main.SplashActivity;
import com.google.gson.Gson;

public class LoginCheckActivity extends AppCompatActivity {

    ActivityLoginCheckBinding binding;

    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginCheckBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });

        binding.tvFindId.setOnClickListener(v -> {
            Intent intent  = new Intent(this, LoginFindIdActivity.class);
            intent.putExtra("phoneNumber", getIntent().getStringExtra("member_phone"));
            startActivity(intent);
        });

        binding.tvFindPw.setOnClickListener(v -> {
            Intent intent  = new Intent(this, LoginFindPwActivity.class);
            intent.putExtra("phoneNumber", getIntent().getStringExtra("member_phone"));
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
                conn.addParamMap("member_phone", intent1.getStringExtra("member_phone"));
                conn.addParamMap("member_id", binding.edtId.getText().toString());
                conn.addParamMap("member_pw", binding.edtPw.getText().toString());
                conn.onExcute((isResult, data) -> {
                    CommonVar.logininfo = new Gson().fromJson(data, MemberVO.class);
                    if(CommonVar.logininfo!=null){

                        CommonConn commonConn = new CommonConn(this, "login/checking");
                        commonConn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
                        commonConn.onExcute((isResult1, data1) -> {
                            CheckVO vo = new Gson().fromJson(data1, CheckVO.class);
                            if(vo.getMember_profileimg()==null){
                                Intent intent = new Intent(this, LoginProfileActivity.class);
                                startActivity(intent);
                            }else if(vo.getFavor()==0){
                                Intent intent = new Intent(this, LoginFavorActivity.class);
                                startActivity(intent);
                            }else if(vo.getEphone_phone()==null){
                                Intent intent = new Intent(this, LoginGodokActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show();
                                SharedPreferences pref = getSharedPreferences("loginInfo", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("loginInfo", binding.edtId.getText().toString());
                                editor.commit();
                                Intent intent = new Intent(this, SplashActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        });



                    }else {
                        Toast.makeText(this, "로그인 실패\n아이디나 비밀번호를 확인해주세요", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }


}