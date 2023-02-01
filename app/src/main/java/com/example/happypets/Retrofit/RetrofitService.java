package com.example.happypets.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    public Retrofit retrofit;
    public RetrofitService(){
        initializeRetrofit();
    }

    private void initializeRetrofit() {

        /*
        * creating OkHttpClient for connecting with http
        * here we increase the connection time because image is sent is will take a lot of time
        * so we need to increase the connection time out time
         */
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(200, TimeUnit.SECONDS);
        client.readTimeout(200, TimeUnit.SECONDS);
        client.writeTimeout(200, TimeUnit.SECONDS);


        retrofit=new Retrofit.Builder()
                .baseUrl("http://192.168.103.112:8080")
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .client(client.build())
                .build();
    }

    public Retrofit getRetrofit(){
        return retrofit;
    }
}
