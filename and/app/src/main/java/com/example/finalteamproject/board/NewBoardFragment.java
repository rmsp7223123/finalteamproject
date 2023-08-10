package com.example.finalteamproject.board;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalteamproject.Login.LoginFavorActivity;
import com.example.finalteamproject.Login.LoginProfileActivity;
import com.example.finalteamproject.Login.LoginVar;
import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.common.RetrofitClient;
import com.example.finalteamproject.common.RetrofitInterface;
import com.example.finalteamproject.databinding.FragmentNewBoardBinding;
import com.example.finalteamproject.main.MainActivity;
import com.google.android.gms.common.internal.service.Common;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewBoardFragment extends Fragment {

    MainActivity activity;
    FragmentNewBoardBinding binding;
    String board_name;
    String align;
    private final int REQ_GALLERY = 1000;
    ActivityResultLauncher<Intent> launcher;
    static int num;
    static String img_path = null;
    static Uri camera_uri = null;
    File file = null;
    String[] list = {"TV", "음악", "영화", "패션", "동물", "뉴스", "자동차", "운동", "게임"};

    public NewBoardFragment(Activity activity, String board_name, String align) {
        this.board_name = board_name;
        this.align = align;
        this.activity = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewBoardBinding.inflate(inflater, container, false);

        binding.spnBoard.getSelectedItem();
        Spinner favorSpinner = binding.spnBoard;
        ArrayAdapter favorAdapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_spinner_item, list);
        favorSpinner.setAdapter(favorAdapter);

        for (int i = 0; i < list.length; i++) {
            if(list[i].equals(board_name)){
                binding.spnBoard.setSelection(i);
            }
        }
        binding.spnBoard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                if(!list[i].equals(board_name)) {
                    board_name = list[i];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.imgvBack.setOnClickListener(v -> {
            activity.changeFragment(this, board_name, align, activity);
        });

        binding.lnImage.setOnClickListener(v -> {
            showDialog();
        });

        InputMethodManager manager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        binding.cvNext.setOnClickListener(v -> {
            if(binding.edtTitle.getText().toString().length()<1){
                Toast.makeText(activity, "제목을 입력하세요", Toast.LENGTH_SHORT).show();
                binding.edtTitle.requestFocus();
                manager.showSoftInput(binding.edtTitle, InputMethodManager.SHOW_IMPLICIT);
            }else if(binding.edtContent.getText().toString().length()<1){
                Toast.makeText(activity, "내용을 입력하세요", Toast.LENGTH_SHORT).show();
                binding.edtContent.requestFocus();
                manager.showSoftInput(binding.edtContent, InputMethodManager.SHOW_IMPLICIT);
            }else {
                CommonConn conn = new CommonConn(this.getContext(), "board/favor");
                conn.addParamMap("favor", board_name);
                conn.onExcute((isResult, data) -> {
                    if(Integer.parseInt(data)>0){
                        CommonConn conn1 = new CommonConn(this.getContext(), "board/insert");
                        conn1.addParamMap("fav_board_title", binding.edtTitle.getText().toString());
                        conn1.addParamMap("fav_board_content", binding.edtContent.getText().toString());
                        conn1.addParamMap("writer", CommonVar.logininfo.getMember_id());
                        conn1.addParamMap("favor", Integer.parseInt(data));
                        conn1.onExcute((isResult1, data1) -> {
                            if (data1.equals("성공")) {
                                activity.changeFragment(this, board_name, align, activity);
                                Toast.makeText(activity, "게시글 등록 성공", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(activity, "게시글 등록 실패", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
            });


        return binding.getRoot();
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//            @Override
//            public void onActivityResult(ActivityResult result) {
//                //액티비티(카메라 액티비티)가 종료되면 콜백으로 데이터를 받는 부분. (기존에는 onActivityResult메소드가 실행되었고 현재는 해당 메소드)
//                Glide.with(NewBoardFragment.this).load(camera_uri).into(binding.imgvPic);
//                num = 2;
//                file = new File(getRealPath(camera_uri));
//            }
//        });
//    }

    public void showDialog(){
        String[] dialog_item = {"갤러리", "카메라"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
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
        camera_uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
//        camera_uri = applicationContext.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, camera_uri);
        launcher.launch(cameraIntent);
    }



    public String getRealPath(Uri contentUri){
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};//
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null);
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