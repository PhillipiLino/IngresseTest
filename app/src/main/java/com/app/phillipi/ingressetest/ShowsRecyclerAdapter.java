package com.app.phillipi.ingressetest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.phillipi.ingressetest.Objects.CatalogItem;
import com.app.phillipi.ingressetest.Objects.Show;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;

import java.util.List;

public class ShowsRecyclerAdapter extends RecyclerView.Adapter {

    static OnItemClickListener mItemClickListener;

    private List<CatalogItem> items = null;
    private Context context = null;
    private final LayoutInflater layoutInflater;

    public ShowsRecyclerAdapter(Context context, List<CatalogItem> items){
        this.context = context;
        this.items = items;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView showPoster;
        private TextView showTitle;
        private TextView showGenres;

        public ItemViewHolder(View itemView) {
            super(itemView);
            showPoster = itemView.findViewById(R.id.show_poster);
            showTitle = itemView.findViewById(R.id.show_title);
            showGenres = itemView.findViewById(R.id.show_genres);
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

        Show show = items.get(position).getShow();

        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        itemViewHolder.showTitle.setText(show.getName());
        String genres = TextUtils.join(", ", show.getGenres());
        itemViewHolder.showGenres.setText(genres);

        if (show.getImage() != null){
            Glide.with(context)
                    .load(show.getImage().getMedium())
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE))
                    .into(itemViewHolder.showPoster);
        }

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
