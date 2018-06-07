package com.app.phillipi.ingressetest.ShowDetails;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.phillipi.ingressetest.Objects.Show;
import com.app.phillipi.ingressetest.Objects.StarButton;
import com.app.phillipi.ingressetest.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

public class ShowDetailsActivity extends AppCompatActivity implements ShowDetailsView{

    private ShowDetailsPresenter presenter;

    private StarButton favoriteButton;

    private TextView showRelease;
    private TextView showGenres;
    private TextView showSummary;

    CollapsingToolbarLayout collapsingToolbarLayout;

    ImageView toolbarImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        presenter = new ShowDetailsPresenterImpl();

        showRelease = findViewById(R.id.show_release);
        showGenres = findViewById(R.id.show_genres);
        showSummary = findViewById(R.id.show_summary);
        showSummary.setMovementMethod(new ScrollingMovementMethod());

        toolbarImage = findViewById(R.id.app_bar_image);

        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        NestedScrollView nestedScrollView = findViewById(R.id.scroll_view);
        nestedScrollView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showSummary.getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });

        showSummary.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showSummary.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        Show show = getIntent().getParcelableExtra("show");
        setInformations(show);

        favoriteButton = findViewById(R.id.button_favorite);
        boolean isFavorite = presenter.getDataInPreferences(ShowDetailsActivity.this, show.getName());
        favoriteButton.setFavorite(isFavorite);
        favoriteButton.changeStarColor();

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Show currentShow = getIntent().getParcelableExtra("show");

                boolean isFavorite = favoriteButton.isFavorite();

                favoriteButton.setFavorite(!isFavorite);
                favoriteButton.changeStarColor();
                presenter.saveFavoriteShowInPreferences(ShowDetailsActivity.this, currentShow.getName(), !isFavorite);
            }
        });
    }

    @Override
    public void setInformations(Show show) {
        collapsingToolbarLayout.setTitle(show.getName());
        showRelease.setText(show.getPremiered());
        showGenres.setText(TextUtils.join(", ", show.getGenres()));
        showSummary.setText(Html.fromHtml(show.getSummary()));

        if(show.getImage() != null){

            String imageUrl = show.getImage().getMedium();
            if(show.getImage().getOriginal() != null){
                imageUrl = show.getImage().getOriginal();
            }
            setImageGlide(ShowDetailsActivity.this, toolbarImage, imageUrl);
        }
    }

    @Override
    public void setImageGlide(Context context, ImageView imageView, String url) {

        Glide.with(context)
                .load(url)
                .apply(
                        RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE)
                                .centerCrop()
                )
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Bitmap largeIcon = presenter.drawableToBitmap(resource);
                        Palette palette = Palette.generate(largeIcon);
                        Palette.Swatch darkVibrant = palette.getDarkVibrantSwatch();
                        if(darkVibrant != null){
                            collapsingToolbarLayout.setContentScrimColor(darkVibrant.getRgb());
                        }
                        return false;
                    }
                })
                .into(imageView);
    }
}
