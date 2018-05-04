package com.example.abdulrahman.bobsplash.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Abdulrahman on 5/3/2018.
 */

public class PhotoInfo {
    @SerializedName("current_user_collections")
    private List<Object> currentUserCollections;

    @SerializedName("color")
    private String color;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("description")
    private Object description;

    @SerializedName("sponsored")
    private boolean sponsored;

    @SerializedName("liked_by_user")
    private boolean likedByUser;

    @SerializedName("urls")
    private Urls urls;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("downloads")
    private int downloads;

    @SerializedName("width")
    private int width;

    @SerializedName("links")
    private Links links;

    @SerializedName("location")
    private Location location;

    @SerializedName("id")
    private String id;

    @SerializedName("categories")
    private List<Object> categories;

    @SerializedName("user")
    private User user;

    @SerializedName("slug")
    private Object slug;

    @SerializedName("views")
    private int views;

    @SerializedName("height")
    private int height;

    @SerializedName("likes")
    private int likes;

    @SerializedName("exif")
    private Exif exif;

    public List<Object> getCurrentUserCollections(){
        return currentUserCollections;
    }

    public String getColor(){
        return color;
    }

    public String getCreatedAt(){
        return createdAt;
    }

    public Object getDescription(){
        return description;
    }

    public boolean isSponsored(){
        return sponsored;
    }

    public boolean isLikedByUser(){
        return likedByUser;
    }

    public Urls getUrls(){
        return urls;
    }

    public String getUpdatedAt(){
        return updatedAt;
    }

    public int getDownloads(){
        return downloads;
    }

    public int getWidth(){
        return width;
    }

    public Links getLinks(){
        return links;
    }

    public Location getLocation(){
        return location;
    }

    public String getId(){
        return id;
    }

    public List<Object> getCategories(){
        return categories;
    }

    public User getUser(){
        return user;
    }

    public Object getSlug(){
        return slug;
    }

    public int getViews(){
        return views;
    }

    public int getHeight(){
        return height;
    }

    public int getLikes(){
        return likes;
    }

    public Exif getExif(){
        return exif;
    }
}
