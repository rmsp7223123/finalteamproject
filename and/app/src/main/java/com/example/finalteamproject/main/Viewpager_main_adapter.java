package com.example.finalteamproject.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalteamproject.databinding.ViewpagerMainSliderBinding;

public class Viewpager_main_adapter extends RecyclerView.Adapter<Viewpager_main_adapter.ViewHolder> {

    ViewpagerMainSliderBinding binding;

    Context context;
    private int[] sliderImage;

    public Viewpager_main_adapter(Context context, int[] sliderImage) {
        this.context = context;
        this.sliderImage = sliderImage;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         binding = ViewpagerMainSliderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int actualPosition = position % sliderImage.length;
        holder.binding.imageSlider.setImageResource(sliderImage[actualPosition]);

    }

    @Override
    public int getItemCount() {
        return sliderImage.length * 100;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ViewpagerMainSliderBinding binding;

        public ViewHolder(@NonNull ViewpagerMainSliderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


    }
}
