package com.app.phillipi.ingressetest.Objects;

import android.os.Parcel;
import android.os.Parcelable;

public class Show implements Parcelable {

    private String name;
    private String[] genres;
    private ShowPosters image;
    private String summary;
    private String premiered;

    protected Show(Parcel in) {
        name = in.readString();
        genres = in.createStringArray();
        image = in.readParcelable(ShowPosters.class.getClassLoader());
        summary = in.readString();
        premiered = in.readString();
    }

    public static final Creator<Show> CREATOR = new Creator<Show>() {
        @Override
        public Show createFromParcel(Parcel in) {
            return new Show(in);
        }

        @Override
        public Show[] newArray(int size) {
            return new Show[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public ShowPosters getImage() {
        return image;
    }

    public void setImage(ShowPosters image) {
        this.image = image;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String sumary) {
        this.summary = sumary;
    }

    public String getPremiered() {
        return premiered;
    }

    public void setPremiered(String premiered) {
        this.premiered = premiered;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeStringArray(genres);
        parcel.writeParcelable(image, i);
        parcel.writeString(summary);
        parcel.writeString(premiered);
    }
}
