package com.example.finalteamproject.cs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.databinding.ActivityModifyCsboardBinding;

public class ModifyCSBoardActivity extends AppCompatActivity {

    ActivityModifyCsboardBinding binding;
    CSBoardVO vo;
    boolean secret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityModifyCsboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        vo = (CSBoardVO) getIntent().getSerializableExtra("vo");

        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });

        binding.edtTitle.setText(vo.csboard_title);
        binding.edtContent.setText(vo.csboard_content);
        if(vo.csboard_secret.equals("Y")){
            binding.imgvSecret.setImageResource(R.drawable.secret_checked);
            secret = true;
        }

        binding.rlSecret.setOnClickListener(v -> {
            if(secret){
                secret = false;
                binding.imgvSecret.setImageResource(R.drawable.secret_unchecked);
                Toast.makeText(this, "비밀글 선택 취소", Toast.LENGTH_SHORT).show();
            }else {
                secret = true;
                binding.imgvSecret.setImageResource(R.drawable.secret_checked);
                Toast.makeText(this, "비밀글 선택 완료", Toast.LENGTH_SHORT).show();
            }
        });


        InputMethodManager manager = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);

        binding.cvNext.setOnClickListener(v -> {
            if(binding.edtTitle.getText().toString().length()<1){
                Toast.makeText(this, "제목을 입력하세요", Toast.LENGTH_SHORT).show();
                binding.edtTitle.requestFocus();
                manager.showSoftInput(binding.edtTitle, InputMethodManager.SHOW_IMPLICIT);
            }else if(binding.edtContent.getText().toString().length()<1){
                Toast.makeText(this, "내용을 입력하세요", Toast.LENGTH_SHORT).show();
                binding.edtContent.requestFocus();
                manager.showSoftInput(binding.edtContent, InputMethodManager.SHOW_IMPLICIT);
            }else {
                CommonConn conn = new CommonConn(this, "csboard/modify");
                conn.addParamMap("csboard_id", vo.csboard_id);
                conn.addParamMap("csboard_title", binding.edtTitle.getText().toString());
                conn.addParamMap("csboard_content", binding.edtContent.getText().toString());
                conn.addParamMap("csboard_secret", secret ? "Y" : "N");
                conn.onExcute((isResult, data) -> {
                    if(data.equals("성공")){
                        Toast.makeText(this, "문의글 수정 성공", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(this, "문의글 수정 실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}