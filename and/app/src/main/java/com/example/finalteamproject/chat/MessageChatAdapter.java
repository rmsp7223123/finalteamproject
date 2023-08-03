package com.example.finalteamproject.chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalteamproject.databinding.ItemMessageChatBinding;

import java.util.ArrayList;

public class MessageChatAdapter extends RecyclerView.Adapter<MessageChatAdapter.ViewHolder>{

    ItemMessageChatBinding binding;

    ArrayList<MessageAdapter> list;
    Context context;

    public MessageChatAdapter(ArrayList<MessageAdapter> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemMessageChatBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.containerLinearMessageChat.setOnClickListener(v -> {
            Intent intent = new Intent(context, MessageChatActivity.class);
            context.startActivity(intent);
        });

        

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemMessageChatBinding binding;
        public ViewHolder(@NonNull ItemMessageChatBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
