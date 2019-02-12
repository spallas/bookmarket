package com.gnufsociety.bookmarket.adapters;


import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gnufsociety.bookmarket.R;
import com.gnufsociety.bookmarket.models.Ad;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by spallas on 04/02/2019.
 */
public class MyCardAdapter extends RecyclerView.Adapter<MyCardAdapter.CardViewHolder> {

    public List<Ad> list;
    public MyCardAdapter(List<Ad> list) {
        this.list = list;
//        fstorage = FirebaseStorage.getInstance();
//        storage = fstorage.getReferenceFromUrlx("gs://openchallenge-81990.appspot.com");
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

//        StorageReference img = storage.child("challenges/"+ad.imageLocation);
//        StorageReference userImg = storage.child("users/"+ad.organizer.proPicLocation);

        holder.title.setText(ad.getBook().getTitle());
        holder.author.setText(ad.getBook().getAuthor());
        holder.desc.setText(ad.getDescription());
        holder.price.setText(ad.getPrice());

//        Glide.with(holder.desc.getContext())
//                .using(new FirebaseImageLoader())
//                .load(img)
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .into(holder.img);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_title)  TextView title;
        @BindView(R.id.card_author)  TextView author;
        @BindView(R.id.card_desc)  TextView desc;
        @BindView(R.id.card_price) TextView price;
        @BindView(R.id.card_img) ImageView img;

        public CardViewHolder(final View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }
}
