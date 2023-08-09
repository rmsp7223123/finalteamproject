package com.example.finalteamproject;

import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class HideActionBar {

    // 액션바를 숨기기 위한 메소드 추가
    public void hideActionBar(AppCompatActivity activity) {
        activity.getSupportActionBar().hide();

        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.setStatusBarColor(Color.TRANSPARENT);
        View view = activity.getWindow().getDecorView();
        view.setSystemUiVisibility(view.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
}
