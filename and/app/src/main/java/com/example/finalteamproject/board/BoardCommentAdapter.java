package com.example.finalteamproject.board;

import android.app.Activity;
import android.content.Context;
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
import com.example.finalteamproject.databinding.ItemBoardCommentRecvBinding;
import com.example.finalteamproject.main.MainActivity;

import java.util.List;

public class BoardCommentAdapter extends RecyclerView.Adapter<BoardCommentAdapter.ViewHolder>{

    List<FavorBoardCommentVO> list;
    Context context;
    MainActivity activity;
    Fragment fragment;
    int fav_board_id;
    public BoardCommentAdapter(List<FavorBoardCommentVO> list, Context context, Activity activity, Fragment fragment, int fav_board_id) {
        this.list = list;
        this.context = context;
        this.activity = (MainActivity) activity;
        this.fragment = fragment;
        this.fav_board_id = fav_board_id;
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
        h.binding.tvCommentWriter.setText(list.get(i).writer);
        h.binding.tvDate.setText(list.get(i).fav_board_comment_writedate);
        h.binding.tvCommentContent.setText(list.get(i).fav_board_comment_content);

        h.binding.tvModify.setOnClickListener(v -> {
                h.binding.lnComment.setVisibility(View.GONE);
                h.binding.lnModifyComment.setVisibility(View.VISIBLE);
                h.binding.tvCommentWriterModify.setText(CommonVar.logininfo.getMember_nickname());
                h.binding.edtCommentContentModify.setText(list.get(i).fav_board_comment_content);
                h.binding.tvModifyModify.setOnClickListener(v1 -> {
                    if(h.binding.edtCommentContentModify.getText().toString().length()<1){
                        Toast.makeText(activity, "내용을 입력하세요", Toast.LENGTH_SHORT).show();
//                        h.binding.edtCommentContentModify.requestFocus();
//                        manager.showSoftInput(h.binding.edtCommentContentModify, InputMethodManager.SHOW_IMPLICIT);
                    }else {
                        CommonConn conn = new CommonConn(context, "board/updateComment");
                        conn.addParamMap("fav_board_comment_id", list.get(i).fav_board_comment_id);
                        conn.addParamMap("fav_board_comment_content", h.binding.edtCommentContentModify.getText().toString());
                        conn.onExcute((isResult, data) -> {
                            if(data.equals("성공")){
                                Toast.makeText(context, "댓글 수정 성공", Toast.LENGTH_SHORT).show();
                                activity.replaceFragment(fragment, new BoardContextFragment(activity, fav_board_id));
                            }else {
                                Toast.makeText(context, "댓글 수정 실패", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
        });
        h.binding.tvDelete.setOnClickListener(v -> {
            CommonConn conn = new CommonConn(context, "board/deleteComment");
            conn.addParamMap("id", list.get(i).fav_board_comment_id);
            conn.onExcute((isResult, data) -> {
                if(data.equals("성공")){
                    Toast.makeText(context, "댓글 삭제 성공", Toast.LENGTH_SHORT).show();
                    activity.replaceFragment(fragment, new BoardContextFragment(activity, fav_board_id));
                }else {
                    Toast.makeText(context, "댓글 삭제 실패", Toast.LENGTH_SHORT).show();
                }
            });
        });
        h.binding.tvModifyDelete.setOnClickListener(v -> {
            CommonConn conn = new CommonConn(context, "board/deleteComment");
            conn.addParamMap("id", list.get(i).fav_board_comment_id);
            conn.onExcute((isResult, data) -> {
                if(data.equals("성공")){
                    Toast.makeText(context, "댓글 삭제 성공", Toast.LENGTH_SHORT).show();
                    activity.replaceFragment(fragment, new BoardContextFragment(activity, fav_board_id));
                }else {
                    Toast.makeText(context, "댓글 삭제 실패", Toast.LENGTH_SHORT).show();
                }
            });
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
