package com.example.finalteamproject.board;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.databinding.FragmentBoardBinding;
import com.example.finalteamproject.main.BoardMainDTO;
import com.example.finalteamproject.main.MainActivity;
import com.example.finalteamproject.main.MainFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BoardFragment extends Fragment {

    MainActivity activity;
    FragmentBoardBinding binding;
    String board_name;
    String[] list = {"TV", "음악", "영화", "패션", "동물", "뉴스", "자동차", "운동", "게임"};
    int num;

    public BoardFragment(String board_name, Activity activity) {
        this.board_name = board_name;
        this.activity = (MainActivity) activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBoardBinding.inflate(inflater, container, false);

        binding.tvBoardTitle.setText(board_name);
        binding.spnBoard.getSelectedItem();
        Spinner favorSpinner = binding.spnBoard;
        ArrayAdapter favorAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_spinner_item, list);
        favorSpinner.setAdapter(favorAdapter);

        for (int i = 0; i < list.length; i++) {
            if(list[i].equals(board_name)){
                binding.spnBoard.setSelection(i);
            }
        }
        binding.spnBoard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                if(!list[i].equals(board_name)) {
                    board_name = list[i];
                    binding.tvBoardTitle.setText(list[i]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.tvBoardTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                CommonConn conn = new CommonConn(getContext(), "board/list");
                conn.addParamMap("favor", board_name);
                conn.onExcute((isResult, data) -> {
                    List<FavorBoardVO> list = new Gson().fromJson(data, new TypeToken<List<FavorBoardVO>>(){}.getType());
                    if(list!=null){
                        BoardListAdapter adapter = new BoardListAdapter(BoardFragment.this.getContext(), list);
                        binding.recvBoard.setAdapter(adapter);
                        binding.recvBoard.setLayoutManager(new LinearLayoutManager(BoardFragment.this.getContext()));

                    }else {
                        Toast.makeText(activity, "내용 없음", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        binding.cvNew.setOnClickListener(v -> {
            Log.d("result", "onCreateView: "+binding.tvBoardTitle.getText().toString());
            activity.replaceFragment(this, new NewBoardFragment(activity, binding.tvBoardTitle.getText().toString()));
        });

        CommonConn conn = new CommonConn(getContext(), "board/list");
        conn.addParamMap("favor", board_name);
        conn.onExcute((isResult, data) -> {
            List<FavorBoardVO> list = new Gson().fromJson(data, new TypeToken<List<FavorBoardVO>>(){}.getType());
            if(list!=null){
                BoardListAdapter adapter = new BoardListAdapter(this.getContext(), list);
                binding.recvBoard.setAdapter(adapter);
                binding.recvBoard.setLayoutManager(new LinearLayoutManager(this.getContext()));

            }else {
                Toast.makeText(activity, "내용 없음", Toast.LENGTH_SHORT).show();
            }
        });

        binding.imgvBack.setOnClickListener(v -> {
            activity.replaceFragment(this, new MainFragment(1));
        });


        return binding.getRoot();
    }

}