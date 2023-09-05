package com.example.finalteamproject.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.finalteamproject.HideActionBar;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.ActivityMainAlarmHistoryBinding;
import com.example.finalteamproject.setting.ChangeAlarmActivity;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainAlarmHistoryActivity extends AppCompatActivity {

    ActivityMainAlarmHistoryBinding binding;
    int itemCnt;

    MainAlarmHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainAlarmHistoryBinding.inflate(getLayoutInflater());

        //new HideActionBar().hideActionBar(this);
        setContentView(binding.getRoot());
        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });
        CommonConn conn = new CommonConn(this, "main/viewAlarm");
        conn.addParamMap("receive_id", CommonVar.logininfo.getMember_id());
        conn.onExcute((isResult, data) -> {
            ArrayList<AlarmVO> alarm_list = new Gson().fromJson(data, new com.google.gson.reflect.TypeToken<ArrayList<AlarmVO>>(){}.getType());
            binding.imgvAlarmClean.setOnClickListener(view -> {
                if(alarm_list.size() == 0){
                    Toast.makeText(this, "삭제할 알람이 없습니다", Toast.LENGTH_SHORT).show();
                }else {
                    // 알람 지웠을 때 알람기록 다 지우기 추가
                    CommonConn conn1 = new CommonConn(this, "main/deleteAlarmOne");
                    Log.d("id", "onCreate: "+CommonVar.logininfo.getMember_id());
                    conn1.addParamMap("receive_id", CommonVar.logininfo.getMember_id());
                    conn1.onExcute((isResult1, data1) -> {
                        alarmVisibility(View.VISIBLE);
                        adapter.list = new ArrayList<>();
                        adapter.notifyDataSetChanged();
                    });
                }

            });
        });
        selectList();
    }

    public void alarmVisibility(int visible){
        binding.containerLinearAlarm.setVisibility(visible);
    }

    public void selectList() {
    //    ArrayList<AlarmVO> list  = new ArrayList<>();

//        if(getIntent().getBooleanExtra("addFriend", false)) {
////            AlarmVO alarm = new AlarmVO();
////            String title = getIntent().getStringExtra("title");
////            String message = getIntent().getStringExtra("message");
////            alarm.setAlarm_content(message);
////            list.add(alarm);
//        }
        CommonConn conn = new CommonConn(this, "main/viewAlarm");
        conn.addParamMap("receive_id", CommonVar.logininfo.getMember_id());
        conn.onExcute((isResult, data) -> {
            ArrayList<AlarmVO> list = new Gson().fromJson(data, new TypeToken<ArrayList<AlarmVO>>(){}.getType());
            adapter = new MainAlarmHistoryAdapter(list, this);
            binding.recvAlarmHistory.setAdapter(adapter);
            binding.recvAlarmHistory.setLayoutManager(new LinearLayoutManager(this));


            if (list.size() > 0) {
               alarmVisibility(View.GONE);
            } else {
                alarmVisibility(View.VISIBLE);
            }
        });
    }
}