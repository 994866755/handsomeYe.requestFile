package com.example.kylinarm.photorequesttest;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/9/7.
 */

public class APIClinet {

    public static String BASE_URL = "http://hb.cui.ngrok.haitou.cc/";
    private static final int TIME_OUT = 15 * 1000;

    public static Retrofit retrofit;

    public static <T> T getInstance(Class<T> service) {
        if (retrofit == null){
            initRetrofit();
        }
        return retrofit.create(service);
    }

    private static void initRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    private static OkHttpClient getOkHttpClient(){
        return new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

}
