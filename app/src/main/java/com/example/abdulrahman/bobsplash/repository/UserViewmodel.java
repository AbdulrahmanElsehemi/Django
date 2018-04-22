package com.example.abdulrahman.bobsplash.repository;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.example.abdulrahman.bobsplash.model.PhotosResponse;
import com.example.abdulrahman.bobsplash.repository.NetworkState;
import com.example.abdulrahman.bobsplash.repository.inMemory.byItem.UnsplashDataSourceFactory;
import com.example.abdulrahman.bobsplash.repository.inMemory.byItem.UnsplashPagedKeyedDataSource;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class UserViewmodel extends ViewModel {

    public LiveData<PagedList<PhotosResponse>> photoList;
    public LiveData<NetworkState> networkState;
    LiveData<UnsplashPagedKeyedDataSource> tDataSource;


    Executor executor;

    public UserViewmodel() {

        executor = Executors.newFixedThreadPool(5);

        UnsplashDataSourceFactory unsplashDataSourceFactory = new UnsplashDataSourceFactory(executor);

        tDataSource = unsplashDataSourceFactory.getMutableLiveData();

        networkState = Transformations.switchMap(unsplashDataSourceFactory
                .getMutableLiveData(), new Function<UnsplashPagedKeyedDataSource, LiveData<NetworkState>>() {
            @Override
            public LiveData<NetworkState> apply(UnsplashPagedKeyedDataSource input) {

                return input.getNetworkState();
            }
        });


        PagedList.Config pagedListConfig =

                (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(20).build();

        photoList = (new LivePagedListBuilder(unsplashDataSourceFactory, pagedListConfig))

                .setBackgroundThreadExecutor(executor)
                .build();
    }
}
