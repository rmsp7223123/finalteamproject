package com.example.finalteamproject.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
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
        holder.binding.containerLinearProfile.setOnClickListener(v -> {
            FriendDetailFragment friendDetailFragment = new FriendDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("friend_detail", list.get(position));
            friendDetailFragment.setArguments(bundle);
            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container_frame_profile, friendDetailFragment);
            transaction.addToBackStack(null);
            transaction.commit();
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
