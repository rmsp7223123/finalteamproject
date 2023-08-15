package com.example.finalteamproject.chat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.FragmentChatBinding;
import com.google.android.material.navigation.NavigationBarView;

public class ChatMainFragment extends Fragment {
    FragmentChatBinding binding;
    FragmentManager manager;

    Fragment fragment = null;

    boolean isAllFabVisible;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);
        manager = getActivity().getSupportFragmentManager();
        binding.fltbtnFriendList.setVisibility(View.GONE);
        binding.fltbtnMessage.setVisibility(View.GONE);
        isAllFabVisible = false;
        manager.beginTransaction().replace(R.id.container_frame_call_msg, new FriendListFragment()).commit();
        binding.fltbtnMenu.setOnClickListener(v -> {
            if(!isAllFabVisible) {
                binding.fltbtnMessage.show();
                binding.fltbtnFriendList.show();
                binding.fltbtnMenu.setImageResource(R.drawable.baseline_close_24);
                isAllFabVisible = true;
            } else {
                binding.fltbtnMessage.hide();
                binding.fltbtnFriendList.hide();
                binding.fltbtnMenu.setImageResource(R.drawable.baseline_add_24);
                isAllFabVisible = false;
            }
        });
        binding.fltbtnFriendList.setOnClickListener(v -> {
            fragment = new FriendListFragment();
            manager.beginTransaction().replace(R.id.container_frame_call_msg, fragment).commit();
        });
        binding.fltbtnMessage.setOnClickListener(v -> {
            fragment = new MessageFragment();
            manager.beginTransaction().replace(R.id.container_frame_call_msg, fragment).commit();
        });
        return binding.getRoot();
    }


}