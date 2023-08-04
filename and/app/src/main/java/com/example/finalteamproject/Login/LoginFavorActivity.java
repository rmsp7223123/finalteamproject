package com.example.finalteamproject.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivityLoginFavorBinding;

import java.util.ArrayList;

public class LoginFavorActivity extends AppCompatActivity {

    ActivityLoginFavorBinding binding;

    static boolean tv, music, movie, fashion, animal, news, car, sports, game;

//    static boolean

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginFavorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });

        ArrayList<LoginFavorVO> list = new ArrayList<>();
        list.add(new LoginFavorVO(1, "tv", tv, binding.lnTv, binding.imgvTv, R.drawable.tv, R.drawable.tv_select));
        list.add(new LoginFavorVO(2, "music", music, binding.lnMusic, binding.imgvMusic, R.drawable.music, R.drawable.music_select));
        list.add(new LoginFavorVO(3, "movie", movie, binding.lnMovie, binding.imgvMovie, R.drawable.movie, R.drawable.movie_select));
        list.add(new LoginFavorVO(4, "fashion", fashion, binding.lnFashion, binding.imgvFashion, R.drawable.fashion, R.drawable.fashion_select));
        list.add(new LoginFavorVO(5, "animal", animal, binding.lnAnimal, binding.imgvAnimal, R.drawable.animal, R.drawable.animal_select));
        list.add(new LoginFavorVO(6, "news", news, binding.lnNews, binding.imgvNews, R.drawable.news, R.drawable.news_select));
        list.add(new LoginFavorVO(7, "car", car, binding.lnCar, binding.imgvCar, R.drawable.car, R.drawable.car_select));
        list.add(new LoginFavorVO(8, "sports", sports, binding.lnSports, binding.imgvSports, R.drawable.sports, R.drawable.sports_select));
        list.add(new LoginFavorVO(9, "game", game, binding.lnGame, binding.imgvGame,R.drawable.game, R.drawable.game_select));


        for (int i = 0; i < list.size(); i++) {
            int j = i;
            list.get(j).ln.setOnClickListener(v -> {
                if(list.get(j).bl){
                    list.get(j).imgv.setImageResource(list.get(j).drawable);
                    list.get(j).bl = false;
                }else {
                    list.get(j).imgv.setImageResource(list.get(j).drawableSelect);
                    list.get(j).bl = true;
                }
            });
        }
        
        binding.cvNext.setOnClickListener(v -> {
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).bl){
                    CommonConn conn = new CommonConn(this, "login/favor");
                    for (int j = 0; j < list.size(); j++) {
                        conn.addParamMap(list.get(j).num+"", true);
                    }
                    conn.onExcute((isResult, data) -> {
                        if(data.equals("성공")){
                            Intent intent = new Intent(this, LoginGodokActivity.class);
                        }else {
                            Toast.makeText(this, "사용가능한 아이디입니다", Toast.LENGTH_SHORT).show();
//                            binding.tvIdCheck.setVisibility(View.GONE);
//                            binding.tvIdCheckDone.setVisibility(View.VISIBLE);
                        }
                    });

//                    startActivity(intent);
                    break;
                }
                if(i==list.size()-1){
                    Toast.makeText(this, "관심사를 선택해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}