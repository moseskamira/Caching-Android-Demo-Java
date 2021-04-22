package com.mvvm.cachingandroiddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.mvvm.cachingandroiddemo.Service.RetrofitService;
import com.mvvm.cachingandroiddemo.Service.RetrofitServiceApi;
import com.mvvm.cachingandroiddemo.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RetrofitServiceApi retrofitServiceApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrofitServiceApi = RetrofitService.getRetrofitService().getRetrofitServiceApi();
        loadUsers();
    }

    private void loadUsers() {
        retrofitServiceApi.getAllUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.body() != null) {
                    for (User user : response.body()) {
                        Log.d("USERADDRESS", user.getAddress().toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });


    }
}