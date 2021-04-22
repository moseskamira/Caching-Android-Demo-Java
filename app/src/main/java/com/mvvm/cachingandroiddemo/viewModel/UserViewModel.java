package com.mvvm.cachingandroiddemo.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mvvm.cachingandroiddemo.Service.RetrofitService;
import com.mvvm.cachingandroiddemo.Service.RetrofitServiceApi;
import com.mvvm.cachingandroiddemo.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends ViewModel {
    private RetrofitServiceApi retrofitServiceApi;
    private MutableLiveData<List<User>> usersList;

    public UserViewModel() {
        usersList = new MutableLiveData<>();
        retrofitServiceApi = RetrofitService.getRetrofitService().getRetrofitServiceApi();

    }

    public LiveData<List<User>> returnAllUsers() {
        retrofitServiceApi.getAllUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.body() != null) {
                    usersList.postValue(response.body());

                }else {
                    usersList.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
        return usersList;
    }


}
