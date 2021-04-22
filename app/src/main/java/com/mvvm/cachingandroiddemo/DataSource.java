package com.mvvm.cachingandroiddemo;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class DataSource {
    private final MemoryDataSource memoryDataSource;
    private final DiskDataSource diskDataSource;
    private final NetworkDataSource networkDataSource;

    public DataSource(MemoryDataSource memoryDataSource,
                      DiskDataSource diskDataSource,
                      NetworkDataSource networkDataSource) {
        this.memoryDataSource = memoryDataSource;
        this.diskDataSource = diskDataSource;
        this.networkDataSource = networkDataSource;
    }

    public Observable<Data> returnDataFromMemory() {
        return memoryDataSource.getData();
    }

    public Observable<Data> returnDataFromDisk() {
        return diskDataSource.getData().doOnNext(new Consumer<Data>() {
            @Override
            public void accept(Data data) throws Exception {
                memoryDataSource.saveToMemory(data);
            }
        });
    }

    public Observable<Data> returnDataFromNetwork() {
        return networkDataSource.getData().doOnNext(new Consumer<Data>() {
            @Override
            public void accept(Data data) throws Exception {
                diskDataSource.saveToDisk(data);
                memoryDataSource.saveToMemory(data);
            }
        });
    }

}
