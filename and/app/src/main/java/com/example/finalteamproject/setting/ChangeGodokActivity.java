package com.example.finalteamproject.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.ActivityChangeGodokBinding;
import com.example.finalteamproject.main.OptionVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class ChangeGodokActivity extends AppCompatActivity {

    ActivityChangeGodokBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeGodokBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgvBack.setOnClickListener(view -> {
            finish();
        });
        CommonConn conn = new CommonConn(this, "setting/viewOption");
        conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
        conn.onExcute((isResult, data) -> {
            ArrayList<OptionVO> list = new Gson().fromJson(data, new TypeToken<ArrayList<OptionVO>>() {
            }.getType());
            binding.tvGodok.setOnClickListener(view -> {
                if(list.get(0).getOption_godok_alarm().equals("N") ) {
                    binding.tvGodok.setText("켜짐");
                } else {
                    binding.tvGodok.setText("꺼짐");
                }
            });
        });

    }
}