package com.example.abdulrahman.bobsplash.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Urls implements Parcelable{

	@SerializedName("small")
	private String small;

	@SerializedName("thumb")
	private String thumb;

	@SerializedName("raw")
	private String raw;

	@SerializedName("regular")
	private String regular;

	@SerializedName("full")
	private String full;

	protected Urls(Parcel in) {
		small = in.readString();
		thumb = in.readString();
		raw = in.readString();
		regular = in.readString();
		full = in.readString();
	}

	public static final Creator<Urls> CREATOR = new Creator<Urls>() {
		@Override
		public Urls createFromParcel(Parcel in) {
			return new Urls(in);
		}

		@Override
		public Urls[] newArray(int size) {
			return new Urls[size];
		}
	};

	public String getSmall(){
		return small;
	}

	public String getThumb(){
		return thumb;
	}

	public String getRaw(){
		return raw;
	}

	public String getRegular(){
		return regular;
	}

	public String getFull(){
		return full;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(small);
		parcel.writeString(thumb);
		parcel.writeString(raw);
		parcel.writeString(regular);
		parcel.writeString(full);
	}
}