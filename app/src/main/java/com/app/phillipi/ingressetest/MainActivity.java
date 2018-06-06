package com.app.phillipi.ingressetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.app.phillipi.ingressetest.Objects.CatalogItem;
import com.app.phillipi.ingressetest.Objects.Show;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.catalog_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final EditText editTextSearch = findViewById(R.id.editText_search);
        ImageButton searchButton = findViewById(R.id.button_search);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrofitTest(editTextSearch.getText().toString());
            }
        });


    }

    private void retrofitTest(String search){

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl("http://api.tvmaze.com/search/")
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        Retrofit retrofit =
                builder.client(okHttpClient).build();


        AppClient client = retrofit.create(AppClient.class);
        Call<List<CatalogItem>> call = client.getShows(search);

        call.enqueue(new Callback<List<CatalogItem>>() {
            @Override
            public void onResponse(Call<List<CatalogItem>> call, retrofit2.Response<List<CatalogItem>> response) {
                List<CatalogItem> list = response.body();

                ShowsRecyclerAdapter adapter =  new ShowsRecyclerAdapter(MainActivity.this, list);
                adapter.SetOnItemClickListener(new ShowsRecyclerAdapter.OnItemClickListener() {

                    @Override
                    public void onItemClick(View v, int position) {
                        CardView cv = v.findViewById(R.id.item_card_view);

                        CatalogItem cardItem = (CatalogItem) cv.getTag();

                        Log.d("Show", cardItem.getShow().getName());

                    }
                });

                recyclerView.setAdapter(adapter);
            }


            @Override
            public void onFailure(Call<List<CatalogItem>> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });

    }
}
