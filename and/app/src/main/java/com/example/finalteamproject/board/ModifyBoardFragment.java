package com.example.finalteamproject.board;

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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalteamproject.R;
import com.example.finalteamproject.common.CommonConn;
import com.example.finalteamproject.common.CommonVar;
import com.example.finalteamproject.common.RetrofitClient;
import com.example.finalteamproject.common.RetrofitInterface;
import com.example.finalteamproject.databinding.FragmentModifyBoardBinding;
import com.example.finalteamproject.main.MainActivity;
import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyBoardFragment extends Fragment {

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == -1){
                //액티비티(카메라 액티비티)가 종료되면 콜백으로 데이터를 받는 부분. (기존에는 onActivityResult메소드가 실행되었고 현재는 해당 메소드)
                binding.rlPic.setVisibility(View.VISIBLE);
                Glide.with(getActivity()).load(camera_uri).into(binding.imgvPic);
                num = 2;
                file = new File(getRealPath(camera_uri));

            }
        }
    });

    FragmentModifyBoardBinding binding;
    MainActivity activity;
    FavorBoardVO vo;

    String board_name;
    String align;
    private final int REQ_GALLERY = 1000, REQ_CAMERA = 2000;
    static int num;
    static String img_path = null;
    static Uri camera_uri = null;
    File file = null;

    public ModifyBoardFragment(MainActivity activity, FavorBoardVO vo) {
        this.activity = activity;
        this.vo = vo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentModifyBoardBinding.inflate(inflater, container, false);

        CommonConn conn2 = new CommonConn(this.getContext(), "board/select");
        conn2.addParamMap("fav_board_id", vo.fav_board_id);
        conn2.onExcute((isResult, data) -> {
            FavorBoardVO vo = new Gson().fromJson(data, FavorBoardVO.class);
            if(vo!=null){
                binding.edtTitle.setText(vo.fav_board_title);
                binding.edtContent.setText(vo.fav_board_content);
                if(vo.fav_board_img!=null){
                    binding.rlPic.setVisibility(View.VISIBLE);
                    Glide.with(this).load(vo.fav_board_img).into(binding.imgvPic);
                }
            }else {
                Toast.makeText(activity, "게시글 불러오기 실패\n다시 시도해주세요", Toast.LENGTH_SHORT).show();
            }
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
                if(num==1){
                    //갤러리
                    RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"), new File(img_path));
                    MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "gallery.jpg", fileBody);
                    RetrofitInterface api = new RetrofitClient().retrofitLogin().create(RetrofitInterface.class);
                    HashMap<String, RequestBody> map = new HashMap<>();
                    map.put("fav_board_id", RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(vo.fav_board_id)));
                    map.put("fav_board_title", RequestBody.create(MediaType.parse("multipart/form-data"), binding.edtTitle.getText().toString()));
                    map.put("fav_board_content", RequestBody.create(MediaType.parse("multipart/form-data"), binding.edtContent.getText().toString()));
                    api.clientSendFile("board/modifyFile", map, filePart).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.body().equals("성공")){
                                Toast.makeText(ModifyBoardFragment.this.getContext(), "게시글 수정 성공", Toast.LENGTH_SHORT).show();
                                activity.replaceFragment(ModifyBoardFragment.this, new BoardContextFragment(activity, vo));
                            }else {
                                Toast.makeText(ModifyBoardFragment.this.getContext(), "게시글 수정 실패", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(ModifyBoardFragment.this.getContext(), "게시글 수정 실패", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if(num==2){
                    //카메라
                    RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
                    MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "camera.jpg", fileBody);
                    RetrofitInterface api = new RetrofitClient().retrofitLogin().create(RetrofitInterface.class);
                    HashMap<String, RequestBody> map = new HashMap<>();
                    map.put("fav_board_id", RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(vo.fav_board_id)));
                    map.put("fav_board_title", RequestBody.create(MediaType.parse("multipart/form-data"), binding.edtTitle.getText().toString()));
                    map.put("fav_board_content", RequestBody.create(MediaType.parse("multipart/form-data"), binding.edtContent.getText().toString()));
                    api.clientSendFile("board/modifyFile", map, filePart).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.body().equals("성공")){
                                Toast.makeText(ModifyBoardFragment.this.getContext(), "게시글 수정 성공", Toast.LENGTH_SHORT).show();
                                activity.replaceFragment(ModifyBoardFragment.this, new BoardContextFragment(activity, vo));
                            }else {
                                Toast.makeText(ModifyBoardFragment.this.getContext(), "게시글 수정 실패", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(ModifyBoardFragment.this.getContext(), "게시글 수정 실패", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    CommonConn conn1 = new CommonConn(this.getContext(), "board/modify");
                    conn1.addParamMap("fav_board_title", binding.edtTitle.getText().toString());
                    conn1.addParamMap("fav_board_content", binding.edtContent.getText().toString());
                    conn1.addParamMap("fav_board_id", vo.fav_board_id);
                    conn1.onExcute((isResult1, data1) -> {
                        if (data1.equals("성공")) {
                            Toast.makeText(ModifyBoardFragment.this.getContext(), "게시글 수정 성공", Toast.LENGTH_SHORT).show();
                            activity.replaceFragment(ModifyBoardFragment.this, new BoardContextFragment(activity, vo));
                        } else {
                            Toast.makeText(activity, "게시글 수정 실패", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        binding.imgvBack.setOnClickListener(v -> {
            activity.replaceFragment(this, new BoardContextFragment(activity, vo));
        });

        binding.imgvDelete.setOnClickListener(v -> {
            binding.rlPic.setVisibility(View.GONE);
            Toast.makeText(activity, "이미지가 삭제되었습니다", Toast.LENGTH_SHORT).show();
            img_path = null;
            file = null;
            num=0;
        });

        binding.lnImage.setOnClickListener(v -> {
            showDialog();
        });


        return binding.getRoot();
    }

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
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, camera_uri);
        launcher.launch(cameraIntent);
        // camera_uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        //Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //  startActivityForResult(cameraIntent, REQ_CAMERA);
        // cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, camera_uri);
//        launcher.launch(cameraIntent);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_GALLERY && resultCode==getActivity().RESULT_OK){
            binding.rlPic.setVisibility(View.VISIBLE);
            //갤러리 액티비티가 종료되었다. (사용자가 사진을 선택했는지?)
            Log.d("갤러리", "onActivityResult: "+data.getData());
            Log.d("갤러리", "onActivityResult: "+data.getData().getPath());
            Glide.with(this).load(data.getData()).into(binding.imgvPic);//갤러리 이미지가 잘붙는지?
            img_path = getRealPath(data.getData());
            num = 1;
        }
//        else if(requestCode==REQ_CAMERA && resultCode==getActivity().RESULT_OK){
//            binding.rlPic.setVisibility(View.VISIBLE);
//            Glide.with(this).load(file).into(binding.imgvPic);
//            file = new File(getRealPath(camera_uri));
//            num = 2;
//        }
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