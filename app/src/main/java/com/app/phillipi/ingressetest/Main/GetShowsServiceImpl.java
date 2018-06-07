package com.app.phillipi.ingressetest.Main;

import android.os.Handler;
import android.util.Log;

import com.app.phillipi.ingressetest.AppServices;
import com.app.phillipi.ingressetest.Objects.CatalogItem;
import com.app.phillipi.ingressetest.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class GetShowsServiceImpl implements GetShowsService{


    @Override
    public void findItems(final OnFinishedListener listener, String search) {

        Retrofit retrofit = RetrofitClient.getClient(AppServices.BASE_URL);

        AppServices client = retrofit.create(AppServices.class);
        Call<List<CatalogItem>> call = client.getShows(search);

        call.enqueue(new Callback<List<CatalogItem>>() {
            @Override
            public void onResponse(Call<List<CatalogItem>> call, retrofit2.Response<List<CatalogItem>> response) {
                List<CatalogItem> list = response.body();
                listener.onFinished(list);
            }

            @Override
            public void onFailure(Call<List<CatalogItem>> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });

    }

}
