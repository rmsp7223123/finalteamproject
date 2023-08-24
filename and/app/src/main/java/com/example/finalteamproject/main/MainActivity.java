package com.example.finalteamproject.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.finalteamproject.Constants;
import com.example.finalteamproject.HideActionBar;
import com.example.finalteamproject.LocationService;
import com.example.finalteamproject.R;
import com.example.finalteamproject.board.BoardCommonVar;
import com.example.finalteamproject.board.BoardFragment;
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
//    ActionBar actionBar;
    FragmentManager manager;


    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        binding = ActivityMainBinding.inflate(getLayoutInflater());


        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);
        } else {
            startLocationService();
        }






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
//        actionBar = getSupportActionBar();
        manager = getSupportFragmentManager();
//        new HideActionBar().hideActionBar(this);
        binding.bottomNavigationView.setSelectedItemId(R.id.fab);
        binding.fltbtnHome.setOnClickListener(v -> {
            // 홈버튼 눌렀을 때 게시판 메뉴 없애기
            BoardCommonVar.board = false;
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

    //게시판 메뉴 이동
    public void changeFragment(Fragment fragment, Activity activity){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment);
        manager.beginTransaction().replace(R.id.container_frame, new BoardFragment(activity)).commit();
    }

    //일반 프래그먼트 이동
    public void replaceFragment(Fragment fragment1, Fragment fragment2) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(fragment1);
        fragmentTransaction.replace(R.id.container_frame, fragment2).commit();
    }

    //어댑터 -> 액티비티 이동
    public void changeActivity(Context context, Activity activity){
        Intent intent = new Intent(context, activity.getClass());
        startActivity(intent);
    }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationService();
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isLocationServiceRunning() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            for (ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)) {
                if (LocationService.class.getName().equals(service.service.getClassName())) {
                    if (service.foreground) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }
    private void startLocationService() {
        if (!isLocationServiceRunning()) {
            Intent intent = new Intent(getApplicationContext(), LocationService.class);
            intent.setAction(Constants.ACTION_START_LOCATION_SERVICE);
            startService(intent);
//            Toast.makeText(this, "Location service started", Toast.LENGTH_SHORT).show();
        }
    }




//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(getIntent().getBooleanExtra("addFriend",false)){
//            showAddFriendDialogOnMainThread(getIntent().getStringExtra("title") , getIntent().getStringExtra("message"));
//        }
//    }
//
//    private void showAddFriendDialog(String title, String message) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(title);
//        builder.setMessage(message);
//        builder.setPositiveButton("수락", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // 수락 버튼을 클릭한 경우의 처리
//                // ...
//                dialog.dismiss();
//            }
//        });
//        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                // 취소 버튼을 클릭한 경우의 처리
//                // ...
//                dialog.dismiss();
//            }
//        });
//
//        AlertDialog dialog = builder.create();
//        dialog.show();
//    }
//
//    private void showAddFriendDialogOnMainThread(String title, String message) {
//        new Handler(Looper.getMainLooper()).post(new Runnable() {
//            @Override
//            public void run() {
//                showAddFriendDialog(title, message);
//            }
//        });
//    }

}