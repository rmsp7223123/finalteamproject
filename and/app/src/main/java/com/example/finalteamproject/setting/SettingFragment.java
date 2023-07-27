package com.example.finalteamproject.setting;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.FragmentSettingBinding;

public class SettingFragment extends Fragment {

    FragmentSettingBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(inflater,container,false);
        binding.containerLinearChangeProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ChangeProfileActivity.class);
            startActivity(intent);
        });
        binding.containerLinearChangeFont.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ChangeFontActivity.class);
            startActivity(intent);
        });
        binding.containerLinearChangeAlarm.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ChangeAlarmActivity.class);
            startActivity(intent);
        });
        binding.containerLinearChangePw.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ChangePasswordActivity.class);
            startActivity(intent);
        });
        binding.containerLinearLogout.setOnClickListener(v -> {
            // dialog로 로그아웃 할 것인지 확인 시키고 확인 했을 때 로그아웃 후 로그인 화면으로 전환
        });
        binding.containerLinearDeleteAccount.setOnClickListener(v -> {
            // 본인 회원가입 했을 때 주민등록번호나 핸드폰 번호를 활용해 검증 시키고 맞을 시 회원 탈퇴
        });
        return binding.getRoot();
    }
}