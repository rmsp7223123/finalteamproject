package com.example.finalteamproject.Login;

import static android.view.View.GONE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.finalteamproject.HideActionBar;
import com.example.finalteamproject.databinding.ActivityPhoneBinding;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

public class PhoneActivity extends AppCompatActivity {

    ActivityPhoneBinding binding;
    Timer timer;
    int nCnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new HideActionBar().hideActionBar(this);



        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });

        binding.edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

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
                SmsSend("01096024788", "ㅎㅇ");
                binding.cvSubmit.setVisibility(GONE);
                binding.cvDone.setVisibility(View.VISIBLE);
                binding.tvMessage.setVisibility(View.VISIBLE);
                binding.rlMessage.setVisibility(View.VISIBLE);
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(nCnt>9) {
                                    binding.tvTimer.setText("0" + (nCnt / 60) + ":" + (nCnt % 60));
                                }else if(10>nCnt&&nCnt>0){
                                    binding.tvTimer.setText("0" + (nCnt / 60) + ":0" + (nCnt % 60));
                                }else {
                                    binding.tvTimer.setVisibility(GONE);
                                    binding.tvReissue.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                        someWork();
                    }
                };
                nCnt = 180;
                timer = new Timer();
                timer.schedule(timerTask, 1000, 1000);
            }else {
                Toast.makeText(this, "전화번호를 정확히 입력해주세요", Toast.LENGTH_SHORT).show();
            }
        });

        binding.tvReissue.setOnClickListener(v -> {
            binding.tvTimer.setText("");
            binding.tvReissue.setVisibility(GONE);
            binding.tvTimer.setVisibility(View.VISIBLE);
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if((nCnt%60)>9) {
                                binding.tvTimer.setText("0" + (nCnt / 60) + ":" + (nCnt % 60));
                            }else if((10>(nCnt%60)&&(nCnt%60)>0)||nCnt==180||nCnt==120){
                                binding.tvTimer.setText("0" + (nCnt / 60) + ":0" + (nCnt % 60));
                            }else {
                                binding.tvTimer.setVisibility(GONE);
                                binding.tvReissue.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    someWork();
                }
            };
            nCnt = 180;
            timer = new Timer();
            timer.schedule(timerTask, 1000, 1000);
        });

        binding.cvDone.setOnClickListener(v -> {
            Intent intent = new Intent(this, IDCardActivity.class);
            startActivity(intent);
        });

    }


    public void SmsSend(String strPhoneNumber, String strMsg){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {//권한이 없다면
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 2323);
        }else { //권한이 있다면 SMS를 보낸다.
            PendingIntent sendIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT"), PendingIntent.FLAG_IMMUTABLE);
            PendingIntent deliveredIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED"), PendingIntent.FLAG_IMMUTABLE);
            SmsManager smsManager = SmsManager.getDefault();
            try {
                smsManager.sendTextMessage(strPhoneNumber, null, strMsg, sendIntent, deliveredIntent);
            } catch (Exception ex) {
                ex.printStackTrace();
                Toast.makeText(getBaseContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void someWork(){
        if(nCnt==1){
            timer.cancel();
        }
        nCnt--;
    }
    
}