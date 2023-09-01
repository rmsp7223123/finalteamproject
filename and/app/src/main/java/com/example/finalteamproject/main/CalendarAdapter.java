package com.example.finalteamproject.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalteamproject.databinding.ItemCalendarBinding;
import com.example.finalteamproject.databinding.ItemCalendarListBinding;
import com.example.finalteamproject.databinding.ItemMessageBinding;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder>{

    ItemCalendarListBinding binding;

    ArrayList<CalendarVO> calendarList;

    Context context;

    public CalendarAdapter(ArrayList<CalendarVO> calendarList, Context context) {
        this.calendarList = calendarList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemCalendarListBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvContent.setText(calendarList.get(position).calendar_content);

    }

    @Override
    public int getItemCount() {
        return calendarList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemCalendarListBinding binding;

        public ViewHolder(@NonNull ItemCalendarListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
