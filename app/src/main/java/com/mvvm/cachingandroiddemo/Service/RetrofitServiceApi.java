package com.mvvm.cachingandroiddemo.Service;

import com.mvvm.cachingandroiddemo.model.MyPhoto;
import com.mvvm.cachingandroiddemo.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitServiceApi {

    @GET("photos")
    Call<List<MyPhoto>> getAllPhotos();

    @GET("users")
    Call<List<User>> getAllUsers();

}
