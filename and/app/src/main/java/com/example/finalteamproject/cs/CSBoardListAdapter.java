package com.example.finalteamproject.cs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalteamproject.board.BoardListAdapter;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.databinding.ItemBoardListRecvBinding;
import com.example.finalteamproject.databinding.ItemCsBoardListRecvBinding;
import com.example.finalteamproject.main.MainActivity;

import java.util.List;

public class CSBoardListAdapter extends RecyclerView.Adapter<CSBoardListAdapter.ViewHolder>{
    List<CSBoardVO> list;
    Context context;

    public CSBoardListAdapter(List<CSBoardVO> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCsBoardListRecvBinding binding = ItemCsBoardListRecvBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int i) {
        if(list.get(i).csboard_title.length()>8){
            h.binding.tvTitle.setText(list.get(i).csboard_title.substring(0, 8)+"...");
        }else {
            h.binding.tvTitle.setText(list.get(i).csboard_title);
        }
        h.binding.tvWritedate.setText(list.get(i).csboard_writedate);
        CommonConn conn = new CommonConn(context, "board/nickname");
        conn.addParamMap("id", list.get(i).writer);
        conn.onExcute((isResult, data) -> {
            h.binding.tvWriter.setText(data);
        });
        CommonConn conn1 = new CommonConn(context, "csboard/commentCheck");
        conn1.addParamMap("id", list.get(i).csboard_id);
        conn1.onExcute((isResult, data) -> {
            if(data.equals("있음")){
                h.binding.lnCommentDone.setVisibility(View.VISIBLE);
                h.binding.lnComment.setVisibility(View.INVISIBLE);
            }else {
                h.binding.lnCommentDone.setVisibility(View.INVISIBLE);
                h.binding.lnComment.setVisibility(View.VISIBLE);
            }
        });

        h.binding.lnBoard.setOnClickListener(v -> {
            Intent intent = new Intent(context, CSBoardContextActivity.class);
            intent.putExtra("csboard_id" , list.get(i).csboard_id);
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemCsBoardListRecvBinding binding;

        public ViewHolder(@NonNull ItemCsBoardListRecvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
