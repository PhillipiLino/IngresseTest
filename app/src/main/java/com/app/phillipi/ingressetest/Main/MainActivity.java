package com.app.phillipi.ingressetest.Main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.app.phillipi.ingressetest.Objects.CatalogItem;
import com.app.phillipi.ingressetest.Objects.Show;
import com.app.phillipi.ingressetest.R;
import com.app.phillipi.ingressetest.ShowDetails.ShowDetailsActivity;
import com.app.phillipi.ingressetest.ShowsRecyclerAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView{

    RecyclerView recyclerView;
    private ProgressBar progressBar;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenterImpl(this, new GetShowsServiceImpl());

        progressBar = findViewById(R.id.progressBar);

        recyclerView = findViewById(R.id.catalog_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final EditText editTextSearch = findViewById(R.id.editText_search);
        ImageButton searchButton = findViewById(R.id.button_search);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.doTheSearch(editTextSearch.getText().toString());
            }
        });

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setItems(List<CatalogItem> items) {
        ShowsRecyclerAdapter adapter =  new ShowsRecyclerAdapter(MainActivity.this, items);
        adapter.SetOnItemClickListener(new ShowsRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                presenter.onItemClicked(v);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void changeActivity(Show show) {
        Intent nextActivity = new Intent(MainActivity.this, ShowDetailsActivity.class);
        nextActivity.putExtra("show", show);
        startActivity(nextActivity);
    }
}
