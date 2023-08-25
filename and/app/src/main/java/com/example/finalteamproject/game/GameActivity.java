package com.example.finalteamproject.game;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.com.example.finalteamproject.common.CustomTextview;

public class GameActivity extends AppCompatActivity {

    private com.example.finalteamproject.common.CustomTextview countDowncom.example.finalteamproject.common.CustomTextview;
    private int count = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        countDowncom.example.finalteamproject.common.CustomTextview = new com.example.finalteamproject.common.CustomTextview(this);
        countDowncom.example.finalteamproject.common.CustomTextview.setTextColor(Color.BLACK);
        countDowncom.example.finalteamproject.common.CustomTextview.setTextSize(50);
        countDowncom.example.finalteamproject.common.CustomTextview.setGravity(Gravity.CENTER);

        setContentView(countDowncom.example.finalteamproject.common.CustomTextview);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (count > 0) {
                    countDowncom.example.finalteamproject.common.CustomTextview.setText(String.valueOf(count));
                    count--;
                    handler.postDelayed(this, 1000); // 1초마다 실행
                } else {
                    countDowncom.example.finalteamproject.common.CustomTextview.setVisibility(View.GONE);
                    setContentView(new GameView(GameActivity.this));
                }
            }
        }, 100);




    }


}