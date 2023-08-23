package com.example.finalteamproject.game;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private TextView countDownTextView;
    private int count = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        countDownTextView = new TextView(this);
        countDownTextView.setTextColor(Color.BLACK);
        countDownTextView.setTextSize(50);
        countDownTextView.setGravity(Gravity.CENTER);

        setContentView(countDownTextView);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (count > 0) {
                    countDownTextView.setText(String.valueOf(count));
                    count--;
                    handler.postDelayed(this, 1000); // 1초마다 실행
                } else {
                    countDownTextView.setVisibility(View.GONE);
                    setContentView(new GameView(GameActivity.this));
                }
            }
        }, 100);




    }


}