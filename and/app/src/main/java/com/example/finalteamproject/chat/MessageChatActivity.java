package com.example.finalteamproject.chat;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalteamproject.FirebaseMessageReceiver;
import com.example.finalteamproject.R;
import com.example.finalteamproject.common.RetrofitClient;
import com.example.finalteamproject.common.RetrofitInterface;
import com.example.finalteamproject.databinding.ActivityMessageChatBinding;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessageChatActivity extends AppCompatActivity {
    ActivityMessageChatBinding binding;
    boolean isChatCheck = false;
    private int sendCnt = 0;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    public static String messageId = "";

    private final int REQ_GALLERY = 1000;

    ActivityResultLauncher<Intent> launcher;

    private static final int SPEECH_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessageChatBinding.inflate(getLayoutInflater());
        MessageChatAdapter adapter = new MessageChatAdapter(getlist(), this, isChatCheck);
        setContentView(binding.getRoot());
        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });

        MessageDTO messageDTO = (MessageDTO) getIntent().getSerializableExtra("dto");

        String itemName = messageDTO.getNickname();

        binding.tvNickname.setText(messageDTO.getNickname());
        binding.imgvProfileImg.setImageResource(messageDTO.getImgRes());
        binding.recvMessageChat.setAdapter(adapter);
        binding.recvMessageChat.setLayoutManager(new LinearLayoutManager(this));

        binding.imgvSend.setOnClickListener(v -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String currentTime = dateFormat.format(new Date());
            String messageText = binding.edtMessage.getText().toString();
            if (!messageText.isEmpty()) {
                // String name = getIntent().getStringExtra("nickname");
                //int imgRes = getIntent().getIntExtra("img",0);
                messageId = databaseReference.child("chat").child(itemName).push().getKey();
                MessageDTO temp = new MessageDTO(messageDTO.getImgRes(), messageDTO.getNickname(), messageText, currentTime, true);
                // 파이어베이스 경로를 닉네임이 아닌 id로 바꾸기
                databaseReference.child("chat").child(messageDTO.getNickname()).child(messageId).setValue(temp);
                binding.recvMessageChat.scrollToPosition(adapter.getItemCount() - 1);
                adapter.notifyDataSetChanged();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        FirebaseMessageReceiver.showNotification(MessageChatActivity.this, binding.tvNickname.getText().toString(), binding.edtMessage.getText().toString());

                    }
                });
                binding.edtMessage.setText("");
            }
        });

        databaseReference.child("chat").child(messageDTO.getNickname()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MessageDTO MessageDTO = dataSnapshot.getValue(MessageDTO.class);
                MessageDTO.setImgRes(messageDTO.getImgRes());
                adapter.addData(MessageDTO);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                MessageDTO messageDTO = dataSnapshot.getValue(MessageDTO.class);
                adapter.removeData(messageDTO);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        binding.containerLinearSendFile.setVisibility(View.GONE);
        binding.imgvSendFile.setOnClickListener(view -> {
            if (sendCnt % 2 == 1) {
                binding.containerLinearSendFile.setVisibility(View.VISIBLE);
            } else {
                binding.containerLinearSendFile.setVisibility(View.GONE);
            }
            if (binding.containerLinearSendFile.getVisibility() == View.VISIBLE) {
                Animation slideDownAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_down);

                binding.containerLinearSendFile.setVisibility(View.GONE);
                binding.imgvSendFile.setImageResource(R.drawable.baseline_add_24);
            } else {
                binding.containerLinearSendFile.setVisibility(View.VISIBLE);
                Animation slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);

                binding.imgvSendFile.setImageResource(R.drawable.baseline_close_24);
            }

            sendCnt++;
        });
        binding.cvAlbum.setOnClickListener(v -> {
            showGallery();
        });
        binding.cvCamera.setOnClickListener(v -> {
            showCamera();
        });
        binding.cvVoice.setOnClickListener(v -> {
            displaySpeechRecognizer();
        });
    }

    public ArrayList<MessageDTO> getlist() {
        ArrayList<MessageDTO> list = new ArrayList<>();
        return list;
    }

    Uri camera_uri = null;


    @Override
    protected void onStart() {
        super.onStart();
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                Glide.with(MessageChatActivity.this).load(camera_uri).into(binding.imgvProfileImg);
                File file = new File(getRealPath(camera_uri));
                if (file != null) {
                    RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
                    MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "test.jpg", fileBody);
                    RetrofitInterface api = new RetrofitClient().retrofitLogin().create(RetrofitInterface.class);
                    api.clientSendFile("file.f", new HashMap<>(), filePart).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }
            }
        });
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
            cursor.close();
        }
        return res;
    }

    public void showGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, REQ_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
        }
        if(requestCode==REQ_GALLERY && resultCode ==RESULT_OK){
            Glide.with(this).load(data.getData()).into(binding.imgvProfileImg);
            String img_path = getRealPath(data.getData());

            //MultiPart 형태로 전송 (File)
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"), new File(img_path));
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "test.jpg", fileBody);
            RetrofitInterface api = new RetrofitClient().retrofitLogin().create(RetrofitInterface.class);
            api.clientSendFile("file.f", new HashMap<>(), filePart).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                }
            });
        }
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

    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
// This starts the activity and populates the intent with the speech text.
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }


}