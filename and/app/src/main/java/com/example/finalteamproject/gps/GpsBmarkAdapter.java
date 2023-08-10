package com.example.finalteamproject.gps;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalteamproject.databinding.ItemBmarkBinding;

import java.util.ArrayList;

public class GpsBmarkAdapter extends RecyclerView.Adapter<GpsBmarkAdapter.ViewHolder> {
    ItemBmarkBinding binding;
    ArrayList<GpsVO> list;

    public GpsBmarkAdapter(ArrayList<GpsVO> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        binding = ItemBmarkBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int i) {
        h.binding.seniorName.setText(list.get(i).getSenior_name());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemBmarkBinding binding;

        public ViewHolder(ItemBmarkBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
