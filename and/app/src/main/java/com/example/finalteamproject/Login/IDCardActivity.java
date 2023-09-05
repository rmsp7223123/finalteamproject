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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalteamproject.R;
import com.example.finalteamproject.databinding.ActivityIdcardBinding;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequest;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class IDCardActivity extends AppCompatActivity {

    ActivityIdcardBinding binding;
    ActivityResultLauncher<Intent> launcher;
    private final int REQ_GALLERY = 1000;
    private static final String CLOUD_VISION_API_KEY = "AIzaSyDnbv8mUCDz4JLZ6M2Q7Xsn7v20gK9bU84";
    private static final String ANDROID_CERT_HEADER = "X-Android-Cert";
    private static final String ANDROID_PACKAGE_HEADER = "X-Android-Package";
    private static final int MAX_LABEL_RESULTS = 10;

    static String name = null;
    static String birth = null;
    static String gender = null;


//    private com.example.finalteamproject.Login.ProgressDialog customProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIdcardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //new HideActionBar().hideActionBar(this);

//        //로딩창 객체 생성
//        customProgressDialog = new ProgressDialog(this);
//        customProgressDialog.setCancelable(false); // 로딩창 주변 클릭 시 종료 막기
//        //로딩창을 투명하게 하는 코드
//        customProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        //getWindow (): 현재 액티비티의 Window 객체를 가져와서 Window 객체를 통해 뷰들의 위치 크기, 색상 조절
//        //Window는 View 의 상위 개념으로, 뷰들을(버튼, 텍스트뷰, 이미지뷰) 감쌓고 있는 컨테이너 역할을 함
//        customProgressDialog.show();

        binding.edtRgNumber.setClickable(false);
        binding.edtRgNumber.setFocusable(false);

        binding.imgvBack.setOnClickListener(v -> {
            finish();
        });

        binding.lnCamera.setOnClickListener(v -> {
            showDialog();
        });

        binding.edtRgNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.cvNext.setOnClickListener(v -> {
            Log.d("edtRgNumber", "onCreate: "+binding.edtRgNumber.getText().toString());
            int year, month, date;
            year = Integer.parseInt(birth.substring(0,2));
            if(birth.charAt(2) == '0'){
                month = Integer.parseInt(birth.substring(2,4));
            }else {
                month = Integer.parseInt(birth.substring(2,4));
            }
            if(birth.charAt(4) == '0'){
                date = Integer.parseInt(birth.substring(4,6));
            }else {
                date = Integer.parseInt(birth.substring(4,6));
            }

            if(binding.edtRgNumber.getText().toString().length()<1){
                Toast.makeText(this, "주민등록증 인증을 완료해주세요", Toast.LENGTH_SHORT).show();
            }else  if(month<1||month>12||date<1||date>31){
                Toast.makeText(this, "잘못된 형식입니다 \n신분증을 다시 촬영해주세요", Toast.LENGTH_SHORT).show();
            }else if(year>=80||Integer.parseInt(gender)>2){
                Toast.makeText(this, "45세 이상부터 가입이 가능합니다", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(this, LoginInfoActivity.class);
                LoginVar.name = name;
                LoginVar.birth = birth;
                LoginVar.gender = Integer.parseInt(gender)%2==1 ? "남" : "여";
                Log.d("result name", "onCreate: "+name);
                Log.d("result gender", "onCreate: "+LoginVar.gender);
                Log.d("result birth", "onCreate: "+birth);
                startActivity(intent);
            }
        });

    }

    private static class LableDetectionTask extends AsyncTask<Object, Void, String> {
        private final WeakReference<IDCardActivity> mActivityWeakReference;
        private Vision.Images.Annotate mRequest;
        private CustomProgressDialog dialog;

        LableDetectionTask(IDCardActivity activity, Vision.Images.Annotate annotate , CustomProgressDialog dialog) {
            mActivityWeakReference = new WeakReference<>(activity);
            mRequest = annotate;
            this.dialog = dialog;
        }

        @Override
        protected String doInBackground(Object... params) {
            try {
                BatchAnnotateImagesResponse response = mRequest.execute();
                return convertResponseToString(response);

            } catch (GoogleJsonResponseException e) {
                Log.d("TAG", "failed to make API request because of other IOException " +
                        e.getMessage());
            }  catch (IOException e) {
                Log.d("TAG", "failed to make API request because of other IOException " +
                        e.getMessage());
            }
            return "Cloud Vision API request failed. Check logs for details.";
        }

        protected void onPostExecute(String result) {
            IDCardActivity activity = mActivityWeakReference.get();
            if (activity != null && !activity.isFinishing()) {
                TextView tv_result = activity.findViewById(R.id.edt_rgNumber);
                try {
                    if(result.contains("주민등록증")){
                        name = result.substring(result.indexOf("증")+2, result.indexOf("("));
                        birth = result.substring(result.indexOf("-")-6, result.indexOf("-"));
                        gender = result.substring(result.indexOf("-")+1, result.indexOf("-")+2);
                        tv_result.setText(result.substring(result.indexOf("-")-6, result.indexOf("-")+2));
                    }else if(result.contains("자동차운전면허증")){
                        name = result.substring(result.indexOf("-", 115)+3, result.indexOf("-", 123)-6);
                        birth = result.substring(result.indexOf("-", 123)-6, result.indexOf("-", 123));
                        gender = result.substring(result.indexOf("-", 123)+1, result.indexOf("-", 123)+2);
                        tv_result.setText(result.substring(result.indexOf("-", 123)-6, result.indexOf("-", 123)+2));
                    }else {
                        Toast.makeText(activity, "신분증을 선택해주세요", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e) {
                    Toast.makeText(activity, "신분증을 선택해주세요", Toast.LENGTH_SHORT).show();
                }
            }
            dialog.dismiss();
        }
    }

    private static String convertResponseToString(BatchAnnotateImagesResponse response) {
        StringBuilder message = new StringBuilder("I found these things:\n\n");

        List<EntityAnnotation> labels = response.getResponses().get(0).getTextAnnotations();
        if (labels != null) {
            for (EntityAnnotation label : labels) {

                message.append(String.format(Locale.US, "%.3f: %s", label.getScore(), label.getDescription()));
                message.append("\n");
            }
        } else {
            message.append("nothing");
        }

        return message.toString();
    }

    private Vision.Images.Annotate prepareAnnotationRequest(Bitmap bitmap) throws IOException {

        HttpTransport httpTransport = new NetHttpTransport();// AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        VisionRequestInitializer requestInitializer =
                new VisionRequestInitializer(CLOUD_VISION_API_KEY) {
                    /**
                     * We override this so we can inject important identifying fields into the HTTP
                     * headers. This enables use of a restricted cloud platform API key.
                     */
                    @Override
                    protected void initializeVisionRequest(VisionRequest<?> visionRequest)
                            throws IOException {
                        super.initializeVisionRequest(visionRequest);

                        String packageName = getPackageName();
                        visionRequest.getRequestHeaders().set(ANDROID_PACKAGE_HEADER, packageName);

                        String sig = PackageManagerUtils.getSignature(getPackageManager(), packageName);


                        visionRequest.getRequestHeaders().set(ANDROID_CERT_HEADER, sig);
                    }
                };

        Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
        builder.setVisionRequestInitializer(requestInitializer);

        Vision vision = builder.build();

        BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                new BatchAnnotateImagesRequest();
        batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {{
            AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();

            // Add the image
            Image base64EncodedImage = new Image();
            // Convert the bitmap to a JPEG
            // Just in case it's a format that Android understands but Cloud Vision
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            // Base64 encode the JPEG
            base64EncodedImage.encodeContent(imageBytes);
            annotateImageRequest.setImage(base64EncodedImage);

            // add the features we want
            annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                Feature labelDetection = new Feature();
                labelDetection.setType("TEXT_DETECTION");
                labelDetection.setMaxResults(MAX_LABEL_RESULTS);
                add(labelDetection);
            }});

            // Add the list of one thing to the request
            add(annotateImageRequest);
        }});

        Vision.Images.Annotate annotateRequest =
                vision.images().annotate(batchAnnotateImagesRequest);
        // Due to a bug: requests to Vision API containing large images fail when GZipped.
        annotateRequest.setDisableGZipContent(true);
        Log.d("TAG", "created Cloud Vision request object, sending request");

        return annotateRequest;
    }

    private final int REQ_PERMISSION = 1000;
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


    @Override
    protected void onStart() {
        super.onStart();

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                //액티비티(카메라 액티비티)가 종료되면 콜백으로 데이터를 받는 부분. (기존에는 onActivityResult메소드가 실행되었고 현재는 해당 메소드)
//                Glide.with(IDCardActivity.this).load(camera_uri).into(binding.imgv);
                File file = new File(getRealPath(camera_uri));
                try {
                    if(file!=null){
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                        Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options);

                        LableDetectionTask lableDetectionTask = new LableDetectionTask(IDCardActivity.this ,prepareAnnotationRequest(bitmap) , getDialog() );
                        lableDetectionTask.execute();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    Uri camera_uri = null;

    public void showCamera(){
        camera_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, camera_uri);
        launcher.launch(cameraIntent);
    }


    public void showGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
//        startActivity(intent); //단순 실행 결과를 알수가없다.
        startActivityForResult(intent, REQ_GALLERY);
    }

    public CustomProgressDialog getDialog(){
        CustomProgressDialog dialog = new CustomProgressDialog(this);
        dialog.show();
        return dialog;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_GALLERY && resultCode == RESULT_OK){

            //갤러리 액티비티가 종료 되었다. ( 사용자가 사진을 선택했는지?)
            Log.d("갤러리", "onActivityResult: " + data.getData());
            Log.d("갤러리", "onActivityResult: " + data.getData().getPath());
            String img_path = getRealPath(data.getData());
//            Glide.with(this).load(img_path).into(binding.imgv);
            File file = new File(img_path);
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options);

                LableDetectionTask lableDetectionTask = new LableDetectionTask(this ,prepareAnnotationRequest(bitmap), getDialog() );
                lableDetectionTask.execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

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