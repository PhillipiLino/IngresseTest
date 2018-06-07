package com.app.phillipi.ingressetest.Objects;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import com.app.phillipi.ingressetest.R;

public class StarButton extends android.support.v7.widget.AppCompatImageButton {

    private boolean isFavorite;
    private Context context;

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public StarButton(Context context) {
        super(context);
        this.context = context;
    }

    public StarButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public StarButton(Context context, AttributeSet attrs, int defStyleAttr, Context context1) {
        super(context, attrs, defStyleAttr);
        this.context = context1;
    }

    public void changeStarColor(){

        if(isFavorite()){
            setImageDrawable(ContextCompat.getDrawable(context,
                    R.drawable.ic_star_favorite));
        } else {
            setImageDrawable(ContextCompat.getDrawable(context,
                    R.drawable.ic_star_not_favorite));
        }
    }

}
