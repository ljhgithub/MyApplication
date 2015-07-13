package com.example.xuchao.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageModel implements Parcelable {
    // 图片ID
    public String id;
    // 缩略图地址
    public String thumb;
    // 图片地址
    public String preview;
    // 图片原图地址
    public String original;

    public ImageModel() {
    }

    public ImageModel(Parcel source) {
        id = source.readString();
        thumb = source.readString();
        preview = source.readString();
        original = source.readString();

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(thumb);
        dest.writeString(preview);
        dest.writeString(original);
    }

    public static final Creator<ImageModel> CREATOR = new Creator<ImageModel>() {
        @Override
        public ImageModel createFromParcel(Parcel source) {
            return new ImageModel(source);
        }

        @Override
        public ImageModel[] newArray(int size) {
            return new ImageModel[size];
        }
    };
}
