package com.example.finalteamproject.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.finalteamproject.Login.LoginFavorVO;
import com.example.finalteamproject.Login.LoginGodokActivity;
import com.example.finalteamproject.Login.LoginVar;
import com.example.finalteamproject.R;
import com.example.finalteamproject.board.FavorBoardVO;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.ActivityChangeFavorBinding;
import com.example.finalteamproject.databinding.ActivityLoginFavorBinding;
import com.example.finalteamproject.main.FavorVO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class ChangeFavorActivity extends AppCompatActivity {

    ActivityChangeFavorBinding binding;
    static boolean tv, music, movie, fashion, animal, news, car, sports, game;
    int num=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChangeFavorBinding.inflate(getLayoutInflater());
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

        CommonConn conn1 = new CommonConn(this, "setting/favor");
        conn1.addParamMap("member_id", CommonVar.logininfo.getMember_id());
        conn1.onExcute((isResult1, data1) -> {
            List<FavorVO> list1 = new Gson().fromJson(data1, new TypeToken<List<FavorVO>>(){}.getType());
            for (int i = 0; i < list1.size(); i++) {
                list.get(list1.get(i).getFavor()-1).getImgv().setImageResource(list.get(list1.get(i).getFavor()-1).getDrawableSelect());
                list.get(list1.get(i).getFavor()-1).setBl(true);
            }

        });



        for (int i = 0; i < list.size(); i++) {
            int j = i;
            list.get(j).getLn().setOnClickListener(v -> {
                if(list.get(j).isBl()){
                    list.get(j).getImgv().setImageResource(list.get(j).getDrawable());
                    list.get(j).setBl(false);
                }else {
                    list.get(j).getImgv().setImageResource(list.get(j).getDrawableSelect());
                    list.get(j).setBl(true);
                }
            });
        }

        binding.cvNext.setOnClickListener(v -> {
            for (int i = 0; i < list.size(); i++) {
                if(list.get(i).isBl()){
                    num++;
                }
            }
            if(num>0){
                CommonConn commonConn = new CommonConn(this, "setting/deleteFavor");
                commonConn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
                commonConn.onExcute((isResult2, data2) -> {
                    if(data2.equals("성공")) {
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).isBl()) {
                                int j = i;
                                CommonConn conn = new CommonConn(this, "login/favor");
                                conn.addParamMap("favor", list.get(i).getNum());
                                conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
                                conn.onExcute((isResult, data) -> {
                                    Log.d("data", "onCreate: " + data);
                                    if (!data.equals("성공")) {
                                        Toast.makeText(this, list.get(j).getName() + "관심사 등록 실패", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                        Toast.makeText(this, "관심사 등록 완료", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }else if(num==0) {
                Toast.makeText(this, "관심사를 선택해주세요", Toast.LENGTH_SHORT).show();
            }




        });

    }
}