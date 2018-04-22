package com.example.abdulrahman.bobsplash.model;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PhotosResponse{

	@SerializedName("id")
	private String photoID;
	@SerializedName("created_at")
	private String createdAt;
	@SerializedName("updated_at")
	private String updatedAt;
	@SerializedName("width")
	private Integer width;
	@SerializedName("height")
	private Integer height;
	@SerializedName("color")
	private String color;
	@SerializedName("description")
	private String description;

	private ArrayList categories;
	@SerializedName("urls")
	private Urls photoUrls;
	@SerializedName("links")
	private Links links;
	@SerializedName("liked_by_user")
	private boolean likedByUser;
	@SerializedName("sponsored")
	private boolean sponsoredPhoto ;
	@SerializedName("likes")
	private Integer photoLikes ;
	@SerializedName("user")
	private User photoUser ;
	@SerializedName("current_user_collection")
	private ArrayList currentUserCollection;

	public PhotosResponse(String photoID, String createdAt, String updatedAt, Integer width, Integer height, String color, String description, ArrayList categories, Urls photoUrls, Links links, boolean likedByUser, boolean sponsoredPhoto, Integer photoLikes, User photoUser, ArrayList currentUserCollection) {
		this.photoID = photoID;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.width = width;
		this.height = height;
		this.color = color;
		this.description = description;
		this.categories = categories;
		this.photoUrls = photoUrls;
		this.links = links;
		this.likedByUser = likedByUser;
		this.sponsoredPhoto = sponsoredPhoto;
		this.photoLikes = photoLikes;
		this.photoUser = photoUser;
		this.currentUserCollection = currentUserCollection;
	}

	public String getPhotoID() {
		return photoID;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public Integer getWidth() {
		return width;
	}

	public Integer getHeight() {
		return height;
	}

	public String getColor() {
		return color;
	}

	public String getDescription() {
		return description;
	}

	public ArrayList getCategories() {
		return categories;
	}

	public Urls getPhotoUrls() {
		return photoUrls;
	}

	public Links getLinks() {
		return links;
	}

	public boolean isLikedByUser() {
		return likedByUser;
	}

	public boolean isSponsoredPhoto() {
		return sponsoredPhoto;
	}

	public Integer getPhotoLikes() {
		return photoLikes;
	}

	public User getPhotoUser() {
		return photoUser;
	}

	public ArrayList getCurrentUserCollection() {
		return currentUserCollection;
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

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;

		PhotosResponse photoObjects = (PhotosResponse) obj;

		return photoObjects.getPhotoID().equals(this.getPhotoID());
	}

	@SerializedName("results")
	@Expose
	private List<Result> results = null;


	public List<Result> getResults() {
		return results;
	}
}