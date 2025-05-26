package com.example.testtp2;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class User implements Parcelable {
    String name;
    String username;
    String password;
    String email;
    String imageUri;

    public User(String name, String username, String password, String email, String imageUri){
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.imageUri = imageUri;
    }

    protected User(Parcel in) {
        name = in.readString();
        username = in.readString();
        password = in.readString();
        email = in.readString();
        imageUri = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getImageUri() { return imageUri; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(email);
        dest.writeString(imageUri);
    }
}
