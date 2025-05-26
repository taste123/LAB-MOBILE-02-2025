package com.example.tp3;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Objects;

public class FeedProfile implements Parcelable {
    private String username;
    private String profilePicture;
    private String imageUri;
    private String caption;
    private int likes;
    private int comments;
    private int shares;
    private String likedBy;
    public FeedProfile(String imageUri) {
        this.imageUri = imageUri;
        this.caption = caption;
        this.username = username;
        this.profilePicture = profilePicture;
        this.likes = likes;
        this.comments = comments;
        this.shares = shares;
        this.likedBy = likedBy;
    }

    public FeedProfile(String imageUri, String caption) {
        this.imageUri = imageUri;
        this.caption = caption;
        this.username = username;
        this.profilePicture = profilePicture;
        this.likes = likes;
        this.comments = comments;
        this.shares = shares;
        this.likedBy = likedBy;
    }



    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(String likedBy) {
        this.likedBy = likedBy;
    }


    protected FeedProfile(Parcel in) {
        imageUri = in.readString();
        caption = in.readString();
        username = in.readString();
        profilePicture = in.readString();
        likes = in.readInt();
        comments = in.readInt();
        shares = in.readInt();
        likedBy = in.readString();
    }

    public static final Creator<FeedProfile> CREATOR = new Creator<FeedProfile>() {
        @Override
        public FeedProfile createFromParcel(Parcel in) {
            return new FeedProfile(in);
        }

        @Override
        public FeedProfile[] newArray(int size) {
            return new FeedProfile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(imageUri);
        dest.writeString(caption);dest.writeString(username);
        dest.writeString(profilePicture);
        dest.writeInt(likes);
        dest.writeInt(comments);
        dest.writeInt(shares);
        dest.writeString(likedBy);
    }


    @Override
    public int hashCode() {
        return Objects.hash(imageUri, caption);
    }
}
