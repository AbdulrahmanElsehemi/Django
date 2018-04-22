package com.example.abdulrahman.bobsplash.repository.inMemory.byItem;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;



import com.example.abdulrahman.bobsplash.api.UnsplashApi;
import com.example.abdulrahman.bobsplash.api.UnsplashService;
import com.example.abdulrahman.bobsplash.model.PaginationLink;
import com.example.abdulrahman.bobsplash.model.PhotosResponse;
import com.example.abdulrahman.bobsplash.model.RelType;
import com.example.abdulrahman.bobsplash.repository.NetworkState;
import com.example.abdulrahman.bobsplash.repository.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Musa on 1/7/2018.
 */

public class UnsplashPagedKeyedDataSource extends PageKeyedDataSource <String,PhotosResponse>{

    public static final String TAG = "PageKeyedDataSource";
    UnsplashService unsplashService;
    LoadInitialParams<String> initialParams;
    LoadParams<String> afterParams;
    private MutableLiveData networkState;
    private MutableLiveData initialLoading;
    private Executor retryExecutor;

    public UnsplashPagedKeyedDataSource(Executor executor) {
        unsplashService = UnsplashApi.createUnsplashService();
        networkState = new MutableLiveData();
        initialLoading = new MutableLiveData();
        this.retryExecutor = executor;
    }

    @Override
    public void loadInitial(@NonNull final LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, PhotosResponse> callback) {

        Log.i(TAG, "Loading Range Initial " + 1 + " Count " + params.requestedLoadSize);
        final List<PhotosResponse> photoObjects = new ArrayList();
        initialParams = params;
        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);


        unsplashService.getPhotosListInitial(1,params.requestedLoadSize).enqueue(new Callback<ArrayList<PhotosResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<PhotosResponse>> call, Response<ArrayList<PhotosResponse>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    Log.d("Link URL", response.headers().get("link"));
                    Log.d("NextPage Num", response.headers().get("link"));

                    photoObjects.addAll(response.body());
                    callback.onResult(photoObjects,null, String.valueOf(getNextPage(response)));
                    initialLoading.postValue(NetworkState.LOADED);
                    networkState.postValue(NetworkState.LOADED);
                    initialParams = null;

                } else {
                    Log.e("API CALL", response.message());
                    initialLoading.postValue(new NetworkState(Status.FAILED, response.message()));
                    networkState.postValue(new NetworkState(Status.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PhotosResponse>> call, Throwable t) {

                String errorMessage;
                errorMessage = t.getMessage();
                if (t == null) {
                    errorMessage = "unknown error";
                }
                networkState.postValue(new NetworkState(Status.FAILED, errorMessage));

            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, PhotosResponse> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<String> params, @NonNull final LoadCallback<String, PhotosResponse> callback) {

        Log.i(TAG, "Loading Rang Load After " + params.key + " Count " + params.requestedLoadSize);

        final List<PhotosResponse> photoObjects = new ArrayList();
        afterParams = params;
        networkState.postValue(NetworkState.LOADING);

        //check here for errors
        unsplashService.getPhotosListAfter(params.requestedLoadSize,Integer.valueOf(params.key)).enqueue(new Callback<ArrayList<PhotosResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<PhotosResponse>> call, Response<ArrayList<PhotosResponse>> response) {
                if (response.isSuccessful()) {

                    photoObjects.addAll(response.body());
                    callback.onResult(photoObjects,String.valueOf(getNextPage(response)));
                    networkState.postValue(NetworkState.LOADED);
                    afterParams = null;

                } else {

                    networkState.postValue(new NetworkState(Status.FAILED, response.message()));
                    Log.e("API CALL", response.message());
                }

            }

            @Override
            public void onFailure(Call<ArrayList<PhotosResponse>> call, Throwable t) {
                String errorMessage;
                errorMessage = t.getMessage();
                if (t == null) {

                    errorMessage = "unknown error";
                }

                networkState.postValue(new NetworkState(Status.FAILED, errorMessage));

            }
        });





    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public MutableLiveData getInitialLoading() {
        return initialLoading;
    }

    public static  int getNextPage(Response<ArrayList<PhotosResponse>> r) {


        String link = r.headers().get("link");
        Log.d("Link in GNP", link);

        if (link != null) {
            String[] parts = link.split(",");


            try {
                String net = null;

                for (String xx : parts) {

                    if (xx.contains("next")){
                        net = xx;
                    }

                }
                Log.d("NextContains",net);


                PaginationLink bottomPaginationLink = new PaginationLink(net);
                if (bottomPaginationLink.rel == RelType.next) {
                    return bottomPaginationLink.page;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        return -1;
    }


}
