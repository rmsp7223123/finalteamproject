package com.example.finalteamproject.common;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface RetrofitInterface {
    @FormUrlEncoded
    @POST
    Call<String> postLogin(@Url String url, @FieldMap HashMap<String, Object> paramMap);

    @GET("{url}")
    Call<String> getLogin(@Url String url, @QueryMap HashMap<String, Object> paramMap);

    @Multipart // <=파일이 전송될때에 어노테이션(Multipart)
    @POST
    Call<String> clientSendFile(@Url String url, @PartMap HashMap<String, RequestBody> maps, @Part MultipartBody.Part file);
}
