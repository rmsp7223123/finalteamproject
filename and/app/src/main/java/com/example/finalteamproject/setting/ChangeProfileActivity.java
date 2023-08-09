package com.example.finalteamproject.setting;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalteamproject.HideActionBar;
import com.example.finalteamproject.Login.LoginVar;
import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.common.MemberVO;
import com.example.finalteamproject.common.RetrofitClient;
import com.example.finalteamproject.common.RetrofitInterface;
import com.example.finalteamproject.databinding.ActivityChangeProfileBinding;
import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeProfileActivity extends AppCompatActivity {

    ActivityChangeProfileBinding binding;

    private final int REQ_GALLERY = 1000;

    ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //new HideActionBar().hideActionBar(this);
        Glide.with(this).load(CommonVar.logininfo.getMember_profileimg()).into(binding.imgvProfileimg);
        binding.tvNickname.setText(CommonVar.logininfo.getMember_nickname());
        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });
        binding.btnChangeProfile.setOnClickListener(v -> {
            showDialog();
        });
        binding.btnChangeNickname.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("닉네임 변경");
            final View customLayout = getLayoutInflater().inflate(R.layout.dialog_change_nickname, null);
            builder.setView(customLayout);
            builder.setPositiveButton("취소", (dialog, which) -> {
            });
            builder.setNegativeButton("확인", (dialog, which) -> {
                EditText edt_nickname = customLayout.findViewById(R.id.edt_nickname);
                // 중복확인 검사 추가하기
                CommonConn conn = new CommonConn(this,"setting/checkNickname");
                conn.addParamMap("member_nickname", edt_nickname.getText().toString());
                conn.onExcute((isResult, data) -> {
                    if(data.equals("null") && edt_nickname.length() > 0) {
                        CommonConn conn2 = new CommonConn(this, "setting/changeNickname");
                        conn2.addParamMap("member_nickname", edt_nickname.getText().toString());
                        conn2.addParamMap("member_id", CommonVar.logininfo.getMember_id());
                        conn2.onExcute((isResult1, data1) -> {
                            binding.tvNickname.setText(edt_nickname.getText().toString());
                            Toast.makeText(this, edt_nickname.getText().toString() + "으로 변경되었습니다.", Toast.LENGTH_SHORT).show();
                            CommonVar.logininfo.setMember_nickname(edt_nickname.getText().toString());
                        });
                    } else {
                        Toast.makeText(this, "이미 존재하는 닉네임이거나 길이가 너무 짧습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Glide.with(this).load(CommonVar.logininfo.getMember_profileimg()).into(binding.imgvProfileimg);
    }


    public void showDialog() {
        String[] dialog_item = {"갤러리", "카메라"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("사진 업로드 방식");
        builder.setSingleChoiceItems(dialog_item, -1, (dialog, i) -> {
            if (dialog_item[i].equals("갤러리")) {
                //갤러리 로직
                showGallery();
            } else if (dialog_item[i].equals("카메라")) {
                //카메라 로직
                showCamera();
            }
            dialog.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, REQ_GALLERY);
    }

    Uri camera_uri = null; // 사진 uri

    public void showCamera() {
        //ContentReseolver(). 앱 --> 컨텐트 리졸버(작업자) ---> 미디어 저장소
        camera_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, camera_uri);
        launcher.launch(cameraIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                // 액티비티(카메라)가 종료 되면 콜백으로 데이터를 받는 부분 기존(onActivityResult메소드)가 실행 되었고 현재는 해당 메소드
                Glide.with(ChangeProfileActivity.this).load(camera_uri).into(binding.imgvProfileimg);
                File file = new File(getRealPath(camera_uri));
                if (file != null) {
                  //  Glide.with(this).load(data.getData()).into(binding.imgvProfileimg);
                   // String img_path = getRealPath(data.getData());
                    RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"),file);
                    MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", CommonVar.logininfo.getMember_id()+".jpg", fileBody);
                    HashMap<String, RequestBody> map = new HashMap<>();
                    map.put("dto", RequestBody.create(MediaType.parse("multipart/form-data"), new Gson().toJson(CommonVar.logininfo)));
                 //   CommonVar.logininfo.setMember_profileimg(img_path);



                    RetrofitInterface api = new RetrofitClient().retrofitLogin().create(RetrofitInterface.class);
                    api.clientSendFile("main/changeProfile", map, filePart).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            CommonVar.logininfo = new Gson().fromJson( response.body() , MemberVO.class);
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }

            }
        });
    }

    public String getRealPath(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};//
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Cursor cursor = getContentResolver().query(contentUri, proj, null, null);
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                res = cursor.getString(column_index);
            }
            cursor.close();//다썼으니까 닫음.
        }
        Log.d("TAG", "getRealPath: 커서" + res);
        return res;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_GALLERY && resultCode == RESULT_OK) {
            Glide.with(this).load(data.getData()).into(binding.imgvProfileimg);
            String img_path = getRealPath(data.getData());



            //MultiPart 형태로 전송 (File)
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"), new File(img_path));
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", CommonVar.logininfo.getMember_id()+".jpg", fileBody);
            HashMap<String, RequestBody> map = new HashMap<>();
            map.put("dto", RequestBody.create(MediaType.parse("multipart/form-data"), new Gson().toJson(CommonVar.logininfo)));
           //  CommonVar.logininfo.setMember_profileimg(img_path);
            RetrofitInterface api = new RetrofitClient().retrofitLogin().create(RetrofitInterface.class);
            api.clientSendFile("main/changeProfile",map, filePart).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    CommonVar.logininfo = new Gson().fromJson( response.body() , MemberVO.class);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }
    }

}