package com.example.finalteamproject.board;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.databinding.ItemBoardListRecvBinding;

import java.util.ArrayList;
import java.util.List;

public class BoardListAdapter extends RecyclerView.Adapter<BoardListAdapter.ViewHolder>{

    Context context;
    List<FavorBoardVO> list;

    public BoardListAdapter(Context context, List<FavorBoardVO> list) {
        this.context = context;
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
        h.binding.tvTitle.setText(list.get(i).fav_board_title);
        h.binding.tvWriter.setText(list.get(i).writer);
        CommonConn conn = new CommonConn(context, "board/rec");
        conn.addParamMap("id", list.get(i).fav_board_id);
        conn.onExcute((isResult, data) -> {
            h.binding.tvRec.setText(data);
        });
        h.binding.tvView.setText(list.get(i).fav_board_writecount+"");
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
