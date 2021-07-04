package com.example.dunzodemoapp.networkcalls;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestController {
    private static final int HTTP_TIME_OUT = 60;

   public static Retrofit getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        Gson gson = new GsonBuilder().setLenient().create();
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(HTTP_TIME_OUT, TimeUnit.SECONDS);

        return new Retrofit.Builder()
                .baseUrl(APITags.BASE_URL)
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}
