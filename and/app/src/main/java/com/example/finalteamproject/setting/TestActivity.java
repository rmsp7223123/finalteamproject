package com.example.finalteamproject.setting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.common.RetrofitClient;
import com.example.finalteamproject.common.RetrofitInterface;
import com.example.finalteamproject.databinding.ActivityTestBinding;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestActivity extends AppCompatActivity {
    ActivityTestBinding binding;

    private final int REQ_GALLERY = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void showGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true    );
        startActivityForResult(intent, REQ_GALLERY);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
//            List<String> results = data.getStringArrayListExtra(
//                    RecognizerIntent.EXTRA_RESULTS);
//            String spokenText = results.get(0);
//        }
//        if(requestCode==REQ_GALLERY && resultCode ==RESULT_OK){
//            Glide.with(this).load(data.getData()).into(binding.imgvProfileImg);
//            String img_path = getRealPath(data.getData());
//
//            //MultiPart 형태로 전송 (File)
//            RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"), new File(img_path));
//            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file",  CommonVar.logininfo.getMember_id() + "jpg", fileBody);
//            RetrofitInterface api = new RetrofitClient().retrofitLogin().create(RetrofitInterface.class);
//            api.clientSendFile("file.f", new HashMap<>(), filePart).enqueue(new Callback<String>() {
//                @Override
//                public void onResponse(Call<String> call, Response<String> response) {
//
//                }
//
//                @Override
//                public void onFailure(Call<String> call, Throwable t) {
//
//                }
//            });
//        }
//    }
}