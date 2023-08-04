package com.example.finalteamproject.chat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ItemMessageChatBinding;

import java.util.ArrayList;

public class MessageChatAdapter extends RecyclerView.Adapter<MessageChatAdapter.ViewHolder>{

    ItemMessageChatBinding binding;

    ArrayList<MessageDTO> list;
    Context context;

    boolean isChatCheck;

    public MessageChatAdapter(ArrayList<MessageDTO> list, Context context,boolean isChatCheck) {
        this.list = list;
        this.context = context;
        this.isChatCheck = isChatCheck;
    }

    public void addData(MessageDTO dto) {
        list.add(dto);
        notifyDataSetChanged();
    }

    public void removeData(MessageDTO dto) {
        list.remove(dto);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemMessageChatBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MessageDTO dto = list.get(position);
        holder.binding.tvName.setText(dto.getNickname());
        holder.binding.imgvMain.setImageResource(dto.getImgRes());

        holder.binding.containerFrame.removeAllViews();
        TextView tv_msg = new TextView(context);
        TextView tv_time = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        if (dto.isCheck()) {
            holder.binding.containerFrame.setLayoutParams(params2);
            holder.binding.tvName.setVisibility(View.GONE);
            holder.binding.imgvMain.setVisibility(View.GONE);
            holder.binding.cvMain.setVisibility(View.GONE);
            params2.gravity = Gravity.END;
            tv_msg.setText(dto.getContent());
            tv_msg.setTextColor(Color.parseColor("#000000"));
            tv_msg.setTextSize(16f);
            tv_msg.setBackgroundResource(R.drawable.message_chat_me_background);
            tv_msg.setPadding(30, 20, 70, 20);
            tv_msg.setMaxWidth(800);
            params.gravity = Gravity.BOTTOM | Gravity.END;
            params.setMargins(0, 0, 20, 0);
            tv_time.setLayoutParams(params);
            tv_time.setText(dto.getTime());
            tv_time.setTextColor(Color.parseColor("#000000"));
            tv_time.setTextSize(12f);
            holder.binding.containerFrame.addView(tv_time);
            holder.binding.containerFrame.addView(tv_msg);
        } else {
            tv_msg.setText(dto.getContent());
            tv_msg.setTextColor(Color.parseColor("#000000"));
            tv_msg.setTextSize(16f);
            tv_msg.setBackgroundResource(R.drawable.message_chat_background);
            tv_msg.setPadding(30, 20, 70, 20);
            tv_msg.setMaxWidth(800);
            params.gravity = Gravity.BOTTOM | Gravity.START;
            tv_time.setLayoutParams(params);
            tv_time.setText(dto.getTime());
            tv_time.setTextColor(Color.parseColor("#000000"));
            tv_time.setTextSize(12f);
            holder.binding.containerFrame.addView(tv_msg);
            holder.binding.containerFrame.addView(tv_time);
        }

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
