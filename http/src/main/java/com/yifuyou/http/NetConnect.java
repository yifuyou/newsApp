package com.yifuyou.http;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NetConnect {
    public static final String BASEURL="https://www.baidu.com";
    private static NetConnect instance;
    private RetrofitInit retrofit;
    private NetConnect(){
        retrofit = new RetrofitInit();
    }

    public static NetConnect builder(){
        if(instance==null){
            instance=new NetConnect();
        }
        return instance;
    }

   public void call(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestApi requestApi = retrofit._retrofit.create(RequestApi.class);
                try {
                Response<ResponseBody> response = requestApi.get().execute();
                if(response.isSuccessful()){
                        assert response.body()!=null;
                        System.out.println(" response.body"+response.body().string());
                        System.out.println(" response.body"+response.body().source());
                }
                else {
                    System.out.println("code "+ response.code()+",  message "+response.message());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            }
        }).start();

   }

    static class RetrofitInit{
        Retrofit _retrofit;
        RetrofitInit(){
             _retrofit=new Retrofit.Builder()
                    .client(new OkHttpClient())
                    .baseUrl(BASEURL)
                    .build();

        }
    }
}
