package com.example.finalteamproject.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.finalteamproject.databinding.ViewpagerMainSliderBinding;

import java.util.ArrayList;

public class Viewpager_main_adapter extends RecyclerView.Adapter<Viewpager_main_adapter.ViewHolder> {

    ViewpagerMainSliderBinding binding;

    Context context;
    ArrayList<Integer> images;

    public Viewpager_main_adapter(Context context, ArrayList<Integer> images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         binding = ViewpagerMainSliderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int actualPosition = position % images.size();
        holder.binding.imageSlider.setImageResource(images.get(actualPosition));
        Glide.with(context).load(images.get(actualPosition)).apply(new RequestOptions().circleCrop()).into(holder.binding.imageSlider);

    }

    @Override
    public int getItemCount() {
        return images.size() * 100;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ViewpagerMainSliderBinding binding;

        public ViewHolder(@NonNull ViewpagerMainSliderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}
