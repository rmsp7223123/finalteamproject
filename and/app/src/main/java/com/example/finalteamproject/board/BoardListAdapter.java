package com.example.finalteamproject.board;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.databinding.ItemBoardListRecvBinding;
import com.example.finalteamproject.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class BoardListAdapter extends RecyclerView.Adapter<BoardListAdapter.ViewHolder>{

    List<FavorBoardVO> list;
    MainActivity activity;
    Fragment fragment;
    int fav_board_id;
    String board_name;
    String align;

    public BoardListAdapter(Activity activity, Fragment fragment,  List<FavorBoardVO> list, String board_name, String align) {
        this.activity = (MainActivity) activity;
        this.fragment = fragment;
        this.list = list;
        this.board_name = board_name;
        this.align = align;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemBoardListRecvBinding binding = ItemBoardListRecvBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int i) {
        fav_board_id = list.get(i).fav_board_id;
        if(list.get(i).fav_board_img==null){
            h.binding.imgvImage.setImageResource(R.drawable.logo);
        }
        if(list.get(i).fav_board_title.length()>8){
            h.binding.tvTitle.setText(list.get(i).fav_board_title.substring(0, 8)+"...");
        }else {
            h.binding.tvTitle.setText(list.get(i).fav_board_title);
        }
        h.binding.tvWriter.setText(list.get(i).writer);
        CommonConn conn = new CommonConn(fragment.getContext(), "board/rec");
        conn.addParamMap("id", list.get(i).fav_board_id);
        conn.onExcute((isResult, data) -> {
            h.binding.tvRec.setText("추천 : "+data);
        });
        h.binding.tvView.setText("조회 : "+list.get(i).fav_board_writecount);

        h.binding.lnBoard.setOnClickListener(v -> {
            activity.replaceFragment(fragment, new BoardContextFragment(activity, fav_board_id, board_name, align));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemBoardListRecvBinding binding;

        public ViewHolder(@NonNull ItemBoardListRecvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
