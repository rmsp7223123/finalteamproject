package com.example.finalteamproject.chat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.FragmentMessageBinding;
import com.example.finalteamproject.main.FriendVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MessageFragment extends Fragment {
    FragmentMessageBinding binding;
    MessageAdapter adapter;

    int selectedPosition;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMessageBinding.inflate(inflater,container,false);
        adapter = new MessageAdapter(getList(),getContext());
        binding.recvMessage.setAdapter(adapter);
        binding.recvMessage.setLayoutManager(new LinearLayoutManager(getContext()));
        return binding.getRoot();
    }

    public ArrayList<MessageDTO> getList() {
        ArrayList<MessageDTO> list = new ArrayList<>();
        list.add(new MessageDTO(R.drawable.haerin2,"해린","내용1","12:34","",false));
        list.add(new MessageDTO(R.drawable.hanni9,"하니","내용2","11:34","",false));
        list.add(new MessageDTO(R.drawable.minji10,"민지","내용3","10:34","",false));
        list.add(new MessageDTO(R.drawable.hyein11,"혜인","내용4","14:34","",false));
        list.add(new MessageDTO(R.drawable.danielle11,"다니엘","내용5","15:34","",false));
        return list;
    }
}