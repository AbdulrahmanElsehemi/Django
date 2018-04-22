package com.example.abdulrahman.bobsplash.model;

import com.google.gson.annotations.SerializedName;

public class Links{

	@SerializedName("followers")
	private String followers;

	@SerializedName("portfolio")
	private String portfolio;

	@SerializedName("following")
	private String following;

	@SerializedName("self")
	private String self;

	@SerializedName("html")
	private String html;

	@SerializedName("photos")
	private String photos;

	@SerializedName("likes")
	private String likes;

	public String getFollowers(){
		return followers;
	}

	public String getPortfolio(){
		return portfolio;
	}

	public String getFollowing(){
		return following;
	}

	public String getSelf(){
		return self;
	}

	public String getHtml(){
		return html;
	}

	public String getPhotos(){
		return photos;
	}

	public String getLikes(){
		return likes;
	}
}