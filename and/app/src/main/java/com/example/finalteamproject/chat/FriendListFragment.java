package com.example.finalteamproject.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.FragmentFriendListBinding;

public class FriendListFragment extends Fragment {
    FragmentFriendListBinding binding;
    FriendListAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFriendListBinding.inflate(inflater,container,false);
        adapter = new FriendListAdapter();
        binding.recvFriendList.setAdapter(adapter);
        return binding.getRoot();
    }
}