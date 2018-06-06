package com.app.phillipi.ingressetest;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.phillipi.ingressetest.Objects.Show;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

public class ShowDetailsActivity extends AppCompatActivity {

    private ImageButton favoriteButton;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        final Show currentShow = getIntent().getParcelableExtra("show");

        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(currentShow.getName());
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ImageView toolbarImage = findViewById(R.id.app_bar_image);

        if(currentShow.getImage() != null){

            String imageUrl = currentShow.getImage().getMedium();

            if(currentShow.getImage().getOriginal() != null){
                imageUrl = currentShow.getImage().getOriginal();
            }

            Glide.with(this)
                    .load(imageUrl)
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
                            Bitmap largeIcon = drawableToBitmap(resource);
                            Palette palette = Palette.generate(largeIcon);
                            Palette.Swatch darkVibrant = palette.getDarkVibrantSwatch();
                            if(darkVibrant != null){
                                collapsingToolbarLayout.setContentScrimColor(darkVibrant.getRgb());
                            }
                            return false;
                        }
                    })
                    .into(toolbarImage);
        }

        TextView showRelease = findViewById(R.id.show_release);
        TextView showGenres = findViewById(R.id.show_genres);
        final TextView showSummary = findViewById(R.id.show_summary);

        showRelease.setText(currentShow.getPremiered());
        showGenres.setText(TextUtils.join(", ", currentShow.getGenres()));
        showSummary.setMovementMethod(new ScrollingMovementMethod());
        showSummary.setText(Html.fromHtml(currentShow.getSummary()));

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

        favoriteButton = findViewById(R.id.button_favorite);
        preferences = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        boolean isFavorite = preferences.getBoolean(currentShow.getName(), false);
        favoriteButton.setTag(isFavorite);

        if(isFavorite){
            favoriteButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_star_favorite));
        } else {
            favoriteButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_star_not_favorite));
        }

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isFavorite = (boolean) favoriteButton.getTag();
                if(isFavorite){
                    favoriteButton.setImageDrawable(ContextCompat.getDrawable(ShowDetailsActivity.this, R.drawable.ic_star_not_favorite));
                } else {
                    favoriteButton.setImageDrawable(ContextCompat.getDrawable(ShowDetailsActivity.this, R.drawable.ic_star_favorite));
                }
                favoriteButton.setTag(!isFavorite);
                preferences.edit().putBoolean(currentShow.getName(), !isFavorite).apply();
            }
        });
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
