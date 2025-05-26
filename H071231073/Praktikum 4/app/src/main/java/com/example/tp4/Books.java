package com.example.tp4;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Books implements Parcelable {
    private String title, writer, genre, blurb, cover;
    private int like, year;

    public Books(String title, String writer, String genre, int year, String blurb, String cover, int like) {
        this.title = title;
        this.writer = writer;
        this.genre = genre;
        this.year = year;
        this.blurb = blurb;
        this.cover = cover;
        this.like = like;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    protected Books(Parcel in) {
        title = in.readString();
        writer = in.readString();
        genre = in.readString();
        year = in.readInt();
        blurb = in.readString();
        cover = in.readString();
        like = in.readInt();
    }

    public static final Creator<Books> CREATOR = new Creator<Books>() {
        @Override
        public Books createFromParcel(Parcel in) {
            return new Books(in);
        }

        @Override
        public Books[] newArray(int size) {
            return new Books[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(writer);
        dest.writeString(genre);
        dest.writeInt(year);
        dest.writeString(blurb);
        dest.writeString(cover);
        dest.writeInt(like);
    }
}
