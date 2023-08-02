package com.example.finalteamproject.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalteamproject.databinding.ItemMessageBinding;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
    ItemMessageBinding binding;

    ArrayList<MessageDTO> list;

    public MessageAdapter(ArrayList<MessageDTO> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.imgvProfileImg.setImageResource(list.get(position).getImgRes());
        holder.binding.tvNickname.setText(list.get(position).getNickname());
        holder.binding.tvContent.setText(list.get(position).getContent());
        holder.binding.tvTime.setText(list.get(position).getTime());
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
