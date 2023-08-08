package com.example.finalteamproject.common;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    public Retrofit retrofitLogin(){
        return new Retrofit.Builder()
                .baseUrl("http://192.168.0.87:8080/cloud/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }
}
