package com.example.finalteamproject.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.finalteamproject.R;

import java.util.List;

public class TestAdapter extends BaseAdapter {

    private List<String> mData;
    LayoutInflater inflater;


    public TestAdapter(List<String> data) {
        this.mData = data;
    }

    public TestAdapter(List<String> mData, LayoutInflater inflater) {
        this.mData = mData;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_main_friend, parent, false);


        return convertView;
    }

}