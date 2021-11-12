package com.yifuyou.http;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RequestApi {

    @GET("/")
    Call<ResponseBody> get();


}
