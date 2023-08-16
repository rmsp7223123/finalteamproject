package com.example.finalteamproject.cs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.ActivityNewCsboardBinding;

public class NewCSBoardActivity extends AppCompatActivity {

    ActivityNewCsboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewCsboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });

        InputMethodManager manager = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);

        binding.cvNext.setOnClickListener(v -> {
            if (binding.edtTitle.getText().toString().length() < 1) {
                Toast.makeText(this, "제목을 입력하세요", Toast.LENGTH_SHORT).show();
                binding.edtTitle.requestFocus();
                manager.showSoftInput(binding.edtTitle, InputMethodManager.SHOW_IMPLICIT);
            } else if (binding.edtContent.getText().toString().length() < 1) {
                Toast.makeText(this, "내용을 입력하세요", Toast.LENGTH_SHORT).show();
                binding.edtContent.requestFocus();
                manager.showSoftInput(binding.edtContent, InputMethodManager.SHOW_IMPLICIT);
            } else {
                CommonConn conn = new CommonConn(this, "csboard/insert");
                conn.addParamMap("writer", CommonVar.logininfo.getMember_id());
                conn.addParamMap("csboard_title", binding.edtTitle.getText().toString());
                conn.addParamMap("csboard_content", binding.edtContent.getText().toString());
                conn.onExcute((isResult, data) -> {
                    if(data.equals("성공")){
                        Toast.makeText(this, "게시글 등록 성공", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(this, "게시글 등록 실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}