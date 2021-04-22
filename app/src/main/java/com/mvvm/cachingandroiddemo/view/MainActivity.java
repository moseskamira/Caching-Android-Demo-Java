package com.mvvm.cachingandroiddemo.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;

import com.mvvm.cachingandroiddemo.Data;
import com.mvvm.cachingandroiddemo.DataSource;
import com.mvvm.cachingandroiddemo.DiskDataSource;
import com.mvvm.cachingandroiddemo.MemoryDataSource;
import com.mvvm.cachingandroiddemo.NetworkDataSource;
import com.mvvm.cachingandroiddemo.R;
import com.mvvm.cachingandroiddemo.model.User;
import com.mvvm.cachingandroiddemo.viewModel.UserViewModel;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private UserViewModel userViewModel;
    private DataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        dataSource = new DataSource(new MemoryDataSource(), new DiskDataSource(), new NetworkDataSource());
        loadUsers();
        createObservables();
    }

    private void loadUsers() {
        userViewModel.returnAllUsers().observe(this, new androidx.lifecycle.Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (users!= null) {
                    for (User user : users) {
                        Log.d("USERADDRESS", user.getAddress().toString());
                    }

                }else {
                    Log.d("FAILEDTO", "RETURNUSERS");
                }

            }
        });
    }

    private void createObservables() {
        Observable<Data> memoryObservable = dataSource.returnDataFromMemory();
        Observable<Data> diskObservable = dataSource.returnDataFromDisk();
        Observable<Data> networkObservable = dataSource.returnDataFromNetwork();
        Observable.concat(memoryObservable, diskObservable, networkObservable)
                .firstElement()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toObservable()
                .subscribe(getObserver());


    }

    private Observer<Data> getObserver() {
        return new Observer<Data>() {

            @Override
            public void onSubscribe(Disposable d) {
                d.isDisposed();

            }

            @Override
            public void onNext(Data data) {
                Log.d("RETURNEDDATA", data.source);

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();

            }

            @Override
            public void onComplete() {

            }
        };
    }

}