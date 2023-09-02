package com.example.finalteamproject.common;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.HEAD;

public class RetrofitClient {
    public Retrofit retrofitLogin(){
        return new Retrofit.Builder()
                .baseUrl("http://121.148.107.94:8080/cloud/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }
}
