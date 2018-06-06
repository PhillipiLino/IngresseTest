package com.app.phillipi.ingressetest.Objects;

import org.json.JSONObject;

public class Show {

    private String name;
    private String[] genres;
    private ShowPosters image;
    private String sumary;
    private String premiered;

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

    public String getSumary() {
        return sumary;
    }

    public void setSumary(String sumary) {
        this.sumary = sumary;
    }

    public String getPremiered() {
        return premiered;
    }

    public void setPremiered(String premiered) {
        this.premiered = premiered;
    }
}
