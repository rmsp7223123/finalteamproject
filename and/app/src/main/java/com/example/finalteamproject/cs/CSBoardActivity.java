package com.example.finalteamproject.cs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.finalteamproject.board.BoardCommonVar;
import com.example.finalteamproject.board.BoardListAdapter;
import com.example.finalteamproject.board.FavorBoardVO;
import com.example.finalteamproject.board.NewBoardFragment;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.databinding.ActivityCsboardBinding;
import com.example.finalteamproject.main.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class CSBoardActivity extends AppCompatActivity {

    ActivityCsboardBinding binding;

    String[] list2 = {"최신순", "조회순", "오래된순"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onResume() {
        super.onResume();

        binding = ActivityCsboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //게시판 글 정렬 스피너
        binding.spnBoard2.getSelectedItem();
        Spinner favorSpinner2 = binding.spnBoard2;
        ArrayAdapter favorAdapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, list2);
        favorSpinner2.setAdapter(favorAdapter2);

        for(int i=0;i<list2.length;i++){
            if(list2[i].equals(BoardCommonVar.cs_board_align)){
                binding.spnBoard2.setSelection(i);
            }
        }

        binding.spnBoard2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                BoardCommonVar.cs_board_align = list2[i];
                CommonConn conn = new CommonConn(CSBoardActivity.this, "csboard/list");
                conn.addParamMap("context", binding.edtSearch.getText().toString());
                conn.addParamMap("align", BoardCommonVar.cs_board_align);
                conn.onExcute((isResult, data) -> {
                    List<CSBoardVO> list = new Gson().fromJson(data, new TypeToken<List<CSBoardVO>>(){}.getType());
                    if(list.size()!=0){
                        binding.recvBoard.setVisibility(View.VISIBLE);
                        binding.tvNone.setVisibility(View.GONE);
                        CSBoardListAdapter adapter = new CSBoardListAdapter(list, CSBoardActivity.this);
                        binding.recvBoard.setAdapter(adapter);
                        binding.recvBoard.setLayoutManager(new LinearLayoutManager(CSBoardActivity.this));
                    }else {
                        binding.recvBoard.setVisibility(View.GONE);
                        binding.tvNone.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.imgvSearch.setOnClickListener(v -> {
            CommonConn conn = new CommonConn(CSBoardActivity.this, "csboard/list");
            conn.addParamMap("context", binding.edtSearch.getText().toString());
            conn.addParamMap("align", BoardCommonVar.cs_board_align);
            conn.onExcute((isResult, data) -> {
                List<CSBoardVO> list = new Gson().fromJson(data, new TypeToken<List<CSBoardVO>>(){}.getType());
                if(list.size()!=0){
                    binding.recvBoard.setVisibility(View.VISIBLE);
                    binding.tvNone.setVisibility(View.GONE);
                    CSBoardListAdapter adapter = new CSBoardListAdapter(list, CSBoardActivity.this);
                    binding.recvBoard.setAdapter(adapter);
                    binding.recvBoard.setLayoutManager(new LinearLayoutManager(CSBoardActivity.this));
                }else {
                    binding.recvBoard.setVisibility(View.GONE);
                    binding.tvNone.setVisibility(View.VISIBLE);
                }
            });
        });

//        binding.cvNew.setOnClickListener(v -> {
//            Log.d("result", "onCreateView: "+binding.tvBoardTitle.getText().toString());
//            activity.replaceFragment(this, new NewBoardFragment(activity, binding.tvBoardTitle.getText().toString(), BoardCommonVar.board_align));
//        });


        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });

        binding.imgRefresh.setOnClickListener(v -> {
            finish();//인텐트 종료
            overridePendingTransition(0, 0);//인텐트 효과 없애기
            Intent intent = getIntent(); //인텐트
            startActivity(intent); //액티비티 열기
            overridePendingTransition(0, 0);//인텐트 효과 없애기
        });

        binding.cvNew.setOnClickListener(v -> {
            Intent intent = new Intent(this, NewCSBoardActivity.class);
            startActivity(intent);
        });

    }
}