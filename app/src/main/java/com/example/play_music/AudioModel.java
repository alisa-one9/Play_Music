package com.example.play_music;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

public class AudioModel implements  Serializable {
    int path;
    String title;
    int image;

    public AudioModel(int path, @Nullable String title, int image) {
        this.path = path;
        this.title = title;
        this.image = image;
    }


    public int getPath() {
        return path;
    }

    public void setPath(int path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }



}
