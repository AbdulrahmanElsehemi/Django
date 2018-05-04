package com.example.abdulrahman.bobsplash.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abdulrahman on 5/3/2018.
 */

public class Location {
    @SerializedName("country")
    private String country;

    @SerializedName("city")
    private String city;

    @SerializedName("name")
    private String name;

    @SerializedName("position")
    private Position position;

    @SerializedName("title")
    private String title;

    public String getCountry(){
        return country;
    }

    public String getCity(){
        return city;
    }

    public String getName(){
        return name;
    }

    public Position getPosition(){
        return position;
    }

    public String getTitle(){
        return title;
    }
}
