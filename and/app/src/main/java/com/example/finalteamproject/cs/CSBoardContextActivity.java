package com.example.finalteamproject.cs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.finalteamproject.R;
import com.example.finalteamproject.board.BoardContextFragment;
import com.example.finalteamproject.board.FavorBoardVO;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.ActivityCsboardContextBinding;
import com.google.gson.Gson;

public class CSBoardContextActivity extends AppCompatActivity {

    ActivityCsboardContextBinding binding;
    int csboard_id;

    CSBoardVO vo;
    CSBoardCommentVO comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();


        binding = ActivityCsboardContextBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        csboard_id = getIntent().getIntExtra("csboard_id", 0);

        CommonConn conn4 = new CommonConn(this, "csboard/viewCnt");
        conn4.addParamMap("id", csboard_id);
        conn4.onExcute((isResult, data) -> {
        });


        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });

        CommonConn conn = new CommonConn(this, "csboard/info");
        conn.addParamMap("id", csboard_id);
        conn.onExcute((isResult, data) -> {
            vo = new Gson().fromJson(data, CSBoardVO.class);
            if(vo!=null){
                if(vo.csboard_secret.equals("Y")){
                    binding.imgvSecret.setVisibility(View.VISIBLE);
                }
                binding.tvTitle.setText(vo.csboard_title);
                binding.tvDate.setText(vo.csboard_writedate);
                CommonConn conn1 = new CommonConn(this, "board/nickname");
                conn1.addParamMap("id", vo.writer);
                conn1.onExcute((isResult1, data1) -> {
                    binding.tvWriter.setText("작성자 : "+data1);
                });
                binding.tvView.setText("조회 : "+vo.csboard_viewcount);
                binding.tvContent.setText(vo.csboard_content);
                if(vo.writer.equals(CommonVar.logininfo.getMember_id())){
                    binding.tvModify.setVisibility(View.VISIBLE);
                    binding.tvDelete.setVisibility(View.VISIBLE);
                }

                CommonConn conn2 = new CommonConn(this, "csboard/comment");
                conn2.addParamMap("id", vo.csboard_id);
                conn2.onExcute((isResult1, data1) -> {
                    comment = new Gson().fromJson(data1, CSBoardCommentVO.class);
                    if(comment!=null){
                        binding.cvNew.setVisibility(View.GONE);
                        binding.lnComment.setVisibility(View.VISIBLE);
                        CommonConn conn3 = new CommonConn(this, "board/nickname");
                        conn3.addParamMap("id", comment.writer);
                        conn3.onExcute((isResult2, data2) -> {
                            binding.tvCommentWriter.setText(data2);
                        });
                        binding.tvCommentDate.setText(comment.csboard_comment_writedate);
                        binding.tvCommentContent.setText(comment.csboard_comment_content);
                        if(CommonVar.logininfo.getMember_id().equals(comment.writer)){
                            binding.tvCommentModify.setVisibility(View.VISIBLE);
                            binding.tvCommentDelete.setVisibility(View.VISIBLE);
                        }

                    }else {
                        binding.tvNone.setVisibility(View.VISIBLE);
                        binding.lnComment.setVisibility(View.GONE);
                        if(comment==null&&CommonVar.logininfo.getMember_admin()==1){
                            binding.cvNew.setVisibility(View.VISIBLE);
                        }else {
                            binding.cvNew.setVisibility(View.GONE);
                        }
                    }
                });
            }else {
                Toast.makeText(this, "게시글 불러오기를 실패하였습니다", Toast.LENGTH_SHORT).show();
            }
        });

        binding.tvModify.setOnClickListener(v -> {
            Intent intent = new Intent(this, ModifyCSBoardActivity.class);
            intent.putExtra("vo", vo);
            startActivity(intent);
        });

        binding.tvDelete.setOnClickListener(v -> {


            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
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
                    CommonConn conn1 = new CommonConn(CSBoardContextActivity.this, "csboard/delete");
                    conn1.addParamMap("id", vo.csboard_id);
                    conn1.onExcute((isResult, data) -> {
                        if(data.equals("성공")){
                            Toast.makeText(CSBoardContextActivity.this, "게시글 삭제 성공", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(CSBoardContextActivity.this, "게시글 삭제 실패", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            android.app.AlertDialog dialog = builder.create();
            dialog.show();

        });


        binding.tvCommentDelete.setOnClickListener(v -> {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
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
                    CommonConn conn1 = new CommonConn(CSBoardContextActivity.this, "csboard/deleteComment");
                    conn1.addParamMap("id", comment.csboard_comment_id);
                    conn1.onExcute((isResult, data) -> {
                        if(data.equals("성공")){
                            Toast.makeText(CSBoardContextActivity.this, "댓글 삭제 성공", Toast.LENGTH_SHORT).show();
                            finish();//인텐트 종료
                            overridePendingTransition(0, 0);//인텐트 효과 없애기
                            Intent intent = getIntent(); //인텐트
                            startActivity(intent); //액티비티 열기
                            overridePendingTransition(0, 0);//인텐트 효과 없애기
                        }else {
                            Toast.makeText(CSBoardContextActivity.this, "댓글 삭제 실패", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            android.app.AlertDialog dialog = builder.create();
            dialog.show();
        });

        InputMethodManager manager = (InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);

        binding.cvNew.setOnClickListener(v -> {
            if(binding.lnNewComment.getVisibility()==View.VISIBLE){
                if(binding.edtNewCommentContent.getText().toString().length()<1){
                    Toast.makeText(this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                    binding.edtNewCommentContent.requestFocus();
                    manager.showSoftInput(binding.edtNewCommentContent, InputMethodManager.SHOW_IMPLICIT);
                }else {
                    CommonConn conn1 = new CommonConn(this, "csboard/insertComment");
                    conn1.addParamMap("csboard_id", csboard_id);
                    conn1.addParamMap("writer", CommonVar.logininfo.getMember_id());
                    conn1.addParamMap("csboard_comment_content", binding.edtNewCommentContent.getText().toString());
                    conn1.onExcute((isResult, data) -> {
                        if(data.equals("성공")){
                            Toast.makeText(this, "답변 등록 성공", Toast.LENGTH_SHORT).show();
                            finish();//인텐트 종료
                            overridePendingTransition(0, 0);//인텐트 효과 없애기
                            Intent intent = getIntent(); //인텐트
                            startActivity(intent); //액티비티 열기
                            overridePendingTransition(0, 0);//인텐트 효과 없애기
                        }else {
                            Toast.makeText(this, "답변 등록 실패", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }else {
                binding.edtNewCommentContent.setText("");
                binding.tvNone.setVisibility(View.GONE);
                binding.lnNewComment.setVisibility(View.VISIBLE);
                binding.tvNewCommentWriter.setText(CommonVar.logininfo.getMember_nickname());
                binding.edtNewCommentContent.requestFocus();
                manager.showSoftInput(binding.edtNewCommentContent, InputMethodManager.SHOW_IMPLICIT);

            }
        });

        binding.tvCommentModify.setOnClickListener(v -> {
            binding.tvCommentWriterModify.setText(CommonVar.logininfo.getMember_nickname());
            binding.lnComment.setVisibility(View.GONE);
            binding.lnModifyComment.setVisibility(View.VISIBLE);
            binding.edtCommentContentModify.setText(comment.csboard_comment_content);
        });

        binding.tvModifyModify.setOnClickListener(v -> {
            if(binding.edtCommentContentModify.getText().toString().length()<1){
                Toast.makeText(this, "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                binding.edtCommentContentModify.requestFocus();
                manager.showSoftInput(binding.edtCommentContentModify, InputMethodManager.SHOW_IMPLICIT);
            }else {
                CommonConn conn1 = new CommonConn(this, "csboard/modifyComment");
                conn1.addParamMap("csboard_comment_id", comment.csboard_comment_id);
                conn1.addParamMap("csboard_comment_content", binding.edtCommentContentModify.getText().toString());
                conn1.onExcute((isResult, data) -> {
                    if(data.equals("성공")){
                        Toast.makeText(this, "댓글 수정 성공", Toast.LENGTH_SHORT).show();
                        finish();//인텐트 종료
                        overridePendingTransition(0, 0);//인텐트 효과 없애기
                        Intent intent = getIntent(); //인텐트
                        startActivity(intent); //액티비티 열기
                        overridePendingTransition(0, 0);//인텐트 효과 없애기

                    }else {
                        Toast.makeText(this, "댓글 수정 실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        binding.tvModifyDelete.setOnClickListener(v -> {

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
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
                    CommonConn conn1 = new CommonConn(CSBoardContextActivity.this, "csboard/deleteComment");
                    conn1.addParamMap("id", comment.csboard_comment_id);
                    conn1.onExcute((isResult, data) -> {
                        if(data.equals("성공")){
                            Toast.makeText(CSBoardContextActivity.this, "댓글 삭제 성공", Toast.LENGTH_SHORT).show();
                            finish();//인텐트 종료
                            overridePendingTransition(0, 0);//인텐트 효과 없애기
                            Intent intent = getIntent(); //인텐트
                            startActivity(intent); //액티비티 열기
                            overridePendingTransition(0, 0);//인텐트 효과 없애기
                        }else {
                            Toast.makeText(CSBoardContextActivity.this, "댓글 삭제 실패", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            android.app.AlertDialog dialog = builder.create();
            dialog.show();

        });

        binding.tvBack.setOnClickListener(v -> {
            binding.lnNewComment.setVisibility(View.GONE);
        });

    }
}