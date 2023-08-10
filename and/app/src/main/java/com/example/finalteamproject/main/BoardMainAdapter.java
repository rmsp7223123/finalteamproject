package com.example.finalteamproject.main;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalteamproject.board.BoardCommonVar;
import com.example.finalteamproject.databinding.ItemBoardMainRecvBinding;

import java.util.ArrayList;

public class BoardMainAdapter extends RecyclerView.Adapter<BoardMainAdapter.ViewHolder> {

    ArrayList<BoardMainDTO> list;
    MainActivity activity;
    Fragment fragment;

    public BoardMainAdapter(Fragment fragment, ArrayList<BoardMainDTO> list, Activity activity) {
        this.fragment = fragment;
        this.list = list;
        this.activity = (MainActivity) activity;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemBoardMainRecvBinding binding = ItemBoardMainRecvBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int i) {
        h.binding.imgvIcon.setImageResource(list.get(i).getImgv_icon());
        h.binding.tvBoardName.setText(list.get(i).getTv_board_name());
        h.binding.imgvMove.setImageResource(list.get(i).getImgv_move());
        h.binding.lnBoardSelect.setOnClickListener(v -> {
            BoardCommonVar.board_name = list.get(i).getTv_board_name();
            BoardCommonVar.board_align = "최신순";
            activity.changeFragment(fragment,  activity);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemBoardMainRecvBinding binding;

        public ViewHolder(@NonNull ItemBoardMainRecvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
