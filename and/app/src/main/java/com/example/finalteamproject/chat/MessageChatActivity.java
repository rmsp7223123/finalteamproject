package com.example.finalteamproject.chat;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.finalteamproject.ChangeStatusBar;
import com.example.finalteamproject.FirebaseMessageReceiver;
import com.example.finalteamproject.Login.ProgressDialog;
import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.databinding.ActivityMessageChatBinding;
import com.example.finalteamproject.main.FriendVO;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

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

    ArrayList<String> uriList = new ArrayList<>();

    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
    String currentTime = dateFormat.format(new Date());

    private FirebaseStorage storage;
    FriendVO friendVO;

    boolean isOpponentInActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMessageChatBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });
        new ChangeStatusBar().changeStatusBarColor(this);
        friendVO = (FriendVO) getIntent().getSerializableExtra("vo");

        adapter = new MessageChatAdapter(getlist(), this, isChatCheck, friendVO.getMember_profileimg(), friendVO.getMember_nickname());
        binding.tvNickname.setText(friendVO.getMember_nickname());
        Glide.with(this).load(friendVO.getMember_profileimg()).apply(new RequestOptions().circleCrop()).into(binding.imgvProfileImg);
        binding.recvMessageChat.setAdapter(adapter);
        binding.recvMessageChat.setLayoutManager(new LinearLayoutManager(this));
        binding.imgvSend.setOnClickListener(v -> {
            String messageText = binding.edtMessage.getText().toString();
            if (!messageText.isEmpty()) {
                currentTime = dateFormat.format(new Date());
                FriendVO vo = new FriendVO(friendVO.getMember_id(), friendVO.getFriend_id(), friendVO.getMember_nickname(), friendVO.getMember_profileimg(), currentTime, binding.edtMessage.getText().toString(), true);

                    vo.setMember_nickname(friendVO.getMember_nickname());
                    sendMsg(friendVO.getMember_id(), friendVO.getFriend_id() ,vo, true);

                    vo.setMember_id(friendVO.getFriend_id());
                    vo.setFriend_id(friendVO.getMember_id());
                    vo.setMember_profileimg(CommonVar.logininfo.getMember_profileimg());
                     vo.setMember_nickname(CommonVar.logininfo.getMember_nickname());
                    sendMsg(friendVO.getFriend_id(), friendVO.getMember_id() ,vo, false);
                //vo.setMember_nickname(CommonVar.logininfo.getMember_nickname());
                //friendVO.getMember_nickname()
                // isChatCheck가 true일때 상대방 닉네임이 들어가야함


                    sendNotification(vo);

                binding.edtMessage.setText("");
            }
        });
        FirebaseMessageReceiver.friend_id = friendVO.getMember_id();

        databaseReference.child("chat").child(friendVO.getMember_id()).child(friendVO.getFriend_id()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FriendVO friendVO = dataSnapshot.getValue(FriendVO.class);
                adapter.addData(friendVO);
                int position = adapter.getItemCount() - 1;
                if (position >= 0) {
                    binding.recvMessageChat.scrollToPosition(position);
                }
                String imageText = friendVO.getContent();
                if (imageText.contains("https://firebasestorage.googleapis.com/")) {
                    uriList.add(imageText);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                FriendVO friendVO = dataSnapshot.getValue(FriendVO.class);
                adapter.removeData(friendVO);
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
            // 키보드가 올라온 상태에서 + 버튼을 눌렀을때 키보드가 내려가게
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(binding.imgvSendFile.getWindowToken(), 0);


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
        binding.imgvGallary.setOnClickListener(v -> {
            Intent intent = new Intent(MessageChatActivity.this, ChatPhotoGalleryActivity.class);
            intent.putExtra("img", uriList);
            startActivity(intent);
        });
    }

    @Override
    protected void onDestroy() {
        FirebaseMessageReceiver.friend_id = "";
        super.onDestroy();
    }

    public ArrayList<FriendVO> getlist() {
        ArrayList<FriendVO> list = new ArrayList<>();
        return list;
    }

    Uri camera_uri = null;


    //param1 =friendVO.getFriend_id()
    public void sendMsg(String mainId , String subId , FriendVO vo, boolean isChatCheck) {
        DatabaseReference def = databaseReference.child("chat").child(mainId).child(subId);
        messageId =def.push().getKey();
        vo.setCheck(isChatCheck);
        def.child(messageId).setValue(vo);
        binding.recvMessageChat.scrollToPosition(adapter.getItemCount() - 1);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                FriendVO friendVO = (FriendVO) getIntent().getSerializableExtra("vo");
//                MessageDTO messageDTO = (MessageDTO) getIntent().getSerializableExtra("dto");
                handleCameraImage(camera_uri, friendVO);
            }
        });
    }

    private void handleCameraImage(Uri cameraImageUri, FriendVO friendVO) {
        storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        String itemName = friendVO.getMember_nickname();
        String uuid = UUID.randomUUID().toString();
        StorageReference riversRef = storageRef.child(CommonVar.logininfo.getMember_id() + "/" + uuid + ".jpg");

        riversRef.putFile(cameraImageUri).addOnSuccessListener(taskSnapshot -> {
            // 이미지 업로드 성공
            riversRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String imageUrl = uri.toString();
                String currentTime = dateFormat.format(new Date());
                FriendVO vo = new FriendVO(friendVO.getMember_id(), friendVO.getFriend_id(), friendVO.getMember_nickname(), friendVO.getMember_profileimg(), currentTime, imageUrl, true);
                sendMsg(friendVO.getMember_id(), friendVO.getFriend_id() ,vo, true);
                sendMsg(friendVO.getFriend_id(), friendVO.getMember_id() ,vo, false);
                sendNotification(vo);
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
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (!results.isEmpty()) {
                String spokenText = results.get(0);
                binding.edtMessage.setText(spokenText);
            }
        }
//        MessageDTO messageDTO = (MessageDTO) getIntent().getSerializableExtra("dto");
        FriendVO friendVO = (FriendVO) getIntent().getSerializableExtra("vo");
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
                            String itemName = friendVO.getMember_nickname();
                            String uuid = UUID.randomUUID().toString();


                            StorageReference riversRef = storageRef.child(CommonVar.logininfo.getMember_id() + "/" + uuid + ".jpg");
                            Uri imageUri = clipData.getItemAt(i).getUri();  // 선택한 이미지들의 uri를 가져온다.

                            final int tempIdx = i;
                            upload.add(riversRef.putFile(imageUri));
                            upload.get(tempIdx).addOnCompleteListener(command -> {
                                upload.get(tempIdx).getResult().getStorage().getDownloadUrl().addOnCompleteListener(command1 -> {
                                    currentTime = dateFormat.format(new Date());
                                    FriendVO vo = new FriendVO(friendVO.getMember_id(), friendVO.getFriend_id(), friendVO.getMember_nickname(), friendVO.getMember_profileimg(), currentTime, command1.getResult() + "", true);
                                    sendMsg(friendVO.getMember_id(), friendVO.getFriend_id() ,vo, true);
                                    sendMsg(friendVO.getFriend_id(), friendVO.getMember_id() ,vo, false);
                                    sendNotification(vo);
                                });

                            });
                        }


                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    public void showCamera() {
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

    public void sendNotification(FriendVO vo) {
            CommonConn conn = new CommonConn(this, "main/addAlarm");
            conn.addParamMap("member_id", CommonVar.logininfo.getMember_id());
            conn.addParamMap("alarm_content", CommonVar.logininfo.getMember_nickname() + "님이 메시지를 보냈습니다.");
            currentTime = dateFormat.format(new Date());
            conn.addParamMap("alarm_time", currentTime);
            conn.addParamMap("receive_id", vo.getFriend_id());

            conn.onExcute((isResult1, data1) -> {
                if (isResult1) {
                    Log.d("TAG", "onClick: " + "확인용");
                }
            });
    }

}
