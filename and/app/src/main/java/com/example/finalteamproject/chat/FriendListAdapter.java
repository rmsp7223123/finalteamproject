package com.example.finalteamproject.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalteamproject.databinding.ItemFriendListBinding;

import java.util.ArrayList;
import java.util.List;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {
    ItemFriendListBinding binding;
    ArrayList<FriendListDTO> list;

    public FriendListAdapter(ArrayList<FriendListDTO> list) {
        this.list = list;
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
