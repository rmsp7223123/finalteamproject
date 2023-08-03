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
import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ItemFriendListBinding;

import java.util.ArrayList;
import java.util.List;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {
    ItemFriendListBinding binding;
    ArrayList<FriendListDTO> list;

    Context context;

    public FriendListAdapter(ArrayList<FriendListDTO> list, Context context) {
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
        holder.binding.imgvProfileImg.setImageResource(list.get(position).getImgRes());
        holder.binding.tvNickname.setText(list.get(position).getNickname());
        Glide.with(context).load(list.get(position).getImgRes()).apply(new RequestOptions().circleCrop()).into(holder.binding.imgvProfileImg);
        holder.binding.containerFrameCall.setOnClickListener(v -> {
            Intent intent = new Intent(context, InCallActivity.class);
            context.startActivity(intent);
        });
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
