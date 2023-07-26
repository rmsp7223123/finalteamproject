package com.example.finalteamproject.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.example.finalteamproject.HideActionBar;
import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Boolean perm=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new HideActionBar().hideActionBar(this);

        pref = getPreferences(MODE_PRIVATE);
        editor = pref.edit();
        pref.getInt("permission", -1);
        if(pref.getInt("permission", -1) == -1){
            checkPermission();
        }




        binding.cvPhone.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, PhoneActivity.class);
            startActivity(intent);
        });

    }

    private final int REQ_PERMISSION = 1000;
    private final int REQ_PERMISSION_DENY = 1001;
    private void checkPermission(){
//        editor.putInt("permission", 0); //데이터 0이 들어감.
//        editor.apply(); //데이터를 확실히 넣음.
        int permission = pref.getInt("permission", -1);
        permission++;
        editor.putInt("permission", permission);
        editor.apply();

        String[] permissions = {Manifest.permission.SEND_SMS,
                                Manifest.permission.SEND_RESPOND_VIA_MESSAGE,
                                Manifest.permission.CAMERA,
                                Manifest.permission.ACCESS_MEDIA_LOCATION,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        for(int i=0; i<permissions.length; i++){
            //내가 모든 권한이 필요하다면 전체 권한을 하나씩 체크해서 허용 안됨이 있는 경우 다시 요청을 하게 만든다.
            if(ActivityCompat.checkSelfPermission(this, permissions[0])== PackageManager.PERMISSION_DENIED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])){
                    //최초 앱이 설치되고 실행 시 false가 나옴. => 사용자가 거부 후 true 재거부 => false
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("권한 요청").setMessage("앱 사용을 위해서 권한이 반드시 필요합니다.");
                    builder.setPositiveButton("확인(권한 허용)", (dialog, which) -> {
                        //2번 권한 설명 후 다시보여줌.
                        ActivityCompat.requestPermissions(this, permissions, REQ_PERMISSION_DENY);
                    });
                    builder.setNegativeButton("종료(권한 허용 불가)", (dialog, which) -> {
                        if (Build.VERSION.SDK_INT >= 21) {
                            // 액티비티 종료 + 태스크 리스트에서 지우기
                            finishAndRemoveTask();
                        } else {
                            // 액티비티 종료
                            finish();
                        }
                        System.exit(0);

                        perm = false;

                    });

                    builder.create().show(); //<==넣어줘야함
                }else{
                    //1번
                    ActivityCompat.requestPermissions(this, permissions, REQ_PERMISSION);
                }
            }
            break;
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(REQ_PERMISSION==requestCode){
            for (int i = 0; i < grantResults.length; i++) {
                if(grantResults[i]==PackageManager.PERMISSION_DENIED){
                    //거절된 권한이 있음.
                    checkPermission();
                    break;
                }
            }

            Log.d("권한", "onRequestPermissionsResult: 권한 요청 완료");
        }else if(REQ_PERMISSION_DENY==requestCode){
            for (int i = 0; i < grantResults.length; i++) {
                if(grantResults[i]==PackageManager.PERMISSION_DENIED){
                    Log.d("권한", "onRequestPermissionsResult: 다시 권한요청 화면을 띄울 수가 없음. 2회 거절당함");
                    editor.putInt("permission", -2);
                    //3번
                    viewSetting();
//                    checkPermission();
                }
            }
        }
    }
    public void viewSetting(){
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:"+getApplicationContext().getPackageName()));
        startActivity(intent);
    }

}