package com.example.finalteamproject.board;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.databinding.FragmentBoardBinding;
import com.example.finalteamproject.main.BoardMainDTO;

import java.util.ArrayList;

public class BoardFragment extends Fragment {

    FragmentBoardBinding binding;

    String board_name;

    public BoardFragment(String board_name) {
        this.board_name = board_name;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBoardBinding.inflate(inflater, container, false);
//        CommonConn conn = new CommonConn(this.getContext(), "board/favorlist");
//        conn.onExcute((isResult, data) -> {
//            if()
//        });
//        binding.spnBoard.



        return binding.getRoot();
    }
}