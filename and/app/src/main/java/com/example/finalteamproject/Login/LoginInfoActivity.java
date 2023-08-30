package com.example.finalteamproject.Login;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.finalteamproject.FirebaseMessageReceiver;
import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.RetrofitClient;
import com.example.finalteamproject.common.RetrofitInterface;
import com.example.finalteamproject.databinding.ActivityLoginInfoBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.integrity.b;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginInfoActivity extends AppCompatActivity {

    ActivityLoginInfoBinding binding;
    private final int REQUEST_CODE = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        new HideActionBar().hideActionBar(this);


        binding.edtName.setClickable(false);
        binding.edtName.setFocusable(false);


        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });

        binding.edtName.setText(LoginVar.name);

        binding.edtNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.tvNicknameCheck.setVisibility(View.VISIBLE);
                binding.tvNicknameCheckDone.setVisibility(View.GONE);
            }
        });

        binding.edtId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Pattern.compile("[A-Z$@$!%*#?&/가-힣ㄱ-ㅎㅏ-ㅣ\\x20]").matcher(binding.edtId.getText().toString()).find()) {
                    binding.tvIdAlert.setVisibility(View.VISIBLE);
                    binding.tvIdAlert.setText("영문, 숫자 외의 다른 문자 미포함");
                } else if (!Pattern.compile("[0-9]").matcher(binding.edtId.getText().toString()).find()) {
                    binding.tvIdAlert.setVisibility(View.VISIBLE);
                    binding.tvIdAlert.setText("숫자 1글자 이상");
                } else if (!Pattern.compile("[a-z]").matcher(binding.edtId.getText().toString()).find()) {
                    binding.tvIdAlert.setVisibility(View.VISIBLE);
                    binding.tvIdAlert.setText("영문 1글자 이상");
                } else if (binding.edtId.getText().toString().length() < 5 || binding.edtId.getText().toString().length() > 15) {
                    binding.tvIdAlert.setVisibility(View.VISIBLE);
                    binding.tvIdAlert.setText("5 - 15자");
                } else {
                    binding.tvIdAlert.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                binding.tvIdCheck.setVisibility(View.VISIBLE);
                binding.tvIdCheckDone.setVisibility(View.GONE);
            }
        });

        binding.tvIdCheck.setOnClickListener(v -> {
            if (binding.edtId.getText().toString().length() > 0 && binding.tvIdAlert.getVisibility() == View.INVISIBLE) {
                CommonConn conn = new CommonConn(this, "login/checkId");
                conn.addParamMap("member_id", binding.edtId.getText().toString());
                conn.onExcute((isResult, data) -> {
                    if (!data.equals("null")) {
                        Toast.makeText(this, "이미 존재하는 아이디입니다", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "사용가능한 아이디입니다", Toast.LENGTH_SHORT).show();
                        binding.tvIdCheck.setVisibility(View.GONE);
                        binding.tvIdCheckDone.setVisibility(View.VISIBLE);
                    }
                });
            } else {
                Toast.makeText(this, "사용가능한 아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
            }
        });
        binding.tvNicknameCheck.setOnClickListener(v -> {
            if (binding.edtNickname.getText().toString().length() > 0) {
                CommonConn conn = new CommonConn(this, "login/checkNickname");
                conn.addParamMap("nickname", binding.edtNickname.getText().toString());
                conn.onExcute((isResult, data) -> {
                    if (!data.equals("null")) {
                        Toast.makeText(this, "이미 존재하는 닉네임입니다", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "사용가능한 닉네임입니다", Toast.LENGTH_SHORT).show();
                        binding.tvNicknameCheck.setVisibility(View.GONE);
                        binding.tvNicknameCheckDone.setVisibility(View.VISIBLE);
                    }
                });
            } else {
                Toast.makeText(this, "닉네임 중복확인을 완료해주세요", Toast.LENGTH_SHORT).show();
            }
        });

        binding.edtPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Pattern.compile("[가-힣]").matcher(binding.edtPw.getText().toString()).find()) {
                    binding.tvPwAlert.setVisibility(View.VISIBLE);
                    binding.tvPwAlert.setText("영문, 숫자, 특수문자 외의 다른 문자 미포함");
                }
                if (!Pattern.compile("[0-9]").matcher(binding.edtPw.getText().toString()).find()) {
                    binding.tvPwAlert.setVisibility(View.VISIBLE);
                    binding.tvPwAlert.setText("숫자 1글자 이상");
                } else if (!Pattern.compile("[a-z]").matcher(binding.edtPw.getText().toString()).find()) {
                    binding.tvPwAlert.setVisibility(View.VISIBLE);
                    binding.tvPwAlert.setText("영문 1글자 이상");
                } else if (!Pattern.compile("[A-Z$@$!%*#?&]").matcher(binding.edtPw.getText().toString()).find()) {
                    binding.tvPwAlert.setVisibility(View.VISIBLE);
                    binding.tvPwAlert.setText("특수문자 1글자 이상");
                } else if (binding.edtPw.getText().toString().length() < 8 || binding.edtPw.getText().toString().length() > 18) {
                    binding.tvPwAlert.setVisibility(View.VISIBLE);
                    binding.tvPwAlert.setText("8 - 18자");
                } else {
                    binding.tvPwAlert.setVisibility(View.INVISIBLE);
                }


                if (binding.edtPwCheck.getText().toString().equals(binding.edtPw.getText().toString())) {
                    binding.tvPwCheckAlert.setVisibility(View.INVISIBLE);
                } else {
                    binding.tvPwCheckAlert.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.edtPwCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (binding.edtPwCheck.getText().toString().equals(binding.edtPw.getText().toString())) {
                    binding.tvPwCheckAlert.setVisibility(View.INVISIBLE);
                } else {
                    binding.tvPwCheckAlert.setVisibility(View.VISIBLE);
                }

            }
        });

        binding.edtAddress.setOnClickListener(v -> {
            Intent intent = new Intent(this, SearchAddress.class);
            startActivityForResult(intent, REQUEST_CODE);
        });


        InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        binding.cvNext.setOnClickListener(v -> {
            if (binding.edtName.getText().toString().length() < 1) {
                Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                binding.edtName.requestFocus();
                manager.showSoftInput(binding.edtName, InputMethodManager.SHOW_IMPLICIT);
            } else if (binding.tvNicknameCheck.getVisibility() == View.VISIBLE) {
                Toast.makeText(this, "닉네임을 중복확인을 완료해주세요", Toast.LENGTH_SHORT).show();
                binding.edtNickname.requestFocus();
                manager.showSoftInput(binding.edtNickname, InputMethodManager.SHOW_IMPLICIT);
            } else if (binding.tvIdCheck.getVisibility() == View.VISIBLE) {
                Toast.makeText(this, "아이디를 중복확인을 완료해주세요", Toast.LENGTH_SHORT).show();
                binding.edtId.requestFocus();
                manager.showSoftInput(binding.edtId, InputMethodManager.SHOW_IMPLICIT);
            } else if (binding.edtPw.getText().toString().length() < 1 || binding.tvPwAlert.getVisibility() == View.VISIBLE) {
                Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                binding.edtPw.requestFocus();
                manager.showSoftInput(binding.edtPw, InputMethodManager.SHOW_IMPLICIT);
            } else if (binding.tvPwCheckAlert.getVisibility() == View.VISIBLE || binding.edtPwCheck.getText().toString().length() < 1) {
                Toast.makeText(this, "비밀번호 확인을 완료해주세요", Toast.LENGTH_SHORT).show();
                binding.edtPwCheck.requestFocus();
                manager.showSoftInput(binding.edtPwCheck, InputMethodManager.SHOW_IMPLICIT);
            } else if(binding.edtAddress.getText().toString().length()<1){
                Toast.makeText(this, "주소 선택을 완료해주세요", Toast.LENGTH_SHORT).show();
                binding.edtAddress.performClick();
            } else if(binding.edtAddressDetail.getText().toString().length()<1){
                Toast.makeText(this, "상세주소 선택을 완료해주세요", Toast.LENGTH_SHORT).show();
                binding.edtAddressDetail.requestFocus();
                manager.showSoftInput(binding.edtAddressDetail, InputMethodManager.SHOW_IMPLICIT);
            }else {
                LoginVar.name = binding.edtName.getText().toString();
                LoginVar.nickname = binding.edtNickname.getText().toString();
                LoginVar.id = binding.edtId.getText().toString();
                LoginVar.pw = binding.edtPw.getText().toString();
                LoginVar.address = binding.edtAddress.getText().toString()+binding.edtAddressDetail.getText().toString();
                CommonConn conn = new CommonConn(this, "login/join");
                conn.addParamMap("member_id", LoginVar.id);
                conn.addParamMap("member_pw", LoginVar.pw);
                conn.addParamMap("member_name", LoginVar.name);
                conn.addParamMap("member_nickname", LoginVar.nickname);
                conn.addParamMap("member_birth", LoginVar.birth);
                conn.addParamMap("member_gender", LoginVar.gender);
                conn.addParamMap("member_phone", LoginVar.phone);
                conn.addParamMap("member_address", LoginVar.address);
//                conn.addParamMap("member_phone_id", Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID));
                FirebaseMessaging.getInstance().getToken()
                        .addOnCompleteListener(new OnCompleteListener<String>() {
                            @Override
                            public void onComplete(@NonNull Task<String> task) {
                                if (!task.isSuccessful()) {
                                    return;
                                }

                                // Get new FCM registration token
                                String token = task.getResult();

                                // Log and toast
                                conn.addParamMap("member_phone_id", token);
                                conn.onExcute((isResult, data) -> {
                                    if (data.equals("성공")) {
                                        Toast.makeText(LoginInfoActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginInfoActivity.this, LoginProfileActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(LoginInfoActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String data = intent.getExtras().getString("data");
            if(data!=null){
                Log.d("address", "onActivityResult: "+data);
                binding.edtAddress.setText(data);
            }

        }
    }
}
