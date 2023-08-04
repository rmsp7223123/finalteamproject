package com.example.finalteamproject.game;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalteamproject.databinding.FragmentGameBinding;

public class GameFragment extends Fragment {

    FragmentGameBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentGameBinding.inflate(inflater, container, false);

        binding.btnGameStart.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), GameActivity.class);
            startActivity(intent);
        });



        return binding.getRoot();
    }
}