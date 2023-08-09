package com.example.finalteamproject.game;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalteamproject.databinding.ItemGameRankBinding;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {

    ItemGameRankBinding binding;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = ItemGameRankBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int i) {
        int rank = i+1;
        h.binding.gameRank.setText(rank+"");


    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemGameRankBinding binding;

        public ViewHolder(ItemGameRankBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
