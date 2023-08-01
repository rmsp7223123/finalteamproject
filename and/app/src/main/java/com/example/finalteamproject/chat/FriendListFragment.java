package com.example.finalteamproject.chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.FragmentFriendListBinding;

import java.util.ArrayList;

public class FriendListFragment extends Fragment {
    FragmentFriendListBinding binding;
    FriendListAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFriendListBinding.inflate(inflater,container,false);
        adapter = new FriendListAdapter(getList());
        binding.recvFriendList.setAdapter(adapter);
        binding.recvFriendList.setLayoutManager(new LinearLayoutManager(getContext()));
        return binding.getRoot();
    }

    public ArrayList<FriendListDTO> getList() {
        ArrayList<FriendListDTO> list = new ArrayList<>();
        list.add(new FriendListDTO(R.drawable.haerin2, "닉네임1"));
        list.add(new FriendListDTO(R.drawable.minji10, "닉네임2"));
        list.add(new FriendListDTO(R.drawable.hanni9, "닉네임3"));
        list.add(new FriendListDTO(R.drawable.danielle11, "닉네임4"));
        list.add(new FriendListDTO(R.drawable.hyein11, "닉네임5"));
        return list;
    }
}