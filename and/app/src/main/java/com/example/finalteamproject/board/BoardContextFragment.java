package com.example.finalteamproject.board;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.FragmentBoardContextBinding;
import com.example.finalteamproject.main.MainActivity;
import com.google.gson.Gson;


public class BoardContextFragment extends Fragment {

    MainActivity activity;
    FragmentBoardContextBinding binding;
    int fav_board_id;
    boolean like;
    String board_name;
    String align;

    public BoardContextFragment(Activity activity, int fav_board_id, String board_name, String align) {
        this.activity = (MainActivity) activity;
        this.fav_board_id = fav_board_id;
        this.board_name = board_name;
        this.align = align;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBoardContextBinding.inflate(inflater, container, false);

        binding.imgvBack.setOnClickListener(v -> {
//            CommonConn conn = new CommonConn(getContext(), "board/favorName");
//            conn.addParamMap("id", fav_board_id);
//            conn.onExcute((isResult, data) -> {
                activity.changeFragment(this, board_name, align, activity);
//            });
        });

        CommonConn conn5 = new CommonConn(getContext(), "board/viewCnt");
        conn5.addParamMap("id", fav_board_id);
        conn5.onExcute((isResult, data) -> {
        });

        CommonConn conn = new CommonConn(this.getContext(), "board/select");
        conn.addParamMap("fav_board_id", fav_board_id);
        conn.onExcute((isResult, data) -> {
            FavorBoardVO vo = new Gson().fromJson(data, FavorBoardVO.class);
            if(vo!=null){
                CommonConn conn1 = new CommonConn(this.getContext(), "board/selectFavor");
                conn1.addParamMap("fav_board_id", fav_board_id);
                conn1.onExcute((isResult1, data1) -> {
                    binding.tvBoardName.setText(data1);
                });
                binding.tvTitle.setText(vo.fav_board_title);
                binding.tvDate.setText(vo.fav_board_writedate);
                binding.tvView.setText("조회수 : "+vo.fav_board_writecount);
                binding.tvContent.setText(vo.fav_board_content);
                CommonConn conn2 = new CommonConn(getContext(), "board/rec");
                conn2.addParamMap("id", fav_board_id);
                conn2.onExcute((isResult1, data1) -> {
                    binding.tvRec.setText("추천 : "+data1);
                });
                CommonConn conn3 = new CommonConn(getContext(), "board/commentCnt");
                conn3.addParamMap("fav_board_id", fav_board_id);
                conn3.onExcute((isResult1, data1) -> {
                    binding.tvComment.setText("댓글 ("+data1+")");
                });
                CommonConn conn4 = new CommonConn(getContext(), "board/checkRec");
                conn4.addParamMap("fav_board_id", fav_board_id);
                conn4.addParamMap("member_id_like", CommonVar.logininfo.getMember_id());
                conn4.onExcute((isResult1, data1) -> {
                    if(data1.equals("없음")){
                        binding.imgvRec.setImageResource(R.drawable.thumb);
                        like = false;
                    }else {
                        binding.imgvRec.setImageResource(R.drawable.thumb_select);
                        like = true;
                    }
                });
            }else {
                Toast.makeText(activity, "오류로 게시글 불러오기를 실패하였습니다", Toast.LENGTH_SHORT).show();
            }
        });

        binding.imgvRec.setOnClickListener(v -> {
            if(!like){
                CommonConn conn1 = new CommonConn(getContext(), "board/insertRec");
                conn1.addParamMap("member_id_like", CommonVar.logininfo.getMember_id());
                conn1.addParamMap("fav_board_id", fav_board_id);
                conn1.onExcute((isResult, data) -> {
                    if(data.equals("성공")){
                        Toast.makeText(activity, "추천", Toast.LENGTH_SHORT).show();
                        binding.imgvRec.setImageResource(R.drawable.thumb_select);
                        like = true;
                        CommonConn conn2 = new CommonConn(getContext(), "board/rec");
                        conn2.addParamMap("id", fav_board_id);
                        conn2.onExcute((isResult1, data1) -> {
                            binding.tvRec.setText("추천 : "+data1);
                        });
                    }else {
                        Toast.makeText(activity, "추천 실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                CommonConn conn1 = new CommonConn(getContext(), "board/deleteRec");
                conn1.addParamMap("member_id_like", CommonVar.logininfo.getMember_id());
                conn1.addParamMap("fav_board_id", fav_board_id);
                conn1.onExcute((isResult, data) -> {
                    if(data.equals("성공")){
                        Toast.makeText(activity, "추천 취소", Toast.LENGTH_SHORT).show();
                        binding.imgvRec.setImageResource(R.drawable.thumb);
                        like = false;
                        CommonConn conn2 = new CommonConn(getContext(), "board/rec");
                        conn2.addParamMap("id", fav_board_id);
                        conn2.onExcute((isResult1, data1) -> {
                            binding.tvRec.setText("추천 : "+data1);
                        });
                    }else {
                        Toast.makeText(activity, "추천 취소 실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });





        return binding.getRoot();
    }
}