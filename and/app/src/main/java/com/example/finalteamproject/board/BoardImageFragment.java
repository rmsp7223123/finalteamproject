package com.example.finalteamproject.board;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.FragmentBoardImageBinding;
import com.example.finalteamproject.main.MainActivity;


public class BoardImageFragment extends Fragment {

  FragmentBoardImageBinding binding;
  MainActivity activity;
    FavorBoardVO vo;

    public BoardImageFragment(MainActivity activity, FavorBoardVO vo) {
        this.activity = activity;
        this.vo = vo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBoardImageBinding.inflate(inflater, container, false);

        binding.imgvBack.setOnClickListener(v -> {
            activity.replaceFragment(this, new BoardContextFragment(activity, vo));
        });

        Glide.with(this).load(vo.fav_board_img).into(binding.imgvImage);


        return binding.getRoot();
    }
}