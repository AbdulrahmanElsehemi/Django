package com.example.abdulrahman.bobsplash.api;

import com.example.abdulrahman.bobsplash.model.PhotosResponse;

import java.util.ArrayList;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Abdulrahman on 4/16/2018.
 */

public interface UnsplashService {

    @GET("photos")
    Call<ArrayList<PhotosResponse>> getPhotosListInitial(@Query("page")int page, @Query("per_page")int perPage);

    @GET("photos")
    Call< ArrayList<PhotosResponse>>getPhotosListAfter( @Query("per_page")int perPage, @Query("page") int page);


}
