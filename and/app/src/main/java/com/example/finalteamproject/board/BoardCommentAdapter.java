package com.example.finalteamproject.board;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.cs.CSBoardContextActivity;
import com.example.finalteamproject.databinding.ItemBoardCommentRecvBinding;
import com.example.finalteamproject.main.MainActivity;

import java.util.List;

public class BoardCommentAdapter extends RecyclerView.Adapter<BoardCommentAdapter.ViewHolder>{

    List<FavorBoardCommentVO> list;
    Context context;
    MainActivity activity;
    Fragment fragment;
    FavorBoardVO vo;
    public BoardCommentAdapter(List<FavorBoardCommentVO> list, Context context, Activity activity, Fragment fragment, FavorBoardVO vo) {
        this.list = list;
        this.context = context;
        this.activity = (MainActivity) activity;
        this.fragment = fragment;
        this.vo = vo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemBoardCommentRecvBinding binding = ItemBoardCommentRecvBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int i) {
        if(list.get(i).writer.equals(CommonVar.logininfo.getMember_id())){
            h.binding.tvModify.setVisibility(View.VISIBLE);
            h.binding.tvDelete.setVisibility(View.VISIBLE);
        }else {
            h.binding.tvModify.setVisibility(View.GONE);
            h.binding.tvDelete.setVisibility(View.GONE);
        }
        CommonConn conn = new CommonConn(context, "board/nickname");
        conn.addParamMap("id", list.get(i).writer);
        conn.onExcute((isResult, data) -> {
            h.binding.tvCommentWriter.setText(data);
        });
        h.binding.tvDate.setText(list.get(i).fav_board_comment_writedate);
        h.binding.tvCommentContent.setText(list.get(i).fav_board_comment_content);

        h.binding.tvModify.setOnClickListener(v -> {
                h.binding.lnComment.setVisibility(View.GONE);
                h.binding.lnModifyComment.setVisibility(View.VISIBLE);
                h.binding.tvCommentWriterModify.setText(CommonVar.logininfo.getMember_nickname());
                h.binding.edtCommentContentModify.setText(list.get(i).fav_board_comment_content);
        });
        h.binding.tvDelete.setOnClickListener(v -> {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
            builder.setTitle("댓글을 삭제하시겠습니까?");
            builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CommonConn conn = new CommonConn(context, "board/deleteComment");
                    conn.addParamMap("id", list.get(i).fav_board_comment_id);
                    conn.onExcute((isResult, data) -> {
                        if(data.equals("성공")){
                            Toast.makeText(context, "댓글 삭제 성공", Toast.LENGTH_SHORT).show();
                            activity.replaceFragment(fragment, new BoardContextFragment(activity, vo));
                        }else {
                            Toast.makeText(context, "댓글 삭제 실패", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            android.app.AlertDialog dialog = builder.create();
            dialog.show();

        });
        h.binding.tvModifyModify.setOnClickListener(v1 -> {
            if(h.binding.edtCommentContentModify.getText().toString().length()<1){
                Toast.makeText(activity, "내용을 입력하세요", Toast.LENGTH_SHORT).show();
            }else {
                CommonConn conn1 = new CommonConn(context, "board/updateComment");
                conn1.addParamMap("fav_board_comment_id", list.get(i).fav_board_comment_id);
                conn1.addParamMap("fav_board_comment_content", h.binding.edtCommentContentModify.getText().toString());
                conn1.onExcute((isResult1, data1) -> {
                    if(data1.equals("성공")){
                        Toast.makeText(context, "댓글 수정 성공", Toast.LENGTH_SHORT).show();
                        activity.replaceFragment(fragment, new BoardContextFragment(activity, vo));
                    }else {
                        Toast.makeText(context, "댓글 수정 실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        h.binding.tvModifyDelete.setOnClickListener(v -> {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
            builder.setTitle("댓글을 삭제하시겠습니까?");
            builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CommonConn conn = new CommonConn(context, "board/deleteComment");
                    conn.addParamMap("id", list.get(i).fav_board_comment_id);
                    conn.onExcute((isResult, data) -> {
                        if(data.equals("성공")){
                            Toast.makeText(context, "댓글 삭제 성공", Toast.LENGTH_SHORT).show();
                            activity.replaceFragment(fragment, new BoardContextFragment(activity, vo));
                        }else {
                            Toast.makeText(context, "댓글 삭제 실패", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            android.app.AlertDialog dialog = builder.create();
            dialog.show();

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ItemBoardCommentRecvBinding binding;

        public ViewHolder(@NonNull ItemBoardCommentRecvBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
