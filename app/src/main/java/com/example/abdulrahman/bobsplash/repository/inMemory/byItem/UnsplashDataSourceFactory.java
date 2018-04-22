package com.example.abdulrahman.bobsplash.repository.inMemory.byItem;

import android.arch.lifecycle.MutableLiveData;

import java.util.concurrent.Executor;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.example.abdulrahman.bobsplash.model.PhotosResponse;


public class UnsplashDataSourceFactory implements DataSource.Factory {


    MutableLiveData<UnsplashPagedKeyedDataSource> mutableLiveData;
    UnsplashPagedKeyedDataSource unsplashPagedKeyedDataSource;

    Executor executor;

    public UnsplashDataSourceFactory(Executor executor) {

        this.mutableLiveData = new MutableLiveData<UnsplashPagedKeyedDataSource>();
        this.executor = executor;
    }

    @Override
    public DataSource<String, PhotosResponse> create() {

        unsplashPagedKeyedDataSource = new UnsplashPagedKeyedDataSource(executor);
        mutableLiveData.postValue(unsplashPagedKeyedDataSource);
        return unsplashPagedKeyedDataSource;

    }

    public MutableLiveData<UnsplashPagedKeyedDataSource> getMutableLiveData() {

        return mutableLiveData;
    }
}
