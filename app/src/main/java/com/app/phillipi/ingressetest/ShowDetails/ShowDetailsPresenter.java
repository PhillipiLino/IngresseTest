package com.app.phillipi.ingressetest.ShowDetails;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public interface ShowDetailsPresenter {

    void saveFavoriteShowInPreferences(Context context, String show, boolean isFavorite);

    boolean getDataInPreferences(Context context, String show);

    Bitmap drawableToBitmap (Drawable drawable);
}
