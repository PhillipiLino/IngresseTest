package com.app.phillipi.ingressetest.ShowDetails;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ShowDetailsPresenterImpl implements ShowDetailsPresenter {

//    private ShowDetailsView showDetailsView;
//
//    public ShowDetailsPresenterImpl(ShowDetailsView showDetailsView) {
//        this.showDetailsView = showDetailsView;
//    }

    @Override
    public void saveFavoriteShowInPreferences(Context context, String show, boolean isFavorite) {
        SharedPreferences preferences = context.getSharedPreferences("DATA", Context.MODE_PRIVATE);
        preferences.edit().putBoolean(show, isFavorite).apply();
    }

    @Override
    public boolean getDataInPreferences(Context context, String show) {
        SharedPreferences preferences = context.getSharedPreferences("DATA", Context.MODE_PRIVATE);
        return preferences.getBoolean(show, false);
    }

    @Override
    public Bitmap drawableToBitmap(Drawable drawable) {
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
