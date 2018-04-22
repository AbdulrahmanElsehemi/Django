package com.example.abdulrahman.bobsplash.api;

/**
 * Created by Abdulrahman on 4/16/2018.
 */

public class UnsplashApi {

    public static UnsplashService createUnsplashService() {


        return ServiceGenerator.createService(UnsplashService.class);
    }
}
