package com.example.finalteamproject.gps;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalteamproject.databinding.ItemGpslikeBinding;

//자주가는 경로당 아이템(좋아요 버튼 외)
public class GpsLikeAdapter extends RecyclerView.Adapter<GpsLikeAdapter.ViewHolder> {
    ItemGpslikeBinding binding;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding=ItemGpslikeBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int i) {
        h.binding.unlike.setVisibility(View.GONE);
        h.binding.unlike.setOnClickListener(v -> {
            h.binding.unlike.setVisibility(View.GONE);
            h.binding.like.setVisibility(View.VISIBLE);
        });
        h.binding.like.setOnClickListener(v -> {
            h.binding.like.setVisibility(View.GONE);
            h.binding.unlike.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemGpslikeBinding binding;

        public ViewHolder(ItemGpslikeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
