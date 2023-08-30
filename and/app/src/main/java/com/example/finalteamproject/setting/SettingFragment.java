package com.example.finalteamproject.setting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalteamproject.FirebaseMessageReceiver;
import com.example.finalteamproject.Login.LoginActivity;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.cs.CSBoardActivity;
import com.example.finalteamproject.databinding.FragmentSettingBinding;
import com.example.finalteamproject.main.OptionVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class SettingFragment extends Fragment {

    FragmentSettingBinding binding;

    boolean isEnabled = FirebaseMessageReceiver.isIsEnabled();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        Glide.with(this).load(CommonVar.logininfo.getMember_profileimg()).into(binding.imgvProfileImg);
        binding.tvNickname.setText(CommonVar.logininfo.getMember_nickname());
        binding.tvName.setText(CommonVar.logininfo.getMember_name());
        setAlarmState();


        binding.containerLinearChangeProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ChangeProfileActivity.class);
            startActivity(intent);
        });
        binding.containerLinearChangeGodok.setOnClickListener(v -> {
            CommonConn conn1 = new CommonConn(getContext(), "setting/updateGodokAlarm");
            conn1.addParamMap("member_id", CommonVar.logininfo.getMember_id());
            conn1.onExcute((isResult1, data1) ->  {
                if(binding.tvGodok.getText().toString().equals("켜짐") ) {
                    binding.tvGodok.setText("꺼짐");
                    Toast.makeText(getContext(), "고독사 문자가 꺼졌습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    binding.tvGodok.setText("켜짐");
                    Toast.makeText(getContext(), "고독사 문자가 켜졌습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        });
        binding.containerLinearChangeAlarm.setOnClickListener(v -> {
            CommonConn conn1 = new CommonConn(getContext(), "setting/updateAlarm");
            conn1.addParamMap("member_id", CommonVar.logininfo.getMember_id());
            conn1.onExcute((isResult, data) -> {
                if (binding.tvAlarm.getText().toString().equals("꺼짐")) {
                    binding.tvAlarm.setText("켜짐");
                    setAlarmState();
                    Toast.makeText(getContext(), "알람이 켜졌습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    binding.tvAlarm.setText("꺼짐");
                    setAlarmState();
                    Toast.makeText(getContext(), "알람이 꺼졌습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        });
        binding.containerLinearChangePw.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
            startActivity(intent);
        });
        binding.containerLinearLogout.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("로그아웃 하시겠습니까?");
            builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CommonVar.logininfo = null;
                    SharedPreferences sharedPreferences = requireContext().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("loginInfo");
                    editor.apply();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
            // dialog로 로그아웃 할 것인지 확인 시키고 확인 했을 때 로그아웃 후 로그인 화면으로 전환
        });
        binding.containerLinearDeleteAccount.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), DeleteAccountActivity.class);
            startActivity(intent);
        });

        //고객센터
        binding.tvCs.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), CSBoardActivity.class);
            startActivity(intent);
        });
        //

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(this).load(CommonVar.logininfo.getMember_profileimg()).into(binding.imgvProfileImg);
        CommonConn conn = new CommonConn(getContext(), "setting/viewOption");
        conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
        conn.onExcute((isResult, data) -> {
            binding.tvNickname.setText(CommonVar.logininfo.getMember_nickname());
        });
        setAlarmState();
    }

    public void setAlarmState() {
        CommonConn conn = new CommonConn(getContext(), "setting/viewOption");
        conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
        conn.onExcute((isResult, data) -> {
            ArrayList<OptionVO> list = new Gson().fromJson(data, new TypeToken<ArrayList<OptionVO>>() {
            }.getType());
            if (list.get(0).getOption_godok_alarm().equals("N")) {
                binding.tvGodok.setText("꺼짐");
            } else {
                binding.tvGodok.setText("켜짐");
            }
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("NotificationPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (list.get(0).getOption_alarm().equals("N")) {
                binding.tvAlarm.setText("꺼짐");
                FirebaseMessageReceiver.setIsEnabled(getContext(), true);
                editor.putBoolean("notificationEnabled", isEnabled);
                editor.apply();
            } else {
                binding.tvAlarm.setText("켜짐");
                FirebaseMessageReceiver.setIsEnabled(getContext(), true);
                editor.putBoolean("notificationEnabled", isEnabled);
                editor.apply();
            }
        });
    }
}