package com.gnufsociety.bookmarket.adapters;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gnufsociety.bookmarket.R;
import com.gnufsociety.bookmarket.models.Ad;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by spallas on 04/02/2019.
 */
public class MyCardAdapter extends RecyclerView.Adapter<MyCardAdapter.CardViewHolder> {

    public List<Ad> list;
    public MyCardAdapter(List<Ad> list) {
        this.list = list;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,
                parent,
                false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Ad ad = list.get(position);

        holder.title.setText(ad.getBook().getTitle());
        holder.author.setText(ad.getBook().getAuthor());
        holder.price.setText(ad.getPrice());

        Log.e("PROFILE IMAGE URL", ad.getImgUrl());
        Glide.with(holder.title.getContext())
                .load(ad.getImgUrl())
                .into(holder.img);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_title)  TextView title;
        @BindView(R.id.card_author)  TextView author;
        @BindView(R.id.card_price) TextView price;
        @BindView(R.id.card_img) ImageView img;

        public CardViewHolder(final View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }
}
