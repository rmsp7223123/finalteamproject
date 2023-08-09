package com.example.finalteamproject.common;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    public Retrofit retrofitLogin(){
        return new Retrofit.Builder()
<<<<<<< HEAD
                .baseUrl("http://192.168.0.87:8080/cloud/")
=======
<<<<<<< HEAD
                .baseUrl("http://192.168.0.87:8080/cloud/")
=======
                .baseUrl("http://192.168.0.33:8080/cloud/")
>>>>>>> bitna
>>>>>>> temp_main
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }
}
