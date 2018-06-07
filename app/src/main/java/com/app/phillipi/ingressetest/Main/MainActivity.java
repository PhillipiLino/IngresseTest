package com.app.phillipi.ingressetest;

import android.content.Intent;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

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

        Retrofit retrofit = RetrofitClient.getClient(AppServices.BASE_URL);

        AppServices client = retrofit.create(AppServices.class);
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

                        Intent nextActivity = new Intent(MainActivity.this, ShowDetailsActivity.class);
                        nextActivity.putExtra("show", cardItem.getShow());
                        startActivity(nextActivity);

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
