package com.app.phillipi.ingressetest;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.phillipi.ingressetest.Objects.CatalogItem;
import com.app.phillipi.ingressetest.Objects.Show;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ShowsRecyclerAdapter extends RecyclerView.Adapter {

    static OnItemClickListener mItemClickListener;

    private List<CatalogItem> items;
    private Context context;

    SharedPreferences preferences;

    public ShowsRecyclerAdapter(Context context, List<CatalogItem> items){
        this.context = context;
        this.items = items;
        this.preferences = context.getSharedPreferences("DATA", Context.MODE_PRIVATE);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView showPoster;
        private TextView showTitle;
        private TextView showGenres;
        private CardView cardView;
        private ImageButton favoriteButton;
        private ImageView itemBackground;

        public ItemViewHolder(View itemView) {
            super(itemView);
            showPoster = itemView.findViewById(R.id.show_poster);
            showTitle = itemView.findViewById(R.id.show_title);
            showGenres = itemView.findViewById(R.id.show_genres);
            cardView = itemView.findViewById(R.id.item_card_view);
            favoriteButton = itemView.findViewById(R.id.button_favorite);
            itemBackground = itemView.findViewById(R.id.item_background_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.series_list_item, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(v);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final Show show = items.get(position).getShow();

        final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.showTitle.setText(show.getName());
        String genres = TextUtils.join(", ", show.getGenres());
        itemViewHolder.showGenres.setText(genres);

        if (show.getImage() != null){
            Glide.with(context)
                    .load(show.getImage().getMedium())
                    .apply(
                            RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE)
                                    .centerCrop()
                    )
                    .into(itemViewHolder.showPoster);

            Glide.with(context)
                    .load(show.getImage().getMedium())
                    .apply(
                            RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE)
                                    .centerCrop()
                    )
                    .into(itemViewHolder.itemBackground);
        }

        boolean isFavorite = preferences.getBoolean(show.getName(), false);
        itemViewHolder.favoriteButton.setTag(isFavorite);

        if(isFavorite){
            itemViewHolder.favoriteButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star_favorite));
        } else {
            itemViewHolder.favoriteButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star_not_favorite));
        }

        itemViewHolder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isFavorite = (boolean) itemViewHolder.favoriteButton.getTag();
                if(isFavorite){
                    itemViewHolder.favoriteButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star_not_favorite));
                } else {
                    itemViewHolder.favoriteButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_star_favorite));
                }
                itemViewHolder.favoriteButton.setTag(!isFavorite);
                preferences.edit().putBoolean(show.getName(), !isFavorite).apply();
                Log.d("STAR", "FAVORITE");
            }
        });

        itemViewHolder.cardView.setTag(items.get(position));

    }

    public interface OnItemClickListener {
        public void onItemClick(View view , int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
