package com.example.finalteamproject.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.databinding.ItemFriendListBinding;
import com.example.finalteamproject.main.FriendVO;

import java.util.ArrayList;


public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {
    ItemFriendListBinding binding;
    ArrayList<FriendVO> list;

    Context context;

    public FriendListAdapter(ArrayList<FriendVO> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemFriendListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvNickname.setText(list.get(position).getMember_nickname());
        Glide.with(context).load(list.get(position).getMember_profileimg()).apply(new RequestOptions().circleCrop()).into(holder.binding.imgvProfileImg);
//        conn.addParamMap("member_id", );
//        holder.binding.imgvProfileImg.setImageResource(list.get(position).getImgRes());
//        holder.binding.tvNickname.setText(list.get(position).getNickname());
//        Glide.with(context).load(list.get(position).getImgRes()).apply(new RequestOptions().circleCrop()).into(holder.binding.imgvProfileImg);
//        holder.binding.containerFrameMessage.setOnClickListener(v -> {
//            Intent intent = new Intent(context, MessageChatActivity.class);
//            intent.putExtra("dto",list.get(position));
//            intent.putExtra("nickname",list.get(position).getNickname());
//            intent.putExtra("img",list.get(position).getImgRes());
//            context.startActivity(intent);
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemFriendListBinding binding;
        public ViewHolder(@NonNull ItemFriendListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
