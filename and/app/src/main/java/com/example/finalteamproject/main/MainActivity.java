package com.example.finalteamproject.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.example.finalteamproject.R;
import com.example.finalteamproject.chat.ChatMainFragment;
import com.example.finalteamproject.databinding.ActivityMainBinding;
import com.example.finalteamproject.game.GameFragment;
import com.example.finalteamproject.gps.GpsFragment;
import com.example.finalteamproject.setting.SettingFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ActionBar actionBar;
    FragmentManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        Log.d("TAG", token);
                    }
                });
        actionBar = getSupportActionBar();
        manager = getSupportFragmentManager();
      //  //new HideActionBar().hideActionBar(this);
        binding.bottomNavigationView.setSelectedItemId(R.id.fab);
        binding.fltbtnHome.setOnClickListener(v -> {
            // 홈버튼 눌렀을 때 네비게이션 뷰 아이템 올라오는처리 삭제하기
            binding.bottomNavigationView.setSelectedItemId(R.id.fab);
            FragmentTransaction transaction = manager.beginTransaction();
            for (Fragment fragment : manager.getFragments()) {
                transaction.remove(fragment);
            }
            transaction.commit();
            binding.bottomNavigationView.setVisibility(View.VISIBLE);
            manager.beginTransaction().replace(R.id.container_frame, new MainFragment()).commit();
        });
        manager.beginTransaction().replace(R.id.container_frame, new MainFragment()).commit();
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            if (item.getItemId() == R.id.chat) {
                fragment = new ChatMainFragment();
            } else if (item.getItemId() == R.id.gps) {
                fragment = new GpsFragment();
            } else if (item.getItemId() == R.id.game) {
                fragment = new GameFragment();
            } else if (item.getItemId() == R.id.setting) {
                fragment = new SettingFragment();
            } else {
                return true;
            }
            manager.beginTransaction().remove(fragment);
            manager.beginTransaction().replace(R.id.container_frame, fragment).commit();
            return true;
        });


    }

}