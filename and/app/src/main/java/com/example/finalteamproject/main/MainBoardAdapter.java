package com.example.finalteamproject.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalteamproject.databinding.ItemMainBoardBinding;

public class MainBoardAdapter extends RecyclerView.Adapter<MainBoardAdapter.ViewHolder>{

    ItemMainBoardBinding binding;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemMainBoardBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemMainBoardBinding binding;
        public ViewHolder(@NonNull ItemMainBoardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
