package com.mvvm.cachingandroiddemo;

import androidx.annotation.NonNull;

public class Data {
    public String source;

    @Override
    public @NonNull Data clone() {
        return new Data();
    }
}
