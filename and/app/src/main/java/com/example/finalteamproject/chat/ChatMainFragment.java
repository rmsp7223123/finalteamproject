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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);
        manager = getActivity().getSupportFragmentManager();
        return binding.getRoot();
    }


}