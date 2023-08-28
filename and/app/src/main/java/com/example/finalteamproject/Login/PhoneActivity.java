package com.example.finalteamproject.Login;

import static android.view.View.GONE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.ActivityPhoneBinding;
import com.example.finalteamproject.common.MemberVO;
import com.example.finalteamproject.main.MainActivity;
import com.google.gson.Gson;

import java.util.regex.Pattern;

public class PhoneActivity extends AppCompatActivity {

    ActivityPhoneBinding binding;
    String result = null;
    CountDownTimer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //new HideActionBar().hideActionBar(this);



        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });

        binding.edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(binding.tvTimer.getVisibility()==View.VISIBLE){
                    binding.tvTimer.setVisibility(GONE);
                    binding.tvReissue.setVisibility(View.VISIBLE);
                    stopTimer();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!Pattern.matches("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", binding.edtPhone.getText().toString())){
                    binding.tvWrong.setVisibility(View.VISIBLE);
                }else {
                    binding.tvWrong.setVisibility(GONE);
                }
            }
        });

        binding.cvSubmit.setOnClickListener(v -> {
            if(Pattern.matches("^01(?:0|1|[6-9])+(?:\\d{3}|\\d{4})+\\d{4}$", binding.edtPhone.getText().toString())){
                binding.rlMessage.setVisibility(View.VISIBLE);
                binding.tvMessage.setVisibility(View.VISIBLE);
                binding.tvTimer.setVisibility(View.VISIBLE);
                binding.cvDone.setVisibility(View.VISIBLE);
                binding.cvSubmit.setVisibility(GONE);
                CommonConn conn = new CommonConn(this, "login/sendSms");
                conn.addParamMap("phoneNumber", binding.edtPhone.getText().toString());
                conn.onExcute((isResult, data) -> {
                    if(data.equals("실패")||data==null){
                        Toast.makeText(this, "sms 문자 전송에 실패하였습니다\n다시 시도해주세요", Toast.LENGTH_SHORT).show();
                    }else {
                        result = data;
//                        binding.edtNumber.setText(data);
                        binding.tvMessage.setVisibility(View.VISIBLE);
                        binding.rlMessage.setVisibility(View.VISIBLE);
                        setTimer();
                    }

                });
            }else {
                Toast.makeText(this, "전화번호를 정확히 입력해주세요", Toast.LENGTH_SHORT).show();
            }
        });

        binding.tvReissue.setOnClickListener(v -> {
            binding.tvTimer.setVisibility(View.VISIBLE);
            binding.tvReissue.setVisibility(GONE);
            if(Pattern.matches("^01(?:0|1|[6-9])+(?:\\d{3}|\\d{4})+\\d{4}$", binding.edtPhone.getText().toString())){
                CommonConn conn = new CommonConn(this, "login/sendSms");
                conn.addParamMap("phoneNumber", binding.edtPhone.getText().toString());
                conn.onExcute((isResult, data) -> {
                    if(data.equals("실패")){
                        Toast.makeText(this, "sms 문자 전송에 실패하였습니다\n다시 시도해주세요", Toast.LENGTH_SHORT).show();
                    }else if(data==null){
                        Toast.makeText(this, "sms 문자 전송에 실패하였습니다\n다시 시도해주세요", Toast.LENGTH_SHORT).show();
                    }else {
                        result = data;
//                        binding.edtNumber.setText(data);
                        binding.tvMessage.setVisibility(View.VISIBLE);
                        binding.rlMessage.setVisibility(View.VISIBLE);
                        setTimer();
                    }

                });
            }else {
                Toast.makeText(this, "전화번호를 정확히 입력해주세요", Toast.LENGTH_SHORT).show();
            }
        });


        binding.cvDone.setOnClickListener(v -> {
            if(binding.tvReissue.getVisibility()==View.VISIBLE){
                Toast.makeText(this, "인증번호 확인을 완료해주세요", Toast.LENGTH_SHORT).show();
            }else if(!binding.edtNumber.getText().toString().equals(result)){
                Toast.makeText(this, "인증번호와 일치하지 않습니다", Toast.LENGTH_SHORT).show();
            }else {
                CommonConn conn = new CommonConn(this, "login/checkPhone");
                conn.addParamMap("phoneNumber", binding.edtPhone.getText().toString());
                conn.onExcute((isResult, data) -> {
                    CommonVar.logininfo  = new Gson().fromJson(data, MemberVO.class);
                    if(CommonVar.logininfo ==null){
                        LoginVar.phone = binding.edtPhone.getText().toString();
                        Intent intent = new Intent(this, IDCardActivity.class);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(this, LoginCheckActivity.class);
                        intent.putExtra("member_phone", binding.edtPhone.getText().toString());
                        startActivity(intent);
                    }
                });
            }
        });

    }

    private void setTimer(){
        timer = new CountDownTimer(180000, 1000) {
            int time = 180;
            @Override
            public void onTick(long millisUntilFinished) {
                binding.tvTimer.setVisibility(View.VISIBLE);
                binding.tvReissue.setVisibility(View.INVISIBLE);
                if(time%60<10){
                    binding.tvTimer.setText("0"+(time/60)+":0"+(time%60));
                }else {
                    binding.tvTimer.setText("0"+(time/60)+":"+(time%60));
                }
                time--;
            }
            @Override
            public void onFinish() {
                binding.tvTimer.setVisibility(View.INVISIBLE);
                binding.tvReissue.setVisibility(View.VISIBLE);
            }
        };
        timer.start();
    }

    private void stopTimer(){
        timer.cancel();
    }
    
}