package com.example.finalteamproject;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.finalteamproject.chat.ChatFragment;
import com.example.finalteamproject.databinding.ActivityMainBinding;
import com.example.finalteamproject.game.GameFragment;
import com.example.finalteamproject.gps.GpsFragment;
import com.example.finalteamproject.setting.SettingFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ActionBar actionBar;
    FragmentManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int[] images = new int[]{
                R.drawable.haerin2,
                R.drawable.minji10,
                R.drawable.minji12,
                R.drawable.danielle11,
                R.drawable.hanni9,
                R.drawable.hyein11
        };

        Viewpager_main_adapter adapter = new Viewpager_main_adapter(this, images);
        binding.imgViewpager.setAdapter(adapter);


        actionBar = getSupportActionBar();
        manager = getSupportFragmentManager();
        binding.bottomNavigationView.setSelectedItemId(R.id.fab);
        binding.fltbtnHome.setOnClickListener(v -> {
            binding.bottomNavigationView.setSelectedItemId(R.id.fab);
            FragmentTransaction transaction = manager.beginTransaction();
            for (Fragment fragment : manager.getFragments()) {
                transaction.remove(fragment);
            }
            transaction.commit();
            binding.bottomNavigationView.setVisibility(View.VISIBLE);
        });
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            if (item.getItemId() == R.id.chat) {
                fragment = new ChatFragment();
            } else if (item.getItemId() == R.id.gps) {
                fragment = new GpsFragment();
            } else if (item.getItemId() == R.id.game) {
                fragment = new GameFragment();
            } else if (item.getItemId() == R.id.setting) {
                fragment = new SettingFragment();
            } else {
                return false;
            }
            manager.beginTransaction().replace(R.id.container_frame, fragment).commit();
            return true;
        });


    }

}