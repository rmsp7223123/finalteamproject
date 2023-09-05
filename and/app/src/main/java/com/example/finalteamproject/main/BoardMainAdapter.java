package com.example.finalteamproject.main;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalteamproject.Login.CustomProgressDialog;
import com.example.finalteamproject.board.BoardCommonVar;
import com.example.finalteamproject.databinding.ItemBoardMainRecvBinding;

import java.util.ArrayList;

public class BoardMainAdapter extends RecyclerView.Adapter<BoardMainAdapter.ViewHolder> {

    ArrayList<BoardMainDTO> list;
    MainActivity activity;
    Fragment fragment;

    Context context;


    public BoardMainAdapter(Fragment fragment, ArrayList<BoardMainDTO> list, Activity activity, Context context) {
        this.fragment = fragment;
        this.list = list;
        this.activity = (MainActivity) activity;
        this.context = context;
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
            CustomProgressDialog dialog = new CustomProgressDialog(context);
            dialog.show();
            BoardCommonVar.board_name = list.get(i).getTv_board_name();
            BoardCommonVar.board_align = "최신순";
            activity.changeFragment(fragment, activity, i);
            dialog.dismiss();
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
