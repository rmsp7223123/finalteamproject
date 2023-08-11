package com.example.finalteamproject.Login;

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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalteamproject.R;
import com.example.finalteamproject.common.RetrofitClient;
import com.example.finalteamproject.common.RetrofitInterface;
import com.example.finalteamproject.databinding.ActivityLoginProfileBinding;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginProfileActivity extends AppCompatActivity {

    ActivityLoginProfileBinding binding;

    private final int REQ_GALLERY = 1000;
    ActivityResultLauncher<Intent> launcher; // <= onCreate에서 초기화하면 오류발생.
    static int num;
    static String img_path = null;
    static Uri camera_uri = null;
    File file = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgvSelect.bringToFront();

        binding.imgvSelect.setOnClickListener(v -> {
            showDialog();
        });

        binding.cvNext.setOnClickListener(v -> {
            if(num==1){
                //갤러리
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"), new File(img_path));
                MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "test.jpg", fileBody);
                RetrofitInterface api = new RetrofitClient().retrofitLogin().create(RetrofitInterface.class);
                HashMap<String, RequestBody> map = new HashMap<>();
                map.put("member_id", RequestBody.create(MediaType.parse("multipart/form-data"), LoginVar.id));
                api.clientSendFile("login/file", map, filePart).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.body().equals("성공")){
                            Toast.makeText(LoginProfileActivity.this, "프로필 이미지 업로드 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginProfileActivity.this, LoginFavorActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(LoginProfileActivity.this, "프로필 이미지 업로드 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(LoginProfileActivity.this, "프로필 이미지 업로드 실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }else if(num==2){
                //카메라
//                            File file = new File(getRealPath(camera_uri));
                //MultiPart 형태로 전송 (File)
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
                MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", ".jpg", fileBody);
                RetrofitInterface api = new RetrofitClient().retrofitLogin().create(RetrofitInterface.class);
                HashMap<String, RequestBody> map = new HashMap<>();
                map.put("member_id", RequestBody.create(MediaType.parse("multipart/form-data"), LoginVar.id));
                api.clientSendFile("login/file", map, filePart).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.body().equals("성공")){
                            Toast.makeText(LoginProfileActivity.this, "프로필 이미지 업로드 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginProfileActivity.this, LoginFavorActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(LoginProfileActivity.this, "프로필 이미지 업로드 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(LoginProfileActivity.this, "프로필 이미지 업로드 실패", Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                Toast.makeText(this, "프로필 사진을 설정해주세요", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == -1){
                    //액티비티(카메라 액티비티)가 종료되면 콜백으로 데이터를 받는 부분. (기존에는 onActivityResult메소드가 실행되었고 현재는 해당 메소드)
                    Glide.with(LoginProfileActivity.this).load(camera_uri).into(binding.imgvProfile);
                    num = 2;
                    file = new File(getRealPath(camera_uri));
                }
            }
        });
    }

    public void showDialog(){
        String[] dialog_item = {"갤러리", "카메라"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("사진 업로드 방식");
        builder.setSingleChoiceItems(dialog_item, -1, (dialog, i) -> {
            if(dialog_item[i].equals("갤러리")){
                //갤러리 로직
                showGallery();
            }else if(dialog_item[i].equals("카메라")){
                //카메라 로직
                showCamera();
            }
            dialog.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, REQ_GALLERY);
    }



    public void showCamera(){
        //ContentResolver(). 앱 ---> 컨텐트리졸버(작업자) ---> 미디어 저장소
//        ContentValues values = new ContentValues();
//        values.describeContents()
        camera_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, camera_uri);
        launcher.launch(cameraIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_GALLERY && resultCode==RESULT_OK){
            //갤러리 액티비티가 종료되었다. (사용자가 사진을 선택했는지?)
            Log.d("갤러리", "onActivityResult: "+data.getData());
            Log.d("갤러리", "onActivityResult: "+data.getData().getPath());
            Glide.with(this).load(data.getData()).into(binding.imgvProfile);//갤러리 이미지가 잘붙는지?
            img_path = getRealPath(data.getData());
            num = 1;
        }
    }

    public String getRealPath(Uri contentUri){
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};//
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Cursor cursor = getContentResolver().query(contentUri, proj, null, null);
            if(cursor.moveToFirst()){
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                res = cursor.getString(column_index);
            }
            cursor.close();//다썼으니까 닫음.
        }
        Log.d("TAG", "getRealPath: 커서"+res);
        return res;
    }



}