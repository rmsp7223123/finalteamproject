package com.example.finalteamproject.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import com.example.finalteamproject.FirebaseMessageReceiver;
import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.ActivityChangeAlarmBinding;
import com.example.finalteamproject.main.MainAlarmHistoryAdapter;
import com.example.finalteamproject.main.OptionVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class ChangeAlarmActivity extends AppCompatActivity {
    ActivityChangeAlarmBinding binding;

    boolean isEnabled = FirebaseMessageReceiver.isIsEnabled();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeAlarmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });
        CommonConn conn = new CommonConn(this, "setting/viewOption");
        conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
        conn.onExcute((isResult, data) -> {
            ArrayList<OptionVO> list = new Gson().fromJson(data, new TypeToken<ArrayList<OptionVO>>(){}.getType());
            SharedPreferences sharedPreferences = getSharedPreferences("NotificationPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            if(list.get(0).getOption_alarm().equals("N")) {
                binding.tvCallMessageAlarmChange.setText("꺼짐");
                // 기본 알람을 껐을때 알람창 데이터 삭제 추가
                CommonConn conn1 = new CommonConn(this, "delete/Alarm");
                conn1.addParamMap("receive_id", CommonVar.logininfo.getMember_id());
                conn1.onExcute((isResult1, data1) -> {
                    // 알람을 보냈을때 받는사람의 id가 알람이 N인 상태이면 보낼때 받는사람의 알람상태를 조회후 Y인 경우에만 보내게?
                    FirebaseMessageReceiver.setIsEnabled(false);
                    editor.putBoolean("notificationEnabled", isEnabled);
                    editor.apply();
                });
            } else {
                binding.tvCallMessageAlarmChange.setText("켜짐");
                FirebaseMessageReceiver.setIsEnabled(true);
                editor.putBoolean("notificationEnabled", isEnabled);
                editor.apply();
            }
        });
        binding.tvCallMessageAlarmChange.setOnClickListener(view -> {
            CommonConn conn1 = new CommonConn(this, "setting/updateAlarm");
            conn1.addParamMap("member_id", CommonVar.logininfo.getMember_id());
            conn1.onExcute((isResult, data) -> {
                if(binding.tvCallMessageAlarmChange.getText().toString().equals("꺼짐")) {
                    binding.tvCallMessageAlarmChange.setText("켜짐");
                }  else {
                    binding.tvCallMessageAlarmChange.setText("꺼짐");
                }
            });
        });
    }

}