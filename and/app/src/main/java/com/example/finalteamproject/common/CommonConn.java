package com.example.finalteamproject.common;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.finalteamproject.Login.CustomProgressDialog;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommonConn {

    // Retrofit을 매번 새로 인스턴스화해서 사용하는 것은 매우 귀찮음
    // 재사용이 가능한 구조를 하나 만들고 재사용하면 편함.

    private final String TAG = "CommonConn";

    private HashMap<String, Object> paramMap; //파라메터 전송용
    private Context context; //화면위에 토스트, ProgressDialog를 보여주기위한 용도
    private String mapping; //list.cu, login, member 등의 맵핑을 받아오기 위한 것.

    private CustomProgressDialog dialog; //모양이 다양하게 커스텀이 가능하니 나중에 바꾸면 된다.

    private CallBack callBack;

    public CommonConn(Context context, String mapping) {
        this.context = context;
        this.mapping = mapping;
        this.paramMap = new HashMap<>();
        this.dialog = new CustomProgressDialog(context);
    }

    public void addParamMap(String key, Object value){
        if(key==null){
        }else if(value==null){
        }else{
            paramMap.put(key, value);
        }
    }
    //enque (전송 실행전 해야할 코드를 넣어줄 메소드 구현, (ProgressDialog 보이게 처리))
    private void onPreExcute(){
        if(context!=null&&dialog==null){
            dialog.show();
        }
    }

    //enque가 실제로 되어야 하는 부분. (파라메터등을 이용해서 실제로 Spring에 전송한다. )↑
    public void onExcute(CallBack callBack){
        onPreExcute();
        //2. 옵저버 2
        this.callBack = callBack;
        RetrofitInterface api = new RetrofitClient().retrofitLogin().create(RetrofitInterface.class);
        //GET방식인지 POST방식인지를 받아와서 처리도 가능하다.(현재는 어려우니까 POST로 고정시켜놓기)
        api.postLogin(mapping, paramMap).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d(TAG, "onExcute . onResponse: "+response.body());
                onPostExcute(true, response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "onExcute . onFailure: "+t.getMessage());
                Toast.makeText(context, "서버와의 연결에 실패했습니다.(개발자에게 문의하세요.)", Toast.LENGTH_SHORT).show();
                onPostExcute(false, null);
            }
        });
    }

    private void onPostExcute(boolean isResult, String data){
        if(dialog!=null){
            dialog.dismiss();
        }
        //3. 옵저버3
        callBack.onResult(isResult, data);
    }

    // 옵저버 패턴 . 감시하다가 어떤 작업이 끝나면 특정 메소드를 실행함 : View.OnClickListener
    // 1.
    public interface CallBack{
        public void onResult(boolean isResult, String data);
    }

}
