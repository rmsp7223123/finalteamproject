package com.example.finalteamproject.setting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.finalteamproject.Login.LoginActivity;
import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.FragmentSettingBinding;

public class SettingFragment extends Fragment {

    FragmentSettingBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(inflater,container,false);
        Glide.with(this).load(CommonVar.logininfo.getMember_profileimg()).into(binding.imgvProfileImg);
        binding.tvNickname.setText(CommonVar.logininfo.getMember_nickname());
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
            // 본인 회원가입 했을 때 주민등록번호나 핸드폰 번호를 활용해 검증 시키고 맞을 시 회원 탈퇴
        });
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(this).load(CommonVar.logininfo.getMember_profileimg()).into(binding.imgvProfileImg);
    }
}