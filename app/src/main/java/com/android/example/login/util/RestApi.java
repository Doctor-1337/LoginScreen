package com.android.example.login.util;

import com.android.example.login.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RestApi {

        @POST("login")
        Call<User> createPost(@Body User user);

}
