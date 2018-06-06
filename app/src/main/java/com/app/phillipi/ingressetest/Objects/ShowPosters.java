package com.app.phillipi.ingressetest.Objects;

import android.os.Parcel;
import android.os.Parcelable;

public class ShowPosters implements Parcelable {

    private String medium;
    private String original;

    protected ShowPosters(Parcel in) {
        medium = in.readString();
        original = in.readString();
    }

    public static final Creator<ShowPosters> CREATOR = new Creator<ShowPosters>() {
        @Override
        public ShowPosters createFromParcel(Parcel in) {
            return new ShowPosters(in);
        }

        @Override
        public ShowPosters[] newArray(int size) {
            return new ShowPosters[size];
        }
    };

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(medium);
        parcel.writeString(original);
    }
}
