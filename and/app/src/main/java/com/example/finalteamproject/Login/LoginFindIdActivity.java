package com.example.finalteamproject.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.databinding.ActivityLoginFindIdBinding;

public class LoginFindIdActivity extends AppCompatActivity {

    ActivityLoginFindIdBinding binding;
    String phoneNumber;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginFindIdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        phoneNumber = getIntent().getStringExtra("phoneNumber");

        binding.tvPhoneNumber.setText(phoneNumber+"(으)로\n가입된 아이디입니다");

        CommonConn conn = new CommonConn(this, "login/findId");
        conn.addParamMap("phoneNumber", phoneNumber);
        conn.onExcute((isResult, data) -> {
            if(data!=null){
                id = data;
                int length = data.length()%2 == 1 ? data.length()/2+1 : data.length()/2;
                binding.tvId.setText(data.replace(data.substring(data.length()/2), replaceId(length)));
            }
        });

//        binding.tvSendId.setOnClickListener(v -> {
//
//        });


        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });

        binding.cvDone.setOnClickListener(v -> {
            CommonConn conn1 = new CommonConn(this, "login/sendId");
            conn1.addParamMap("phoneNumber", phoneNumber);
            conn1.addParamMap("id", id);
            conn1.onExcute((isResult, data) -> {
                if(data.equals("성공")){
                    Toast.makeText(this, "문자 발송 성공\n문자를 확인해주세요", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "문자 발송 실패", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }

    public String replaceId(int length){
        String replace = "";
        for(int i=0; i<length; i++){
            replace += "*";
        }
            return replace;
    }

}