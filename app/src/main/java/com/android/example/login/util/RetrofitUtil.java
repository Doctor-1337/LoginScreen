package com.android.example.login.util;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil extends Application {
    private static RetrofitUtil mInstance;
    private static Retrofit retrofit = null;
    final private static String BASE_URL = "https://api.cinquex.com/api/internshala/";


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized  RetrofitUtil getmInstance(){
        return mInstance;
    }

    public static Retrofit getRetrofitClient(){
        if(retrofit == null){
             retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();




        }
        return retrofit;
    }
}
