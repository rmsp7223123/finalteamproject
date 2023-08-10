package com.example.finalteamproject.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalteamproject.databinding.ItemAlarmHistoryBinding;

import java.util.ArrayList;
import java.util.List;

public class MainAlarmHistoryAdapter extends RecyclerView.Adapter<MainAlarmHistoryAdapter.ViewHolder> {

    ItemAlarmHistoryBinding binding;
    private ArrayList<String> dataList;

    public ArrayList<String> getDataList() {
        return dataList;
    }

    public MainAlarmHistoryAdapter(ArrayList<String> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemAlarmHistoryBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemAlarmHistoryBinding binding;
        public ViewHolder(@NonNull ItemAlarmHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}