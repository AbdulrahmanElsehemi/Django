package com.example.abdulrahman.bobsplash.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Abdulrahman on 4/22/2018.
 */

public class Result implements Parcelable {
    @SerializedName("urls")
    @Expose
    private Urls urls;


    protected Result(Parcel in) {

        urls = (Urls)in.readParcelable(Urls.class.getClassLoader());

    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(urls,i);

    }

    public static DiffCallback<PhotosResponse> DIFF_CALLBACK = new DiffCallback<PhotosResponse>() {
        @Override
        public boolean areItemsTheSame(@NonNull PhotosResponse oldItem, @NonNull PhotosResponse newItem) {
            return oldItem.getPhotoID().equals( newItem.getPhotoID());
        }



        @Override
        public boolean areContentsTheSame(@NonNull PhotosResponse oldItem, @NonNull PhotosResponse newItem) {
            return oldItem.equals(newItem);
        }
    };
}
