package com.example.finalteamproject.game;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalteamproject.databinding.ItemGameRankBinding;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {

    ItemGameRankBinding binding;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemGameRankBinding binding;

        public ViewHolder(@NonNull View itemView, ItemGameRankBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
