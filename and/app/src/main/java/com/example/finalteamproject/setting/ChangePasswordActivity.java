package com.example.finalteamproject.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.ActivityChangePasswordBinding;

public class ChangePasswordActivity extends AppCompatActivity {
    ActivityChangePasswordBinding binding;
    Intent intent;

    public static String password = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });
        binding.btnPassword.setOnClickListener(view -> {
            CommonConn conn = new CommonConn(this, "setting/inquirePattern");
            conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
            conn.onExcute((isResult, data) -> {
                if(!data.equals("null")) {
                    Toast.makeText(this, "패턴이 존재합니다.", Toast.LENGTH_SHORT).show();
                } else {
                    CommonConn conn2 = new CommonConn(this, "setting/inquirePw");
                    conn2.addParamMap("member_id", CommonVar.logininfo.getMember_id());
                    conn2.onExcute((isResult1, data1) -> {
                        if(data != null) {
                            Toast.makeText(this, "비밀번호가 존재합니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            intent = new Intent(this, SetPasswordActivity.class);
                            startActivity(intent);
                        }
                    });

                }
            });

        });
        binding.btnPattern.setOnClickListener(view -> {
            CommonConn conn = new CommonConn(this, "setting/inquirePw");
            conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
            conn.onExcute((isResult, data) -> {
                if(!data.equals("null")) {
                    Toast.makeText(this, "비밀번호가 존재합니다.", Toast.LENGTH_SHORT).show();
                } else {
                    CommonConn conn2 = new CommonConn(this, "setting/inquirePattern");
                    conn2.addParamMap("member_id", CommonVar.logininfo.getMember_id());
                    conn2.onExcute((isResult1, data1) -> {
                        if(data != null) {
                            Toast.makeText(this, "패턴이 존재합니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            intent = new Intent(this, SetPasswordActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            });
        });
        binding.btnDeletePassword.setOnClickListener(v -> {
            CommonConn conn = new CommonConn(this, "setting/inquirePw");
        });
        // 비밀번호가 있는경우 비밀번호 재입력, 패턴이 있는경우 패턴 재입력 후 비밀번호 삭제되게 추가하기
    }
}