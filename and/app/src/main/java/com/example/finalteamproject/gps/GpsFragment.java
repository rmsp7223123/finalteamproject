package com.example.finalteamproject.gps;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.FragmentGpsBinding;

//지도 나오는 첫 페이지
public class GpsFragment extends Fragment {

    FragmentGpsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGpsBinding.inflate(inflater,container,false);
        binding.recvGps.setAdapter(new GpsAdapter(getContext()));
        binding.recvGps.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.tvMore.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), GpsLikeActivity.class);
            startActivity(intent);
        });

        return binding.getRoot();
    }
}