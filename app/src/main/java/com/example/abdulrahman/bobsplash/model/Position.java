package com.example.abdulrahman.bobsplash.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abdulrahman on 5/3/2018.
 */

public class Position {

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }
}
