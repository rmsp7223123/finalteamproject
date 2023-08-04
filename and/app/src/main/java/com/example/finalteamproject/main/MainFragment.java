package com.example.finalteamproject.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
        MainBoardAdapter adapter1 = new MainBoardAdapter();
        binding.recvBoard.setAdapter(adapter1);
        binding.recvBoard.setLayoutManager(new LinearLayoutManager(getContext()));
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


        binding.containerLinearTv.setOnClickListener(v -> {
            boardImgColor();
            boardTextColor();
            binding.imgvTv.setImageResource(R.drawable.tv_select);
            binding.tvTv.setTextColor(Color.parseColor("#F5DC6D"));
        });
        binding.containerLinearMusic.setOnClickListener(v -> {
            boardImgColor();
            boardTextColor();
            binding.imgvMusic.setImageResource(R.drawable.music_select);
            binding.tvMusic.setTextColor(Color.parseColor("#F5DC6D"));
        });
        binding.containerLinearMovie.setOnClickListener(v -> {
            boardImgColor();
            boardTextColor();
            binding.imgvMovie.setImageResource(R.drawable.movie_select);
            binding.tvMovie.setTextColor(Color.parseColor("#F5DC6D"));
        });
        binding.containerLinearFashion.setOnClickListener(v -> {
            boardImgColor();
            boardTextColor();
            binding.imgvFashion.setImageResource(R.drawable.fashion_select);
            binding.tvFashion.setTextColor(Color.parseColor("#F5DC6D"));
        });
        binding.containerLinearAnimal.setOnClickListener(v -> {
            boardImgColor();
            boardTextColor();
            binding.imgvAnimal.setImageResource(R.drawable.animal_select);
            binding.tvAnimal.setTextColor(Color.parseColor("#F5DC6D"));
        });
        binding.containerLinearNews.setOnClickListener(v -> {
            boardImgColor();
            boardTextColor();
            binding.imgvNews.setImageResource(R.drawable.news_select);
            binding.tvNews.setTextColor(Color.parseColor("#F5DC6D"));
        });
        binding.containerLinearCar.setOnClickListener(v -> {
            boardImgColor();
            boardTextColor();
            binding.imgvCar.setImageResource(R.drawable.car_select);
            binding.tvCar.setTextColor(Color.parseColor("#F5DC6D"));
        });
        binding.containerLinearSports.setOnClickListener(v -> {
            boardImgColor();
            boardTextColor();
            binding.imgvSports.setImageResource(R.drawable.sports_select);
            binding.tvSports.setTextColor(Color.parseColor("#F5DC6D"));
        });
        binding.containerLinearGame.setOnClickListener(v -> {
            boardImgColor();
            boardTextColor();
            binding.imgvGame.setImageResource(R.drawable.game_select);
            binding.tvGame.setTextColor(Color.parseColor("#F5DC6D"));
        });


        return binding.getRoot();
    }

    private void boardImgColor() {
        binding.imgvTv.setImageResource(R.drawable.tv);
        binding.imgvMusic.setImageResource(R.drawable.music);
        binding.imgvMovie.setImageResource(R.drawable.movie);
        binding.imgvFashion.setImageResource(R.drawable.fashion);
        binding.imgvAnimal.setImageResource(R.drawable.animal);
        binding.imgvNews.setImageResource(R.drawable.news);
        binding.imgvCar.setImageResource(R.drawable.car);
        binding.imgvSports.setImageResource(R.drawable.sports);
        binding.imgvGame.setImageResource(R.drawable.game);
    }

    private  void boardTextColor() {
        binding.tvTv.setTextColor(Color.parseColor("#000000"));
        binding.tvMusic.setTextColor(Color.parseColor("#000000"));
        binding.tvMovie.setTextColor(Color.parseColor("#000000"));
        binding.tvFashion.setTextColor(Color.parseColor("#000000"));
        binding.tvAnimal.setTextColor(Color.parseColor("#000000"));
        binding.tvNews.setTextColor(Color.parseColor("#000000"));
        binding.tvCar.setTextColor(Color.parseColor("#000000"));
        binding.tvSports.setTextColor(Color.parseColor("#000000"));
        binding.tvGame.setTextColor(Color.parseColor("#000000"));
    }
}