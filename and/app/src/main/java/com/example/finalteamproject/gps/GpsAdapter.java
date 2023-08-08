package com.example.finalteamproject.gps;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalteamproject.databinding.ItemGpsBinding;

import java.util.ArrayList;

//내 주변 경로당 아이템
public class GpsAdapter extends RecyclerView.Adapter<GpsAdapter.ViewHolder> {
    ArrayList<GpsVO> list;
    GpsVO vo;
    Context context;

    public GpsAdapter(ArrayList<GpsVO> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGpsBinding binding = ItemGpsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        context = parent.getContext();
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int i) {
        h.binding.seniorName.setText(list.get(i).getSenior_name()+"");
        h.binding.seniorRoadaddress.setText(list.get(i).getSenior_roadaddress()+"");
        h.binding.seniorLike.setText(list.get(i).getSenior_like_num()+"");

        h.binding.itemSenior.setOnClickListener(v -> {
            Intent intent = new Intent(context, GpsDetailActivity.class);
//            intent.putExtra("")
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemGpsBinding binding;

        public ViewHolder(ItemGpsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
