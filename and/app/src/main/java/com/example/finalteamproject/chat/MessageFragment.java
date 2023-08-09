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
import com.example.finalteamproject.databinding.FragmentMessageBinding;

import java.util.ArrayList;

public class MessageFragment extends Fragment {
    FragmentMessageBinding binding;
    MessageAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMessageBinding.inflate(inflater,container,false);
        adapter = new MessageAdapter(getList(),getContext());
        binding.recvMessage.setAdapter(adapter);
        binding.recvMessage.setLayoutManager(new LinearLayoutManager(getContext()));
        Glide.with(getContext()).load(R.drawable.baseline_add_circle_24_white).apply(new RequestOptions().circleCrop()).into(binding.imgvAdd);
        binding.imgvAdd.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            // 친구목록을 프로필 이미지, 닉네임이 보이게 다이얼로그로 보여주고 클릭시 이미있는 채팅방 연결 혹은 새로운 채팅방 만들기
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("친구목록");
            builder.setMessage(bundle.getString("nickname"));
            builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });


            AlertDialog dialog = builder.create();
            dialog.show();
        });
        return binding.getRoot();
    }

    public ArrayList<MessageDTO> getList() {
        ArrayList<MessageDTO> list = new ArrayList<>();
        list.add(new MessageDTO(R.drawable.haerin2,"해린","내용1","12:34",false));
        list.add(new MessageDTO(R.drawable.hanni9,"하니","내용2","11:34",false));
        list.add(new MessageDTO(R.drawable.minji10,"민지","내용3","10:34",false));
        list.add(new MessageDTO(R.drawable.hyein11,"혜인","내용4","14:34",false));
        list.add(new MessageDTO(R.drawable.danielle11,"다니엘","내용5","15:34",false));
        return list;
    }
}