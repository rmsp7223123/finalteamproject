package com.example.finalteamproject.chat;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.example.finalteamproject.databinding.ItemGridvPhotoBinding;

import java.util.ArrayList;

public class PhotoGridvAdapter extends BaseAdapter {

    LayoutInflater inflater;

    ChatPhotoGalleryActivity context;

    ArrayList<String> list;

    public PhotoGridvAdapter(LayoutInflater inflater, ChatPhotoGalleryActivity context, ArrayList<String> list) {
        this.inflater = inflater;
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemGridvPhotoBinding binding = ItemGridvPhotoBinding.inflate(inflater, parent, false);
        Glide.with(context).load(list.get(position)).into(binding.imgvMain);
        binding.imgvMain.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChatPhotoDetailActivity.class);
            intent.putExtra("image", list.get(position));
            context.startActivity(intent);
        });
        return binding.getRoot();
    }
}
