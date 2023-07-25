package com.example.finalteamproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.finalteamproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bottomNavigationView.setSelectedItemId(R.id.fab);
        binding.fltbtnHome.setOnClickListener(v->{
            binding.bottomNavigationView.setSelectedItemId(R.id.fab);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, new MainFragment()).commit();
        });

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId()==R.id.chat){
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new ChatFragment()).commit();
            }else if(item.getItemId()==R.id.gps){
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new GpsFramgent()).commit();
            }else if(item.getItemId()==R.id.game){
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new GameFragment()).commit();
            }else if(item.getItemId()==R.id.setting){
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new SettingFragment()).commit();
            }else {
                return false;
            }
            return true;
        });

    }
}