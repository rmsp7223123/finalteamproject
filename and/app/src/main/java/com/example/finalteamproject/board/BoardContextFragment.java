package com.example.finalteamproject.board;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalteamproject.Login.LoginActivity;
import com.example.finalteamproject.R;
import com.example.finalteamproject.chat.ChatPhotoDetailActivity;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.DialogConfirmBinding;
import com.example.finalteamproject.databinding.FragmentBoardContextBinding;
import com.example.finalteamproject.main.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;


public class BoardContextFragment extends Fragment {

    MainActivity activity;
    FragmentBoardContextBinding binding;
    String[] list = {"최신순", "오래된순"};
    boolean like;
    String nickname;
    FavorBoardVO vo;

    public BoardContextFragment(Activity activity,  FavorBoardVO vo) {
        this.activity = (MainActivity) activity;
        this.vo = vo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBoardContextBinding.inflate(inflater, container, false);

        binding.imgvBack.setOnClickListener(v -> {
                activity.changeFragment(this, activity);
        });

        CommonConn conn5 = new CommonConn(getContext(), "board/viewCnt");
        conn5.addParamMap("id", vo.fav_board_id);
        conn5.onExcute((isResult, data) -> {
        });

//        CommonConn conn = new CommonConn(this.getContext(), "board/select");
//        conn.addParamMap("fav_board_id", fav_board_id);
//        conn.onExcute((isResult, data) -> {
//            FavorBoardVO vo = new Gson().fromJson(data, FavorBoardVO.class);
//            if(vo!=null){
                if(CommonVar.logininfo.getMember_id().equals(vo.writer)){
                    binding.tvModify.setVisibility(View.VISIBLE);
                    binding.tvDelete.setVisibility(View.VISIBLE);
                }else {
                    binding.tvModify.setVisibility(View.GONE);
                    binding.tvDelete.setVisibility(View.GONE);
                }
                CommonConn conn1 = new CommonConn(getContext(), "board/nickname");
                conn1.addParamMap("id", vo.writer);
                conn1.onExcute((isResult1, data1) -> {
                    binding.tvWriter.setText("작성자 : "+data1);
                    nickname = data1;
                });
                binding.tvBoardName.setText(BoardCommonVar.board_name);
                binding.tvTitle.setText(vo.fav_board_title);
                binding.tvDate.setText(vo.fav_board_writedate);
                binding.tvView.setText("조회수 : "+vo.fav_board_writecount);
                binding.tvContent.setText(vo.fav_board_content);
                if(vo.fav_board_img!=null){
                    Glide.with(this).load(vo.fav_board_img).into(binding.imgvImg);
                }else {
                    binding.imgvImg.setVisibility(View.GONE);
                }
                CommonConn conn2 = new CommonConn(getContext(), "board/rec");
                conn2.addParamMap("id", vo.fav_board_id);
                conn2.onExcute((isResult1, data1) -> {
                    binding.tvRec.setText("추천 : "+data1);
                });
                CommonConn conn3 = new CommonConn(getContext(), "board/commentCnt");
                conn3.addParamMap("fav_board_id", vo.fav_board_id);
                conn3.onExcute((isResult1, data1) -> {
                    binding.tvComment.setText("댓글 ("+data1+")");
                });
                CommonConn conn4 = new CommonConn(getContext(), "board/checkRec");
                conn4.addParamMap("fav_board_id", vo.fav_board_id);
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

                binding.imgvImg.setOnClickListener(v -> {
                    activity.replaceFragment(this, new BoardImageFragment(activity, vo));
                });

//            }else {
//                Toast.makeText(activity, "게시글 불러오기를 실패하였습니다", Toast.LENGTH_SHORT).show();
//            }
//        });

        binding.imgvRec.setOnClickListener(v -> {
            if(!like){
                if(nickname.equals(CommonVar.logininfo.getMember_nickname())){
                    Toast.makeText(activity, "본인의 글에는 추천할 수 없습니다", Toast.LENGTH_SHORT).show();
                }else {
                    CommonConn conn = new CommonConn(getContext(), "board/insertRec");
                    conn.addParamMap("member_id_like", CommonVar.logininfo.getMember_id());
                    conn.addParamMap("fav_board_id", vo.fav_board_id);
                    conn.onExcute((isResult, data) -> {
                        if(data.equals("성공")){
                            Toast.makeText(activity, "추천", Toast.LENGTH_SHORT).show();
                            binding.imgvRec.setImageResource(R.drawable.thumb_select);
                            like = true;
                            CommonConn conn6 = new CommonConn(getContext(), "board/rec");
                            conn6.addParamMap("id", vo.fav_board_id);
                            conn6.onExcute((isResult1, data1) -> {
                                binding.tvRec.setText("추천 : "+data1);
                            });
                        }else {
                            Toast.makeText(activity, "추천 실패", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }else {
                CommonConn conn = new CommonConn(getContext(), "board/deleteRec");
                conn.addParamMap("member_id_like", CommonVar.logininfo.getMember_id());
                conn.addParamMap("fav_board_id", vo.fav_board_id);
                conn.onExcute((isResult, data) -> {
                    if(data.equals("성공")){
                        Toast.makeText(activity, "추천 취소", Toast.LENGTH_SHORT).show();
                        binding.imgvRec.setImageResource(R.drawable.thumb);
                        like = false;
                        CommonConn conn6 = new CommonConn(getContext(), "board/rec");
                        conn6.addParamMap("id", vo.fav_board_id);
                        conn6.onExcute((isResult1, data1) -> {
                            binding.tvRec.setText("추천 : "+data1);
                        });
                    }else {
                        Toast.makeText(activity, "추천 취소 실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        binding.tvModify.setOnClickListener(v -> {
            activity.replaceFragment(this, new ModifyBoardFragment(activity, vo));
        });

        binding.tvDelete.setOnClickListener(v -> {
            showDialog();
        });

        binding.spnBoard.getSelectedItem();
        Spinner commentSpinner = binding.spnBoard;
        ArrayAdapter commentAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_spinner_item, list);
        commentSpinner.setAdapter(commentAdapter);

        for(int i=0;i<list.length;i++){
            if(list[i].equals(BoardCommonVar.board_comment_align)){
                binding.spnBoard.setSelection(i);
            }
        }

        binding.spnBoard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                BoardCommonVar.board_comment_align = list[i];
                CommonConn conn1 = new CommonConn(getContext(), "board/commentList");
                conn1.addParamMap("fav_board_id", vo.fav_board_id);
                conn1.addParamMap("align", BoardCommonVar.board_comment_align);
                conn1.onExcute((isResult, data) -> {
                    List<FavorBoardCommentVO> list = new Gson().fromJson(data, new TypeToken<List<FavorBoardCommentVO>>(){}.getType());
                    if(list.size()!=0){
                        binding.recvComment.setVisibility(View.VISIBLE);
                        binding.tvNone.setVisibility(View.GONE);
                        BoardCommentAdapter adapter = new BoardCommentAdapter(list, getContext(), activity, BoardContextFragment.this, vo);
                        binding.recvComment.setAdapter(adapter);
                        binding.recvComment.setLayoutManager(new LinearLayoutManager(BoardContextFragment.this.getContext()));
                    }else {
                        binding.recvComment.setVisibility(View.GONE);
                        binding.tvNone.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        InputMethodManager manager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        binding.cvNew.setOnClickListener(v -> {
            if(binding.lnNewComment.getVisibility()==View.GONE){
                binding.lnNewComment.setVisibility(View.VISIBLE);
                binding.tvCommentWriter.setText(CommonVar.logininfo.getMember_nickname());
                binding.edtCommentContent.setText("");
            }else {
                if(binding.edtCommentContent.getText().toString().length()<1){
                    Toast.makeText(activity, "내용을 입력하세요", Toast.LENGTH_SHORT).show();
                    binding.edtCommentContent.requestFocus();
                    manager.showSoftInput(binding.edtCommentContent, InputMethodManager.SHOW_IMPLICIT);
                }else {
                    CommonConn conn = new CommonConn(getContext(), "board/insertComment");
                    conn.addParamMap("writer", CommonVar.logininfo.getMember_id());
                    conn.addParamMap("fav_board_id", vo.fav_board_id);
                    conn.addParamMap("fav_board_comment_content", binding.edtCommentContent.getText().toString());
                    conn.onExcute((isResult, data) -> {
                        if(data.equals("성공")){
                            binding.tvNone.setVisibility(View.GONE);
                            binding.recvComment.setVisibility(View.VISIBLE);
                            Toast.makeText(activity, "댓글 등록 성공", Toast.LENGTH_SHORT).show();
                            CommonConn conn6 = new CommonConn(getContext(), "board/commentList");
                            conn6.addParamMap("fav_board_id", vo.fav_board_id);
                            conn6.addParamMap("align", BoardCommonVar.board_comment_align);
                            conn6.onExcute((isResult1, data1) -> {
                                List<FavorBoardCommentVO> list = new Gson().fromJson(data1, new TypeToken<List<FavorBoardCommentVO>>(){}.getType());
                                if(list.size()!=0){
                                    binding.recvComment.setVisibility(View.VISIBLE);
                                    binding.tvNone.setVisibility(View.GONE);
                                    BoardCommentAdapter adapter = new BoardCommentAdapter(list, getContext(), activity, BoardContextFragment.this, vo);
                                    binding.recvComment.setAdapter(adapter);
                                    binding.recvComment.setLayoutManager(new LinearLayoutManager(BoardContextFragment.this.getContext()));
                                    binding.tvComment.setText("댓글 ("+list.size()+")");
                                }else {
                                    binding.recvComment.setVisibility(View.GONE);
                                    binding.tvNone.setVisibility(View.VISIBLE);
                                }
                            });
                        }else{
                            Toast.makeText(activity, "댓글 등록 실패", Toast.LENGTH_SHORT).show();
                        }
                    });
                    binding.lnNewComment.setVisibility(View.GONE);
                }

            }
        });

        binding.tvBack.setOnClickListener(v -> {
            binding.lnNewComment.setVisibility(View.GONE);
        });


        return binding.getRoot();
    }



    public void showDialog(){
//        String[] dialog_item = {"삭제", "취소"};
//        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
//        builder.setTitle("게시글을 삭제하시겠습니까?");
//        builder.setSingleChoiceItems(dialog_item, -1, (dialog, i) -> {
//            if(dialog_item[i].equals("삭제")){
//                CommonConn conn = new CommonConn(this.getContext(), "board/deleteBoard");
//                conn.addParamMap("id", fav_board_id);
//                conn.onExcute((isResult, data) -> {
//                    if(data.equals("성공")){
//                        Toast.makeText(activity, "게시물이 삭제되었습니다", Toast.LENGTH_SHORT).show();
//                        activity.changeFragment(this, activity);
//                    }else {
//                        Toast.makeText(activity, "게시물 삭제 실패 /n다시 시도해주세요", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }else if(dialog_item[i].equals("취소")){
//                dialog.dismiss();
//            }
//            dialog.dismiss();
//        });
//        AlertDialog dialog = builder.create();
//        dialog.show();


        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(requireContext());
        builder.setTitle("게시글을 삭제하시겠습니까?");
        builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CommonConn conn = new CommonConn(BoardContextFragment.this.getContext(), "board/deleteBoard");
                conn.addParamMap("id", vo.fav_board_id);
                conn.onExcute((isResult, data) -> {
                    if(data.equals("성공")){
                        Toast.makeText(activity, "게시물이 삭제되었습니다", Toast.LENGTH_SHORT).show();
                        activity.changeFragment(BoardContextFragment.this, activity);
                    }else {
                        Toast.makeText(activity, "게시물 삭제 실패 /n다시 시도해주세요", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        android.app.AlertDialog dialog = builder.create();
        dialog.show();
        // dialog로 로그아웃 할 것인지 확인 시키고 확인 했을 때 로그아웃 후 로그인 화면으로 전환

    }


}