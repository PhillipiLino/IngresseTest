package com.app.phillipi.ingressetest;

import com.app.phillipi.ingressetest.Objects.CatalogItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AppServices {

    String BASE_URL = "http://api.tvmaze.com/search/";
    @GET("shows")
    Call<List<CatalogItem>> getShows(@Query("q") String search);
}
