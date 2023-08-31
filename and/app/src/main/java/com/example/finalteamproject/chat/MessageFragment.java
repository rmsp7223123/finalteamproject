package com.example.finalteamproject.chat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
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
import com.example.finalteamproject.main.AlarmVO;
import com.example.finalteamproject.main.FriendVO;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MessageFragment extends Fragment {
    FragmentMessageBinding binding;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    MessageAdapter adapter;

    ArrayList<FriendVO> list =  new ArrayList<>();

    DatabaseReference def = databaseReference.child("chat").child(CommonVar.logininfo.getMember_id());
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMessageBinding.inflate(inflater,container,false);
        adapter = new MessageAdapter(list, getContext());
        binding.recvMessage.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recvMessage.setAdapter(adapter);
        def.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.d("TAG", "onChildAdded: " + snapshot.getKey());
                lastChatSelect(snapshot.getKey());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return binding.getRoot();
    }

    public ArrayList<FriendVO> getList() {
        ArrayList<FriendVO> list = new ArrayList<>();
        return list;
    }

    public void lastChatSelect(String key) {
        DatabaseReference childDef = def.child(key);
        childDef.limitToLast(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                FriendVO vo = snapshot.getValue(FriendVO.class);
                if(vo.getContent().contains("https://firebasestorage.googleapis.com/")) {
                    list.add(new FriendVO(CommonVar.logininfo.getMember_id(), vo.getFriend_id(),vo.getMember_nickname(),vo.getMember_profileimg(), vo.getTime(), "이미지", true));
                } else {
                    list.add(new FriendVO(CommonVar.logininfo.getMember_id(), vo.getFriend_id(),vo.getMember_nickname(),vo.getMember_profileimg(), vo.getTime(), vo.getContent(), true));
                }



                adapter.notifyDataSetChanged();

                if(list.size() != 0 ) {
                    binding.containerLinearChatList.setVisibility(View.GONE);
                } else {
                    binding.containerLinearChatList.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}