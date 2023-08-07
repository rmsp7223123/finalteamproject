package com.example.finalteamproject.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.FragmentMainBinding;
import com.example.finalteamproject.setting.ChangeProfileActivity;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    FragmentMainBinding binding;

    int[] images = new int[]{
            R.drawable.haerin2,
            R.drawable.minji10,
            R.drawable.minji12,
            R.drawable.danielle11,
            R.drawable.hanni9,
            R.drawable.hyein11
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container,false);
        Viewpager_main_adapter adapter = new Viewpager_main_adapter(getContext(), images);
        int initialPosition = adapter.getItemCount() / 2;
        binding.imgViewpager.setAdapter(adapter);
        binding.imgViewpager.setCurrentItem(initialPosition, false);
        binding.imgViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            int currentState = 0;
            int currentPos = 0;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(currentState == ViewPager2.SCROLL_STATE_DRAGGING && currentPos == position) {
                    if(currentPos == 0) {
                        binding.imgViewpager.setCurrentItem(images.length);
                    }
                    else if(currentPos == 2) {
                        binding.imgViewpager.setCurrentItem(0);
                    }
                }
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                currentPos = position;
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                currentState = state;
                super.onPageScrollStateChanged(state);
            }
        });

        binding.imgvAlarmHistory.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MainAlarmHistoryActivity.class);
            startActivity(intent);
        });

        binding.imgvSmallProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ChangeProfileActivity.class);
            startActivity(intent);
        });

        binding.imgvRight.setOnClickListener(v -> {
            binding.imgViewpager.setCurrentItem(binding.imgViewpager.getCurrentItem()+1);
        });

        binding.imgvLeft.setOnClickListener(v -> {
            binding.imgViewpager.setCurrentItem(binding.imgViewpager.getCurrentItem()-1);
        });

        binding.imgvAdd.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("닉네임")
                    .setMessage("친구 추가하기");
            builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        });

        BoardMainAdapter adapter1 = new BoardMainAdapter(getList(),getActivity());
        binding.recvBoard.setAdapter(adapter1);
        binding.recvBoard.setLayoutManager(new LinearLayoutManager(this.getContext()));

        binding.imgvMenu.setOnClickListener(v -> {
            binding.lnBoard.setVisibility(View.VISIBLE);
        });

        binding.imgvClose.setOnClickListener(v -> {
            binding.lnBoard.setVisibility(View.INVISIBLE);
        });


        return binding.getRoot();
    }

    private ArrayList<BoardMainDTO> getList() {
        ArrayList<BoardMainDTO> list = new ArrayList<>();
        list.add(new BoardMainDTO(R.drawable.tv_select, R.drawable.mini_arrow, "tv"));
        list.add(new BoardMainDTO(R.drawable.music_select, R.drawable.mini_arrow, "음악"));
        list.add(new BoardMainDTO(R.drawable.movie_select, R.drawable.mini_arrow, "영화"));
        list.add(new BoardMainDTO(R.drawable.fashion_select, R.drawable.mini_arrow, "패션"));
        list.add(new BoardMainDTO(R.drawable.animal_select, R.drawable.mini_arrow, "동물"));
        list.add(new BoardMainDTO(R.drawable.news_select, R.drawable.mini_arrow, "뉴스"));
        list.add(new BoardMainDTO(R.drawable.car_select, R.drawable.mini_arrow, "자동차"));
        list.add(new BoardMainDTO(R.drawable.sports_select, R.drawable.mini_arrow, "운동"));
        list.add(new BoardMainDTO(R.drawable.game_select, R.drawable.mini_arrow, "게임"));
        return list;
    }
}