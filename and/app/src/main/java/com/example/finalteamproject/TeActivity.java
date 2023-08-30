package com.example.finalteamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.databinding.ActivityTeBinding;

public class TeActivity extends AppCompatActivity {
    ActivityTeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String member_id = getIntent().getStringExtra("member_id");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("안부문자 보내기")
                .setMessage("안부문자를 보내시겠습니까?")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CommonConn conn = new CommonConn(getApplicationContext(), "godok/sendOneGodokMsg");
                        conn.addParamMap("member_id", member_id);
                        conn.onExcute((isResult, data) -> {
                        });
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}