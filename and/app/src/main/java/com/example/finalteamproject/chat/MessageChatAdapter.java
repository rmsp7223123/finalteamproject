package com.example.finalteamproject.chat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ItemMessageChatBinding;
import com.example.finalteamproject.main.FriendVO;
import com.example.finalteamproject.setting.ChangeProfileActivity;

import java.util.ArrayList;

public class MessageChatAdapter extends RecyclerView.Adapter<MessageChatAdapter.ViewHolder>{

    ItemMessageChatBinding binding;

    ArrayList<FriendVO> list;
    Context context;

    boolean isChatCheck;
    String profile_img;
    public MessageChatAdapter(ArrayList<FriendVO> list, Context context,boolean isChatCheck ,String profile_img) {
        this.list = list;
        this.context = context;
        this.isChatCheck = isChatCheck;
        this.profile_img = profile_img;
    }

    public MessageChatAdapter(ArrayList<FriendVO> list, Context context,boolean isChatCheck) {
        this.list = list;
        this.context = context;
        this.isChatCheck = isChatCheck;
    }

    public void addData(FriendVO dto) {
        list.add(dto);
        notifyDataSetChanged();
    }

    public void removeData(FriendVO dto) {
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
        FriendVO friendVO = list.get(position);
        holder.binding.tvName.setText(friendVO.getMember_nickname());
        Glide.with(context).load(profile_img).into(holder.binding.imgvMain);
//        holder.binding.imgvMain.setImageResource(friendVO.getImgRes());

        holder.binding.containerFrame.removeAllViews();
        ImageView imageView = new ImageView(context);
        TextView tv_msg = new TextView(context);
        TextView tv_time = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(500, 500);

        if (friendVO.isCheck()) {
            holder.binding.containerFrame.setLayoutParams(params2);
            holder.binding.tvName.setVisibility(View.GONE);
            holder.binding.imgvMain.setVisibility(View.GONE);
            holder.binding.cvMain.setVisibility(View.GONE);
            params2.gravity = Gravity.END;

            if(list.get(position).getContent().contains("https://firebasestorage.googleapis.com/")) {
                imageView.setLayoutParams(params3);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(context).load(list.get(position).getContent()).into(imageView);
                holder.binding.containerFrame.addView(imageView);
            } else {
                tv_msg.setText(friendVO.getContent());
                tv_msg.setTextColor(Color.parseColor("#000000"));
                tv_msg.setTextSize(16f);
                tv_msg.setBackgroundResource(R.drawable.message_chat_me_background);
                tv_msg.setPadding(30, 20, 70, 20);
                tv_msg.setMaxWidth(800);
            }
            params.gravity = Gravity.BOTTOM | Gravity.END;
            params.setMargins(0, 0, 20, 0);
            tv_time.setLayoutParams(params);
            tv_time.setText(friendVO.getTime());
            tv_time.setTextColor(Color.parseColor("#000000"));
            tv_time.setTextSize(12f);
            holder.binding.containerFrame.addView(tv_msg);
            holder.binding.containerFrame.addView(tv_time);
        } else {
            params.gravity = Gravity.BOTTOM | Gravity.START;
            if(list.get(position).getContent().contains("https://firebasestorage.googleapis.com/")) {
                imageView.setLayoutParams(params3);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                Glide.with(context).load(list.get(position).getContent()).into(imageView);
                holder.binding.containerFrame.addView(imageView);
            } else {
                tv_msg.setText(friendVO.getContent());
                tv_msg.setTextColor(Color.parseColor("#000000"));
                tv_msg.setTextSize(16f);
                tv_msg.setBackgroundResource(R.drawable.message_chat_background);
                tv_msg.setPadding(30, 20, 70, 20);
                tv_msg.setMaxWidth(800);
            }
            tv_time.setLayoutParams(params);
            tv_time.setText(friendVO.getTime());
            tv_time.setTextColor(Color.parseColor("#000000"));
            tv_time.setTextSize(12f);
            holder.binding.containerFrame.addView(tv_msg);
            holder.binding.containerFrame.addView(tv_time);
        }

        holder.binding.containerLinearMessageChat.setOnClickListener(v -> {
            Intent intent = new Intent(context, MessageChatActivity.class);
            context.startActivity(intent);
        });

        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChatPhotoDetailActivity.class);
            intent.putExtra("image", list.get(position).getContent());
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
