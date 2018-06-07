package com.app.phillipi.ingressetest.ShowDetails;

import android.content.Context;
import android.widget.ImageView;

import com.app.phillipi.ingressetest.Objects.Show;

public interface ShowDetailsView {

    void setInformations(Show show);

    void setImageGlide(Context context, ImageView view, String url);

}
