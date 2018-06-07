package com.app.phillipi.ingressetest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.phillipi.ingressetest.Objects.CatalogItem;
import com.app.phillipi.ingressetest.Objects.Show;
import com.app.phillipi.ingressetest.Objects.StarButton;
import com.app.phillipi.ingressetest.ShowDetails.ShowDetailsPresenter;
import com.app.phillipi.ingressetest.ShowDetails.ShowDetailsPresenterImpl;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ShowsRecyclerAdapter extends RecyclerView.Adapter {

    static OnItemClickListener mItemClickListener;

    private List<CatalogItem> items;
    private Context context;

    ShowDetailsPresenter presenter;

    public ShowsRecyclerAdapter(Context context, List<CatalogItem> items){
        this.context = context;
        this.items = items;

        presenter = new ShowDetailsPresenterImpl();

    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView showPoster;
        private TextView showTitle;
        private TextView showGenres;
        private CardView cardView;
        private StarButton favoriteButton;
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
            setImage(itemViewHolder.showPoster, show.getImage().getMedium());
            setImage(itemViewHolder.itemBackground, show.getImage().getMedium());
        }

        boolean isFavorite = presenter.getDataInPreferences(context, show.getName());
        itemViewHolder.favoriteButton.setFavorite(isFavorite);
        itemViewHolder.favoriteButton.changeStarColor();

        itemViewHolder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isFavorite = itemViewHolder.favoriteButton.isFavorite();

                itemViewHolder.favoriteButton.setFavorite(!isFavorite);
                itemViewHolder.favoriteButton.changeStarColor();
                presenter.saveFavoriteShowInPreferences(context, show.getName(), !isFavorite);
            }
        });

        itemViewHolder.cardView.setTag(items.get(position));

    }

    public void setImage(ImageView imageView, String url){
        Glide.with(context)
                .load(url)
                .apply(
                        RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE)
                                .centerCrop()
                )
                .into(imageView);
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
