package com.example.tp3;

import android.os.Parcel;
import android.os.Parcelable;
import android.net.Uri;

import androidx.annotation.NonNull;

public class Feed implements Parcelable {

    String username, caption, name;

    int pfp, content, likedBy;

    public Feed(String username, String name, String caption, int pfp, int content, int likedBy) {
        this.name = name;
        this.username = username;
        this.caption = caption;
        this.pfp = pfp;
        this.content = content;
        this.likedBy = likedBy;
    }
    public Feed(String username, String caption, int pfp, int content, int likedBy) {
        this.username = username;
        this.caption = caption;
        this.pfp = pfp;
        this.content = content;
        this.likedBy = likedBy;
    }

    public int getProfilePicture() {
        return pfp;
    }

    public String getProfileImageUri() {
        return String.valueOf(pfp);
    }
    public int getLikedBy() {
        return likedBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLikedBy(int likedBy) {
        this.likedBy = likedBy;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCaption() {
        return caption;
    }
    public String getBio() {
        return "dummy bio";
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getPfp() {
        return pfp;
    }

    public void setPfp(int pfp) {
        this.pfp = pfp;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }


    protected Feed(Parcel in) {
        username = in.readString();
        caption = in.readString();
        pfp = in.readInt();
        content = in.readInt();
        likedBy = in.readInt();
    }

    public static final Creator<Feed> CREATOR = new Creator<Feed>() {
        @Override
        public Feed createFromParcel(Parcel in) {
            return new Feed(in);
        }

        @Override
        public Feed[] newArray(int size) {
            return new Feed[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(caption);
        dest.writeInt(pfp);
        dest.writeInt(content);
        dest.writeInt(likedBy);
    }
}
