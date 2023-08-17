package com.example.finalteamproject.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
        binding.imgvAlarmClean.setOnClickListener(view -> {
            // 알람 지웠을 때 알람기록 다 지우기 추가
            CommonConn conn = new CommonConn(this, "main/deleteAlarm");
            conn.addParamMap("receive_id", CommonVar.logininfo.getMember_id());
            conn.onExcute((isResult, data) -> {
                binding.containerLinearAlarm.setVisibility(View.VISIBLE);
                adapter.list = new ArrayList<>();
                adapter.notifyDataSetChanged();
            });
        });


        selectList();
        // 알람 기록이 있을때 -- > 어댑터 리턴 사이즈가 0이 아닐 때 프레임 레이아웃안에 linear 안보이게 추가하기
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

            itemCnt = adapter.getItemCount();
            if (itemCnt > 0) {
                binding.containerLinearAlarm.setVisibility(View.INVISIBLE);
            }
        });
    }
}