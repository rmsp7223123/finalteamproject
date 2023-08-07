package com.example.finalteamproject.chat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.finalteamproject.FirebaseMessageReceiver;
import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivityMessageChatBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MessageChatActivity extends AppCompatActivity {
    ActivityMessageChatBinding binding;
    boolean isChatCheck = false;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    public static String messageId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessageChatBinding.inflate(getLayoutInflater());
        MessageChatAdapter adapter = new MessageChatAdapter(getlist(),this,isChatCheck);
        setContentView(binding.getRoot());
        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });

        MessageDTO messageDTO = (MessageDTO) getIntent().getSerializableExtra("dto");

        String itemName = messageDTO.getNickname();

        binding.tvNickname.setText(messageDTO.getNickname());
        binding.imgvProfileImg.setImageResource(messageDTO.getImgRes());
        binding.recvMessageChat.setAdapter(adapter);
        binding.recvMessageChat.setLayoutManager(new LinearLayoutManager(this));

        binding.imgvSend.setOnClickListener(v -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String currentTime = dateFormat.format(new Date());
            String messageText = binding.edtMessage.getText().toString();
            if(!messageText.isEmpty()) {
               // String name = getIntent().getStringExtra("nickname");
                //int imgRes = getIntent().getIntExtra("img",0);
                messageId = databaseReference.child("chat").child(itemName).push().getKey();
                MessageDTO temp = new MessageDTO(messageDTO.getImgRes(),messageDTO.getNickname(),messageText,currentTime,true);
                // 파이어베이스 경로를 닉네임이 아닌 id로 바꾸기
                databaseReference.child("chat").child(messageDTO.getNickname()).child(messageId).setValue(temp);
                adapter.notifyDataSetChanged();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        FirebaseMessageReceiver.showNotification(MessageChatActivity.this,binding.tvNickname.getText().toString(),binding.edtMessage.getText().toString());

                    }
                });
                binding.edtMessage.setText("");
            }
        });

        databaseReference.child("chat").child(messageDTO.getNickname()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MessageDTO MessageDTO = dataSnapshot.getValue(MessageDTO.class);
                MessageDTO.setImgRes(messageDTO.getImgRes());
                adapter.addData(MessageDTO);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                MessageDTO messageDTO = dataSnapshot.getValue(MessageDTO.class);
                adapter.removeData(messageDTO);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        binding.imgvSendFile.setOnClickListener(view -> {
            // 다이얼로그? 프래그먼트?
        });
    }

    public ArrayList<MessageDTO> getlist() {
        ArrayList<MessageDTO> list = new ArrayList<>();
        return list;
    }
}