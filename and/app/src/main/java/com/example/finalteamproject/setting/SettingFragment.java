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
        return binding.getRoot();
    }
}