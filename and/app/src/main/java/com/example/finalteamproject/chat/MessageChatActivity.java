package com.example.finalteamproject.chat;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.common.RetrofitClient;
import com.example.finalteamproject.common.RetrofitInterface;
import com.example.finalteamproject.databinding.ActivityMessageChatBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

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

    MessageChatAdapter adapter;

    ArrayList<Uri> uriList = new ArrayList<>();

    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    String currentTime = dateFormat.format(new Date());

    private FirebaseStorage storage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessageChatBinding.inflate(getLayoutInflater());
        adapter = new MessageChatAdapter(getlist(), this, isChatCheck);
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
                int position = adapter.getItemCount() - 1;
                if (position >= 0) {
                    binding.recvMessageChat.scrollToPosition(position);
                }
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
                MessageDTO messageDTO = (MessageDTO) getIntent().getSerializableExtra("dto");
                handleCameraImage(camera_uri, messageDTO);
            }
        });
    }

    private void handleCameraImage(Uri cameraImageUri, MessageDTO messageDTO) {
        storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        String itemName = messageDTO.getNickname();
        String uuid = UUID.randomUUID().toString();
        StorageReference riversRef = storageRef.child(CommonVar.logininfo.getMember_id() + "/" + uuid + ".jpg");

        riversRef.putFile(cameraImageUri).addOnSuccessListener(taskSnapshot -> {
            // 이미지 업로드 성공
            riversRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String imageUrl = uri.toString();
                String currentTime = dateFormat.format(new Date());

                // 채팅 메시지에 이미지 URL 추가
                messageId = databaseReference.child("chat").child(itemName).push().getKey();
                MessageDTO temp = new MessageDTO(messageDTO.getImgRes(), messageDTO.getNickname(), imageUrl, currentTime, true);
                databaseReference.child("chat").child(messageDTO.getNickname()).child(messageId).setValue(temp);

                // 어댑터 갱신 등의 필요한 작업 수행
//                adapter.addData(temp);
//                binding.recvMessageChat.scrollToPosition(adapter.getItemCount() - 1);
                adapter.notifyDataSetChanged();
            });
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
            cursor.close();
        }
        return res;
    }

    public void showGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, REQ_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MessageDTO messageDTO = (MessageDTO) getIntent().getSerializableExtra("dto");
        if (requestCode == REQ_GALLERY) {
            if (data == null) {   // 어떤 이미지도 선택하지 않은 경우
                Toast.makeText(getApplicationContext(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
            } else {   // 이미지를 하나라도 선택한 경우
                if (data.getClipData() == null) {     // 이미지를 하나만 선택한 경우
                    Uri imageUri = data.getData();

                } else {      // 이미지를 여러장 선택한 경우
                    ClipData clipData = data.getClipData();

                    if (clipData.getItemCount() > 10) {   // 선택한 이미지가 11장 이상인 경우
                        Toast.makeText(getApplicationContext(), "사진은 10장까지 선택 가능합니다.", Toast.LENGTH_LONG).show();
                    } else {   // 선택한 이미지가 1장 이상 10장 이하인 경우

                        ArrayList<UploadTask> upload = new ArrayList<>();
                        storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReference();
                        for (int i = 0; i < clipData.getItemCount(); i++) {
                            String itemName = messageDTO.getNickname();
                            String uuid = UUID.randomUUID().toString();


                            StorageReference riversRef = storageRef.child(CommonVar.logininfo.getMember_id() + "/" + uuid + ".jpg");
                            Uri imageUri = clipData.getItemAt(i).getUri();  // 선택한 이미지들의 uri를 가져온다.

                            final int tempIdx = i;
                            upload.add(riversRef.putFile(imageUri));
                            upload.get(tempIdx).addOnCompleteListener(command -> {

                                upload.get(tempIdx).getResult().getStorage().getDownloadUrl().addOnCompleteListener(command1 -> {
                                    messageId = databaseReference.child("chat").child(itemName).push().getKey();
                                    MessageDTO temp = new MessageDTO(messageDTO.getImgRes(), messageDTO.getNickname(), command1.getResult() + "", currentTime, true);
                                    databaseReference.child("chat").child(messageDTO.getNickname()).child(messageId).setValue(temp);
                                    adapter = new MessageChatAdapter(getlist(), this, isChatCheck);
                                    binding.recvMessageChat.setAdapter(adapter);
                                    binding.recvMessageChat.setLayoutManager(new LinearLayoutManager(this));
                                });

                            });
                        }


                    }
                }
            }
        }
    }

    public void showCamera() {
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