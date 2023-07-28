package com.example.finalteamproject.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.example.finalteamproject.HideActionBar;
import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivityLoginDoneBinding;

import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

public class LoginDoneActivity extends AppCompatActivity {

    ActivityLoginDoneBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginDoneBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        new HideActionBar().hideActionBar(this);
        binding.viewKonfetti.bringToFront();

        binding.imgvLogo.setOnClickListener(v->{
            binding.viewKonfetti.build()
                    .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                    .setDirection(0.0, 359.0)
                    .setSpeed(1f, 5f)
                    .setFadeOutEnabled(true)
                    .setTimeToLive(2000L)
                    .addShapes(Shape.RECT, Shape.CIRCLE)
                    .addSizes(new Size(12, 5))
                    .setPosition(-50f, binding.viewKonfetti.getWidth() + 50f, -50f, -50f)
                    .streamFor(300, 5000L);
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.imgvLogo.performClick();
            }
        } , 1000);

    }
}