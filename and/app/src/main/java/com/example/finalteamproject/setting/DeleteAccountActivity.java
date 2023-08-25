package com.example.finalteamproject.setting;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.com.example.finalteamproject.common.CustomTextview;
import android.widget.Toast;

import com.example.finalteamproject.Login.LoginInfoActivity;
import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.ActivityDeleteAccountBinding;
import com.example.finalteamproject.main.SplashActivity;

public class DeleteAccountActivity extends AppCompatActivity {
    ActivityDeleteAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeleteAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });
        binding.cvDeleteAccount.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // 생성한 AlertDialog.Builder에 사용자 정의 View를 설정
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_confirm, null);
            builder.setView(dialogView);

            // 정의한 AlertDialog에 View를 설정하고, 예 아니오 버튼 리스너 등을 추가
            AlertDialog dialog = builder.create();
            com.example.finalteamproject.common.CustomTextview messagecom.example.finalteamproject.common.CustomTextview = dialogView.findViewById(android.R.id.message);
            messagecom.example.finalteamproject.common.CustomTextview.setGravity(Gravity.CENTER); // 메시지 텍스트를 가운데 정렬

            Button yesButton = dialogView.findViewById(R.id.btnYes);
            Button noButton = dialogView.findViewById(R.id.btnNo);

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CommonConn conn = new CommonConn(DeleteAccountActivity.this, "setting/deleteAccount");
                    if (CommonVar.logininfo.getMember_id().equals(binding.edtId.getText().toString()) && CommonVar.logininfo.getMember_pw().equals(binding.edtPw.getText().toString())) {
                        conn.addParamMap("member_id", binding.edtId.getText().toString());
                        conn.addParamMap("member_pw", binding.edtPw.getText().toString());
                        conn.onExcute((isResult, data) -> {
                            CommonVar.logininfo = null;
                            Toast.makeText(DeleteAccountActivity.this, "회원탈퇴 되었습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DeleteAccountActivity.this, SplashActivity.class);
                            startActivity(intent);
                        });
                    } else {
                        Toast.makeText(DeleteAccountActivity.this, "아이디 또는 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss(); //
                }
            });

            noButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();
        });
    }
}