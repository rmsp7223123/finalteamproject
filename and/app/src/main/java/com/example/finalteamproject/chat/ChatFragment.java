package com.example.finalteamproject.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.FragmentChatBinding;

public class ChatFragment extends Fragment {
    FragmentChatBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);
        binding.navigationRail.setOnItemSelectedListener(item -> {
            if(item.getItemId()==R.id.close) {
                binding.navigationRail.setVisibility(View.GONE);
            }
            return true;
        });
        binding.containerLinearOpen.setOnClickListener(view -> {
            binding.navigationRail.setVisibility(View.VISIBLE);
            binding.navigationRail.setSelectedItemId(R.id.friend_list);
        });
        binding.navigationRail.setSelectedItemId(R.id.friend_list);
        return binding.getRoot();

    }
}