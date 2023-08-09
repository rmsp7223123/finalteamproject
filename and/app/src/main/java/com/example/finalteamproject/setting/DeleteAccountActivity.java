package com.example.finalteamproject.setting;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivityDeleteAccountBinding;

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
            TextView messageTextView = dialogView.findViewById(android.R.id.message);
            messageTextView.setGravity(Gravity.CENTER); // 메시지 텍스트를 가운데 정렬

            Button yesButton = dialogView.findViewById(R.id.btnYes);
            Button noButton = dialogView.findViewById(R.id.btnNo);

            yesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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