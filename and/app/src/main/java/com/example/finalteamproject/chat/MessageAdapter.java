package com.example.finalteamproject.chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.finalteamproject.databinding.ItemMessageBinding;
import com.example.finalteamproject.main.FriendVO;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
    ItemMessageBinding binding;

    ArrayList<FriendVO> list;
    Context context;

    public MessageAdapter(ArrayList<FriendVO> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvNickname.setText(list.get(position).getMember_nickname());
        holder.binding.tvContent.setText(list.get(position).getContent());
        holder.binding.tvTime.setText(list.get(position).getTime());
        Glide.with(context).load(list.get(position).getMember_profileimg()).apply(new RequestOptions().circleCrop()).into(holder.binding.imgvProfileImg);
        holder.binding.containerLinearMessageChat.setOnClickListener(v -> {
            Intent intent = new Intent(context, MessageChatActivity.class);
            //   FriendListDTO dto = (FriendListDTO) getIntent().getSerializableExtra("dto");
//            MessageDTO dto = new MessageDTO(list.get(position).getImgRes(),list.get(position).getNickname(),list.get(position).getContent(),
//            list.get(position).getTime(),"",list.get(position).isCheck());
//            intent.putExtra("dto",dto);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemMessageBinding binding;
        public ViewHolder(@NonNull ItemMessageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
