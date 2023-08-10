package com.example.finalteamproject.board;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.databinding.FragmentModifyBoardBinding;
import com.example.finalteamproject.main.MainActivity;
import com.google.gson.Gson;

public class ModifyBoardFragment extends Fragment {

    FragmentModifyBoardBinding binding;
    MainActivity activity;
    int fav_board_id;

    public ModifyBoardFragment(MainActivity activity, int fav_board_id) {
        this.activity = activity;
        this.fav_board_id = fav_board_id;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentModifyBoardBinding.inflate(inflater, container, false);

        CommonConn conn = new CommonConn(this.getContext(), "board/select");
        conn.addParamMap("fav_board_id", fav_board_id);
        conn.onExcute((isResult, data) -> {
            FavorBoardVO vo = new Gson().fromJson(data, FavorBoardVO.class);
            if(vo!=null){
                binding.edtTitle.setText(vo.fav_board_title);
                binding.edtContent.setText(vo.fav_board_content);
                if(vo.fav_board_img!=null){

                }
            }else {
                Toast.makeText(activity, "게시글 불러오기 실패\n다시 시도해주세요", Toast.LENGTH_SHORT).show();
            }
        });

        InputMethodManager manager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        binding.cvNext.setOnClickListener(v -> {
            if(binding.edtTitle.getText().toString().length()<1){
                Toast.makeText(activity, "제목을 입력하세요", Toast.LENGTH_SHORT).show();
                binding.edtTitle.requestFocus();
                manager.showSoftInput(binding.edtTitle, InputMethodManager.SHOW_IMPLICIT);
            }else if(binding.edtContent.getText().toString().length()<1){
                Toast.makeText(activity, "내용을 입력하세요", Toast.LENGTH_SHORT).show();
                binding.edtContent.requestFocus();
                manager.showSoftInput(binding.edtContent, InputMethodManager.SHOW_IMPLICIT);
            }else {
                CommonConn conn1 = new CommonConn(this.getContext(), "board/modify");
                conn1.addParamMap("fav_board_id", fav_board_id);
                conn1.addParamMap("fav_board_title", binding.edtTitle.getText().toString());
                conn1.addParamMap("fav_board_content", binding.edtContent.getText().toString());
                conn1.onExcute((isResult, data) -> {
                    if(data.equals("성공")){
                        Toast.makeText(activity, "게시글 수정 성공", Toast.LENGTH_SHORT).show();
                        activity.replaceFragment(this, new BoardContextFragment(activity, fav_board_id));
                    }else {
                        Toast.makeText(activity, "게시글 수정 실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        binding.imgvBack.setOnClickListener(v -> {
            activity.replaceFragment(this, new BoardContextFragment(activity, fav_board_id));
        });



        return binding.getRoot();
    }
}