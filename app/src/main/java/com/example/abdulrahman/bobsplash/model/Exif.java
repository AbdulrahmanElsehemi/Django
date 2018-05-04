package com.example.abdulrahman.bobsplash.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abdulrahman on 5/3/2018.
 */

public class Exif {
    @SerializedName("exposure_time")
    private String exposureTime;

    @SerializedName("aperture")
    private String aperture;

    @SerializedName("focal_length")
    private String focalLength;

    @SerializedName("iso")
    private int iso;

    @SerializedName("model")
    private String model;

    @SerializedName("make")
    private String make;

    public String getExposureTime(){
        return exposureTime;
    }

    public String getAperture(){
        return aperture;
    }

    public String getFocalLength(){
        return focalLength;
    }

    public int getIso(){
        return iso;
    }

    public String getModel(){
        return model;
    }

    public String getMake(){
        return make;
    }
}
