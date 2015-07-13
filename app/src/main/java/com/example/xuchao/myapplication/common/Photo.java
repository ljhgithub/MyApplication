package com.example.xuchao.myapplication.common;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by xuchao on 15-7-9.
 */
public class Photo implements PhotoUrlModel, Parcelable {

    public static final int THUMBNAIL = 1;
    public static final int ORIGIN = 2;
    public static final int NORMAL = 3;

    public int size=2;
    public String thumbnail;
    public String origin;
    public String normal;


    public Photo() {
    }

    public Photo(Parcel in) {
        thumbnail = in.readString();
        origin = in.readString();
        normal = in.readString();
    }

    @Override
    public String buildUrl(int width, int height) {
        String url = "";
        switch (size) {
            default:
            case ORIGIN:
                url = origin;
                break;
            case NORMAL:
                url = normal;
                break;
            case THUMBNAIL:
                url = thumbnail;
                break;
        }
        return null == url || url.isEmpty() ? origin : url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(thumbnail);
        dest.writeString(origin);
        dest.writeString(normal);
    }
}
