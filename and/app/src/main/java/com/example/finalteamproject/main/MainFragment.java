package com.example.finalteamproject.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.common.MemberVO;
import com.example.finalteamproject.databinding.FragmentMainBinding;
import com.example.finalteamproject.setting.ChangeProfileActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    FragmentMainBinding binding;

    public static ArrayList<MemberVO> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        CommonConn conn = new CommonConn(getContext(), "main/viewpager");
        conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
        conn.onExcute((isResult, data) -> {
            list = new Gson().fromJson(data, new TypeToken<ArrayList<MemberVO>>() {
            }.getType());

            favorChange(0);

            ArrayList<MemberVO> virtualList = new ArrayList<>();
            for (MemberVO member : list) {
                virtualList.add(member);
            }

            Viewpager_main_adapter adapter = new Viewpager_main_adapter(getContext(), virtualList);
            binding.imgViewpager.setAdapter(adapter);


            int initialPosition = list.size() * 1000;
            binding.imgViewpager.setCurrentItem(0, false);
            binding.tvNickname.setText(list.get(0).getMember_nickname());
            binding.imgViewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                int currentState = 0;
                int currentPos = initialPosition;

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (currentState == ViewPager2.SCROLL_STATE_DRAGGING && currentPos == position) {
                        if (currentPos == 0) {
                            binding.imgViewpager.setCurrentItem(list.size() * 2 - 2, false);
                        } else if (currentPos == list.size() * 2 - 1) {
                            binding.imgViewpager.setCurrentItem(1, false);
                        }
                    }
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }

                @Override
                public void onPageSelected(int position) {
                    favorChange(position);
                    currentPos = position;
                    int realPosition = position % list.size();
                    binding.tvNickname.setText(list.get(realPosition).getMember_nickname());
                    super.onPageSelected(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    currentState = state;
                    super.onPageScrollStateChanged(state);
                }
            });


        });
        MainBoardAdapter adapter1 = new MainBoardAdapter();
        binding.recvBoard.setAdapter(adapter1);
        binding.recvBoard.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.imgvAlarmHistory.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MainAlarmHistoryActivity.class);
            startActivity(intent);
        });

        binding.imgvSmallProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ChangeProfileActivity.class);
            startActivity(intent);
        });

        binding.imgvRight.setOnClickListener(v -> {
            binding.imgViewpager.setCurrentItem(binding.imgViewpager.getCurrentItem() + 1);
        });

        binding.imgvLeft.setOnClickListener(v -> {
            binding.imgViewpager.setCurrentItem(binding.imgViewpager.getCurrentItem() - 1);
        });

        binding.imgvAdd.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("친구추가 보내기");
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

        BoardMainAdapter adapter2 = new BoardMainAdapter(this, getList(), getActivity(), null);
        binding.recvBoard.setAdapter(adapter2);
        binding.recvBoard.setLayoutManager(new LinearLayoutManager(this.getContext()));

        binding.imgvMenu.setOnClickListener(v -> {
            binding.lnBoard.setVisibility(View.VISIBLE);
        });

        binding.imgvClose.setOnClickListener(v -> {
            binding.lnBoard.setVisibility(View.INVISIBLE);
        });


        binding.imgvMessage.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setTitle("메시지 보내기");
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

//        CommonConn conn = new CommonConn(getContext(),"main/test");
//        conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());


        Glide.with(this).load(CommonVar.logininfo.getMember_profileimg()).into(binding.imgvSmallProfile);


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

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(this).load(CommonVar.logininfo.getMember_profileimg()).into(binding.imgvSmallProfile);
    }

    public void images() {

    }

    public void favorChange(int position) {
        returnFavorColor();
        CommonConn conn1 = new CommonConn(getContext(), "main/favor");
        conn1.addParamMap("member_id", list.get(position).getMember_id());
        conn1.onExcute((isResult1, data1) -> {
            ArrayList<FavorVO> list1 = new Gson().fromJson(data1, new TypeToken<ArrayList<FavorVO>>() {
            }.getType());
            for (int i = 0; i < list1.size(); i++) {
                FavorVO favorVO = list1.get(i);
                int favor = favorVO.favor;
                if (favor == 1) {
                    binding.imgvTv.setImageResource(R.drawable.tv_select);
                } else if (favor == 2) {
                    binding.imgvMusic.setImageResource(R.drawable.music_select);
                } else if (favor == 3) {
                    binding.imgvMovie.setImageResource(R.drawable.movie_select);
                } else if (favor == 4) {
                    binding.imgvFashion.setImageResource(R.drawable.fashion_select);
                } else if (favor == 5) {
                    binding.imgvAnimal.setImageResource(R.drawable.animal_select);
                } else if (favor == 6) {
                    binding.imgvNews.setImageResource(R.drawable.news_select);
                } else if (favor == 7) {
                    binding.imgvCar.setImageResource(R.drawable.car_select);
                } else if (favor == 8) {
                    binding.imgvSports.setImageResource(R.drawable.sports_select);
                } else if (favor == 9) {
                    binding.imgvGame.setImageResource(R.drawable.game_select);
                }
            }
        });
    }

    public void returnFavorColor() {
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
}