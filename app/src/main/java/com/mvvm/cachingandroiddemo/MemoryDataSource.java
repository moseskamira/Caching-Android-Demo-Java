package com.mvvm.cachingandroiddemo;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class MemoryDataSource {
    Data data;

    public Observable<Data> getData() {
        return Observable.create(new ObservableOnSubscribe<Data>() {
            @Override
            public void subscribe(ObservableEmitter<Data> emitter) throws Exception {
                if (data!= null) {
                    emitter.onNext(data);
                }
                emitter.onComplete();
            }
        });
    }

    public void saveToMemory(Data myData) {
        this.data = myData.clone();
        this.data.source = "Memory";
    }
}
