package com.example.finalteamproject.setting;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.com.example.finalteamproject.common.CustomTextview;

import androidx.annotation.NonNull;

import com.example.finalteamproject.R;

public class FontColorArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    private final int[] colors;

    public FontColorArrayAdapter(Context context, String[] values, int[] colors){
        super(context,android.R.layout.select_dialog_singlechoice, values);
        this.context = context;
        this.values = values;
        this.colors = colors;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        com.example.finalteamproject.common.CustomTextview com.example.finalteamproject.common.CustomTextview = (com.example.finalteamproject.common.CustomTextview) super.getView(position, convertView, parent);
        com.example.finalteamproject.common.CustomTextview.setTextColor(colors[position]);
        return com.example.finalteamproject.common.CustomTextview;
    }
}
