package com.example.finalteamproject.gps;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalteamproject.databinding.ItemGpsBinding;

//내 주변 경로당 아이템
public class GpsAdapter extends RecyclerView.Adapter<GpsAdapter.ViewHolder> {
    ItemGpsBinding binding;
    Context context;

    public GpsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = ItemGpsBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int i) {


        h.binding.itemSenior.setOnClickListener(v -> {
            Intent intent = new Intent(context, GpsDetailActivity.class);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemGpsBinding binding;

        public ViewHolder(ItemGpsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
