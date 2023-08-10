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

    public BoardListAdapter(Activity activity, Fragment fragment,  List<FavorBoardVO> list) {
        this.activity = (MainActivity) activity;
        this.fragment = fragment;
        this.list = list;
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
        if(list.get(i).fav_board_img==null){
            h.binding.imgvImage.setImageResource(R.drawable.logo);
        }
        if(list.get(i).fav_board_title.length()>8){
            h.binding.tvTitle.setText(list.get(i).fav_board_title.substring(0, 8)+"...");
        }else {
            h.binding.tvTitle.setText(list.get(i).fav_board_title);
        }
        CommonConn conn1 = new CommonConn(fragment.getContext(), "board/nickname");
        conn1.addParamMap("id", list.get(i).writer);
        conn1.onExcute((isResult, data) -> {
            h.binding.tvWriter.setText(data);
        });
        CommonConn conn = new CommonConn(fragment.getContext(), "board/rec");
        conn.addParamMap("id", list.get(i).fav_board_id);
        conn.onExcute((isResult, data) -> {
            h.binding.tvRec.setText("추천 : "+data);
        });
        h.binding.tvView.setText("조회 : "+list.get(i).fav_board_writecount);
        CommonConn conn2 = new CommonConn(fragment.getContext(), "board/commentCnt");
        conn2.addParamMap("fav_board_id", list.get(i).fav_board_id);
        conn2.onExcute((isResult, data) -> {
            h.binding.tvComment.setText("댓글 : "+data);
        });

        h.binding.lnBoard.setOnClickListener(v -> {
            activity.replaceFragment(fragment, new BoardContextFragment(activity, list.get(i).fav_board_id));
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
