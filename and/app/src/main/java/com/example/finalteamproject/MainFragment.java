package com.example.finalteamproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalteamproject.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {

    FragmentMainBinding binding;

    int[] images = new int[]{
            R.drawable.haerin2,
            R.drawable.minji10,
            R.drawable.minji12,
            R.drawable.danielle11,
            R.drawable.hanni9,
            R.drawable.hyein11
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container,false);
        Viewpager_main_adapter adapter = new Viewpager_main_adapter(getContext(), images);
        binding.imgViewpager.setAdapter(adapter);
        return binding.getRoot();
    }
}