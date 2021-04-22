package com.mvvm.cachingandroiddemo;

import com.mvvm.cachingandroiddemo.viewModel.UserViewModel;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class NetworkDataSource {
    private UserViewModel userViewModel;

    public Observable<Data> getData() {
        return Observable.create(new ObservableOnSubscribe<Data>() {
            @Override
            public void subscribe(ObservableEmitter<Data> emitter) throws Exception {
                Data data = new Data();
                data.source = "Network";
                emitter.onNext(data);
                emitter.onComplete();
            }
        });
    }
}
