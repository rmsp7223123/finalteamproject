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
import com.example.finalteamproject.common.CommonVar;
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
        FriendVO vo = new FriendVO(CommonVar.logininfo.getMember_id(), list.get(position).getFriend_id(), list.get(position).getMember_nickname(), list.get(position).getMember_profileimg(), "", "", false);
        // FriendVO friendVO = new FriendVO(CommonVar.logininfo.getMember_id(), list.get(position).getMember_id(), list.get(position).getMember_nickname(), list.get(position).getMember_profileimg(), "", "", false);
        String nickname = list.get(position).getMember_nickname();
        String content = list.get(position).getContent();
        if (nickname.length() > 7) {
            holder.binding.tvNickname.setText(nickname.substring(0, 7) + "...");
        } else {
            holder.binding.tvNickname.setText(nickname);
        }
        if(content.length() > 10) {
            holder.binding.tvContent.setText(content.substring(0, 10) + "...");
        } else {
            holder.binding.tvContent.setText(list.get(position).getContent());
        }
        holder.binding.tvTime.setText(list.get(position).getTime());
        Glide.with(context).load(list.get(position).getMember_profileimg()).apply(new RequestOptions().circleCrop()).into(holder.binding.imgvProfileImg);
        holder.binding.containerLinearMessageChat.setOnClickListener(v -> {
            Intent intent = new Intent(context, MessageChatActivity.class);
            intent.putExtra("vo",vo);
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
